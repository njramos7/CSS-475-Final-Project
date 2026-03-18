package com.salesanalytics.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.salesanalytics.util.DBConnection;

public class Server_CloseOpportunity {

    public static void run(int opportunityID, String outcomeStatus) {

        String getStatusSql = """
        SELECT statusID
        FROM OpportunityStatus
        WHERE LOWER(statusName) = LOWER(?)
        """;

        String getStageSql = """
        SELECT stageID
        FROM OpportunityStage
        WHERE LOWER(stageName) = LOWER('Closed')
        """;

        String updateSql = """
        UPDATE Opportunities
        SET statusID = ?,
            stageID = ?,
            actualCloseDate = CURRENT_TIMESTAMP,
            saleDate = CURRENT_TIMESTAMP,
            lastUpdated = CURRENT_TIMESTAMP
        WHERE opportunityID = ?
        """;

        try {
            Connection con = DBConnection.getConnection();

            int statusID = -1;
            int stageID = -1;

            PreparedStatement statusStatement = con.prepareStatement(getStatusSql);
            statusStatement.setString(1, outcomeStatus);
            ResultSet statusResult = statusStatement.executeQuery();

            if (statusResult.next()) {
                statusID = statusResult.getInt("statusID");
            } else {
                System.out.println("Invalid status. Use Won or Lost.");
                statusResult.close();
                statusStatement.close();
                con.close();
                return;
            }

            PreparedStatement stageStatement = con.prepareStatement(getStageSql);
            ResultSet stageResult = stageStatement.executeQuery();

            if (stageResult.next()) {
                stageID = stageResult.getInt("stageID");
            } else {
                System.out.println("Closed stage not found.");
                statusResult.close();
                statusStatement.close();
                stageResult.close();
                stageStatement.close();
                con.close();
                return;
            }

            PreparedStatement updateStatement = con.prepareStatement(updateSql);
            updateStatement.setInt(1, statusID);
            updateStatement.setInt(2, stageID);
            updateStatement.setInt(3, opportunityID);

            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Opportunity closed successfully.");
            } else {
                System.out.println("Opportunity not found.");
            }

            statusResult.close();
            statusStatement.close();
            stageResult.close();
            stageStatement.close();
            updateStatement.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error running CloseOpportunity.");
            e.printStackTrace();
        }
    }
}