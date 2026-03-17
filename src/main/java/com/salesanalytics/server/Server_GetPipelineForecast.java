package com.salesanalytics.server;

import com.salesanalytics.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Server_GetPipelineForecast
 *
 * Returns the estimated value of all OPEN opportunities,
 * broken down by stage. Optionally filtered by a single rep.
 *
 * API Type : Complex Query (60 pts)
 * Author   : Joshua Ramos
 */
public class Server_GetPipelineForecast {

    // -------------------------------------------------------
    // Result records
    // -------------------------------------------------------

    /** One row per stage in the breakdown */
    public static class StageBreakdown {
        public String stageName;
        public int    opportunityCount;
        public double totalValue;

        @Override
        public String toString() {
            return String.format("  %-18s | %3d opportunities | $%,14.2f",
                    stageName, opportunityCount, totalValue);
        }
    }

    /** Full forecast result */
    public static class ForecastResult {
        public String       repEmail;          // null = all reps
        public String       repName;           // null = all reps
        public int          totalOpportunities;
        public double       totalPipelineValue;
        public List<StageBreakdown> byStage = new ArrayList<>();

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            String scope = (repEmail != null)
                    ? "Rep: " + repName + " <" + repEmail + ">"
                    : "All Reps (Company-Wide)";

            sb.append("\n========================================\n");
            sb.append("  PIPELINE FORECAST — ").append(scope).append("\n");
            sb.append("========================================\n");
            sb.append(String.format("  Total Open Opportunities : %d%n", totalOpportunities));
            sb.append(String.format("  Total Pipeline Value     : $%,.2f%n", totalPipelineValue));
            sb.append("\n  Breakdown by Stage:\n");
            sb.append("  ----------------------------------------------------------\n");
            for (StageBreakdown s : byStage) {
                sb.append(s).append("\n");
            }
            sb.append("  ----------------------------------------------------------\n");
            return sb.toString();
        }
    }

    // -------------------------------------------------------
    // Core server method
    // -------------------------------------------------------

    /**
     * GetPipelineForecast
     *
     * @param repEmail  sales rep email to filter by, or null/blank for all reps
     * @return          ForecastResult with total + stage breakdown
     * @throws SQLException on DB error or invalid rep email
     */
    public static ForecastResult execute(String repEmail) throws SQLException {

        boolean filterByRep = (repEmail != null && !repEmail.isBlank());

        // --------------------------------------------------
        // 1. Validate rep if filtering
        // --------------------------------------------------
        String repFirstName = null, repLastName = null;
        if (filterByRep) {
            String[] name = getRepName(repEmail);
            if (name == null) {
                throw new SQLException("No sales rep found with email: " + repEmail);
            }
            repFirstName = name[0];
            repLastName  = name[1];
        }

        // --------------------------------------------------
        // 2. Stage-level breakdown query (open opps only)
        // --------------------------------------------------
        String breakdownSql = """
            SELECT
                os.stageName,
                COUNT(o.opportunityID)  AS oppCount,
                COALESCE(SUM(o.dealAmount), 0) AS stageTotal
            FROM Opportunities o
            JOIN OpportunityStage  os  ON o.stageID  = os.stageID
            JOIN OpportunityStatus ost ON o.statusID = ost.statusID
            JOIN SalesReps         sr  ON o.salesRepID = sr.salesRepID
            WHERE LOWER(ost.statusName) = 'open'
            """ +
            (filterByRep ? "  AND LOWER(sr.email) = LOWER(?) \n" : "") +
            """
            GROUP BY os.stageID, os.stageName
            ORDER BY os.stageID
            """;

        // --------------------------------------------------
        // 3. Totals query
        // --------------------------------------------------
        String totalSql = """
            SELECT
                COUNT(o.opportunityID)         AS totalCount,
                COALESCE(SUM(o.dealAmount), 0) AS grandTotal
            FROM Opportunities o
            JOIN OpportunityStatus ost ON o.statusID   = ost.statusID
            JOIN SalesReps         sr  ON o.salesRepID = sr.salesRepID
            WHERE LOWER(ost.statusName) = 'open'
            """ +
            (filterByRep ? "  AND LOWER(sr.email) = LOWER(?)" : "");

        ForecastResult result = new ForecastResult();
        result.repEmail = filterByRep ? repEmail : null;
        result.repName  = filterByRep ? repFirstName + " " + repLastName : null;

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();

            // Run totals
            try (PreparedStatement ps = conn.prepareStatement(totalSql)) {
                if (filterByRep) ps.setString(1, repEmail);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        result.totalOpportunities = rs.getInt("totalCount");
                        result.totalPipelineValue = rs.getDouble("grandTotal");
                    }
                }
            }

            // Run stage breakdown
            try (PreparedStatement ps = conn.prepareStatement(breakdownSql)) {
                if (filterByRep) ps.setString(1, repEmail);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        StageBreakdown row = new StageBreakdown();
                        row.stageName        = rs.getString("stageName");
                        row.opportunityCount = rs.getInt("oppCount");
                        row.totalValue       = rs.getDouble("stageTotal");
                        result.byStage.add(row);
                    }
                }
            }

        } finally {
            DBConnection.close(conn);
        }

        return result;
    }

    // -------------------------------------------------------
    // Helper
    // -------------------------------------------------------
    private static String[] getRepName(String repEmail) throws SQLException {
        String sql = "SELECT firstName, lastName FROM SalesReps WHERE LOWER(email) = LOWER(?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, repEmail);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new String[]{ rs.getString("firstName"), rs.getString("lastName") };
                    }
                }
            }
        } finally {
            DBConnection.close(conn);
        }
        return null;
    }
}
