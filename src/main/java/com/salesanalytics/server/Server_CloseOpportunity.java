package com.salesanalytics.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.salesanalytics.util.DBConnection;

public class Server_GetCustomerHistory {

    public static void run(String companyName, String contactEmail) {

        String sql = """
        SELECT
            Customers.customerID,
            Customers.companyName,
            Customers.contactName,
            Customers.contactEmail,
            Customers.contactPhone,
            SalesReps.firstName,
            SalesReps.lastName,
            Opportunities.opportunityID,
            Opportunities.dealAmount,
            Opportunities.expectedCloseDate,
            Opportunities.actualCloseDate,
            Opportunities.saleDate,
            OpportunityStatus.statusName,
            OpportunityStage.stageName,
            Interactions.interactionID,
            Interactions.interactionDate,
            Interactions.notes,
            InteractionType.typeName
        FROM Customers
            JOIN SalesReps ON Customers.assignedRepID = SalesReps.salesRepID
            LEFT JOIN Opportunities ON Customers.customerID = Opportunities.customerID
            LEFT JOIN OpportunityStatus ON Opportunities.statusID = OpportunityStatus.statusID
            LEFT JOIN OpportunityStage ON Opportunities.stageID = OpportunityStage.stageID
            LEFT JOIN Interactions ON Customers.customerID = Interactions.customerID
            LEFT JOIN InteractionType ON Interactions.interactionTypeID = InteractionType.interactionTypeID
        WHERE Customers.companyName = ?
            AND Customers.contactEmail = ?
        ORDER BY Opportunities.expectedCloseDate, Interactions.interactionDate
        """;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, companyName);
            statement.setString(2, contactEmail);

            ResultSet rs = statement.executeQuery();

            boolean found = false;

            while (rs.next()) {
                if (!found) {
                    System.out.println("\n---- GetCustomerHistory ---");
                }
                found = true;

                System.out.println("Customer ID: " + rs.getInt("customerID"));
                System.out.println("Company Name: " + rs.getString("companyName"));
                System.out.println("Contact Name: " + rs.getString("contactName"));
                System.out.println("Contact Email: " + rs.getString("contactEmail"));
                System.out.println("Contact Phone: " + rs.getString("contactPhone"));
                System.out.println("Assigned Rep: " + rs.getString("firstName") + " " + rs.getString("lastName"));

                int opportunityID = rs.getInt("opportunityID");
                if (!rs.wasNull()) {
                    System.out.println("Opportunity ID: " + opportunityID);
                    System.out.println("Deal Amount: $" + rs.getDouble("dealAmount"));
                    System.out.println("Expected Close Date: " + rs.getTimestamp("expectedCloseDate"));
                    System.out.println("Actual Close Date: " + rs.getTimestamp("actualCloseDate"));
                    System.out.println("Sale Date: " + rs.getTimestamp("saleDate"));
                    System.out.println("Stage: " + rs.getString("stageName"));
                    System.out.println("Status: " + rs.getString("statusName"));
                }

                int interactionID = rs.getInt("interactionID");
                if (!rs.wasNull()) {
                    System.out.println("Interaction ID: " + interactionID);
                    System.out.println("Interaction Type: " + rs.getString("typeName"));
                    System.out.println("Interaction Date: " + rs.getTimestamp("interactionDate"));
                    System.out.println("Notes: " + rs.getString("notes"));
                }

                System.out.println("-----------------------------------------");
            }

            if (!found) {
                System.out.println("No customer history found.");
            }

            rs.close();
            statement.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error running GetCustomerHistory.");
            e.printStackTrace();
        }
    }
}