package com.salesanalytics.server;

import com.salesanalytics.util.DBConnection;
import java.sql.*;

public class Server_UpdateOpportunity {
    public static void run(
        int opportunityID,
        String stageName,
        String statusName,
        String amount,
        String closeDate
    ) {
        try (Connection conn = DBConnection.getConnection()) {

            Integer stageID = null;
            Integer statusID = null;

            // Get stageID
            if (!stageName.isEmpty()) {
                PreparedStatement ps = conn.prepareStatement(
                    "SELECT stageID FROM OpportunityStage WHERE stageName = ?");
                ps.setString(1, stageName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) stageID = rs.getInt("stageID");
            }

            // Get statusID
            if (!statusName.isEmpty()) {
                PreparedStatement ps = conn.prepareStatement(
                    "SELECT statusID FROM OpportunityStatus WHERE statusName = ?");
                ps.setString(1, statusName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) statusID = rs.getInt("statusID");
            }

            String sql = "UPDATE Opportunities SET ";
            boolean first = true;

            if (stageID != null) {
                sql += "stageID = ?";
                first = false;
            }
            if (statusID != null) {
                if (!first) sql += ", ";
                sql += "statusID = ?";
                first = false;
            }
            if (!amount.isEmpty()) {
                if (!first) sql += ", ";
                sql += "dealAmount = ?";
                first = false;
            }
            if (!closeDate.isEmpty()) {
                if (!first) sql += ", ";
                sql += "expectedCloseDate = ?";
            }

            sql += " WHERE opportunityID = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            int index = 1;
            if (stageID != null)      ps.setInt(index++, stageID);
            if (statusID != null)     ps.setInt(index++, statusID);
            if (!amount.isEmpty())    ps.setDouble(index++, Double.parseDouble(amount));
            if (!closeDate.isEmpty()) ps.setTimestamp(index++, Timestamp.valueOf(closeDate + " 00:00:00"));
            ps.setInt(index, opportunityID);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Opportunity updated!");
            } else {
                System.out.println("Opportunity not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
