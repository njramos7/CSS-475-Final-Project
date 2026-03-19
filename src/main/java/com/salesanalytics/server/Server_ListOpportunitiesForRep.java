package com.salesanalytics.server;

import com.salesanalytics.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server_ListOpportunitiesForRep {

    public static class OpportunitySummary {
        public int    opportunityID;
        public String companyName;
        public String contactName;
        public String stageName;
        public String statusName;
        public double dealAmount;
        public String expectedCloseDate;
        public String actualCloseDate;
        public String dateCreated;
        public String lastUpdated;

        @Override
        public String toString() {
            return String.format(
                "  [Opp #%d] %-22s | %-15s | %-15s | $%,12.2f | Expected: %s",
                opportunityID,
                companyName,
                stageName,
                statusName,
                dealAmount,
                expectedCloseDate != null ? expectedCloseDate : "N/A"
            );
        }
    }

    public static List<OpportunitySummary> execute(String repEmail) throws SQLException {
        // Validate input
        if (repEmail == null || repEmail.isBlank()) {
            throw new IllegalArgumentException("repEmail is required.");
        }

        String sql = """
            SELECT
                o.opportunityID,
                c.companyName,
                c.contactName,
                os.stageName,
                ost.statusName,
                o.dealAmount,
                TO_CHAR(o.expectedCloseDate, 'YYYY-MM-DD') AS expectedCloseDate,
                TO_CHAR(o.actualCloseDate,   'YYYY-MM-DD') AS actualCloseDate,
                TO_CHAR(o.dateCreated,       'YYYY-MM-DD') AS dateCreated,
                TO_CHAR(o.lastUpdated,       'YYYY-MM-DD') AS lastUpdated
            FROM Opportunities o
            JOIN Customers         c   ON o.customerID = c.customerID
            JOIN OpportunityStage  os  ON o.stageID    = os.stageID
            JOIN OpportunityStatus ost ON o.statusID   = ost.statusID
            JOIN SalesReps         sr  ON o.salesRepID = sr.salesRepID
            WHERE LOWER(sr.email) = LOWER(?)
            ORDER BY o.lastUpdated DESC
            """;

        List<OpportunitySummary> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();

            // First verify the rep exists
            if (!repExists(conn, repEmail)) {
                throw new SQLException("No sales rep found with email: " + repEmail);
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, repEmail);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        OpportunitySummary row = new OpportunitySummary();
                        row.opportunityID      = rs.getInt("opportunityID");
                        row.companyName        = rs.getString("companyName");
                        row.contactName        = rs.getString("contactName");
                        row.stageName          = rs.getString("stageName");
                        row.statusName         = rs.getString("statusName");
                        row.dealAmount         = rs.getDouble("dealAmount");
                        row.expectedCloseDate  = rs.getString("expectedCloseDate");
                        row.actualCloseDate    = rs.getString("actualCloseDate");
                        row.dateCreated        = rs.getString("dateCreated");
                        row.lastUpdated        = rs.getString("lastUpdated");
                        results.add(row);
                    }
                }
            }
        } finally {
            DBConnection.close(conn);
        }

        return results;
    }

    private static boolean repExists(Connection conn, String repEmail) throws SQLException {
        String sql = "SELECT 1 FROM SalesReps WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, repEmail);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
