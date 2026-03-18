package com.salesanalytics.server;

import com.salesanalytics.util.DBConnection;
import java.sql.*;

public class Server_LogInteraction {
    public static void run(
        String companyName,
        String contactEmail,
        String repEmail,
        String interactionTypeName,
        String interactionDate,
        String notes
    ) {
        try (Connection conn = DBConnection.getConnection()) {

            // 1. Get customerID
            PreparedStatement ps1 = conn.prepareStatement(
                "SELECT customerID FROM Customers WHERE companyName = ? AND contactEmail = ?");
            ps1.setString(1, companyName);
            ps1.setString(2, contactEmail);
            ResultSet rs1 = ps1.executeQuery();
            if (!rs1.next()) {
                System.out.println("Customer not found.");
                return;
            }
            int customerID = rs1.getInt("customerID");

            // 2. Get salesRepID
            PreparedStatement ps2 = conn.prepareStatement(
                "SELECT salesRepID FROM SalesReps WHERE email = ?");
            ps2.setString(1, repEmail);
            ResultSet rs2 = ps2.executeQuery();
            if (!rs2.next()) {
                System.out.println("Sales rep not found.");
                return;
            }
            int salesRepID = rs2.getInt("salesRepID");

            // 3. Get interactionTypeID
            PreparedStatement ps3 = conn.prepareStatement(
                "SELECT interactionTypeID FROM InteractionType WHERE typeName = ?");
            ps3.setString(1, interactionTypeName);
            ResultSet rs3 = ps3.executeQuery();
            if (!rs3.next()) {
                System.out.println("Invalid interaction type.");
                return;
            }
            int interactionTypeID = rs3.getInt("interactionTypeID");

            // 4. Insert interaction
            PreparedStatement ps4 = conn.prepareStatement(
                "INSERT INTO Interactions (customerID, salesRepID, interactionTypeID, interactionDate, notes) VALUES (?, ?, ?, ?, ?)");
            ps4.setInt(1, customerID);
            ps4.setInt(2, salesRepID);
            ps4.setInt(3, interactionTypeID);
            ps4.setTimestamp(4, Timestamp.valueOf(interactionDate + " 00:00:00"));
            ps4.setString(5, notes);
            ps4.executeUpdate();
            System.out.println("Interaction logged successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
