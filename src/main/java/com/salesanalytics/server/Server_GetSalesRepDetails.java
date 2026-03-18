package com.salesanalytics.server;

import java.sql.*;
import com.salesanalytics.util.DBConnection;

public class Server_GetSalesRepDetails {

    public static void run(String email) {

        try {
            Connection con = DBConnection.getConnection();

           // Get Sales Rep
            String repQuery = "SELECT salesRepID, firstName, lastName, email FROM SalesReps WHERE email = ?";
            PreparedStatement repStatement = con.prepareStatement(repQuery);
            repStatement.setString(1, email);

            ResultSet repResultSet = repStatement.executeQuery();

            if (!repResultSet.next()) {
                System.out.println("Sales rep are not found!");
                return;
            }

            int repID = repResultSet.getInt("salesRepID");
            String firstName = repResultSet.getString("firstName");
            String lastName = repResultSet.getString("lastName");

            System.out.println("\n------------- Sales Rep Details ----------------\n");
            System.out.println(firstName + " " + lastName + " || " + email);

            // Get Customers
            String customerQuery = "SELECT companyName, contactName, contactEmail, contactPhone FROM Customers WHERE assignedRepID = ?";
            PreparedStatement customerStatement = con.prepareStatement(customerQuery);
            customerStatement.setInt(1, repID);

            ResultSet customerResultSet = customerStatement.executeQuery();

            System.out.println("\n\n-------- Assigned Customers ----------\n");

            boolean hasCustomers = false;

            while (customerResultSet.next()) {
                hasCustomers = true;

                String company = customerResultSet.getString("companyName");
                String contact = customerResultSet.getString("contactName");
                String contactEmail = customerResultSet.getString("contactEmail");
                String phone = customerResultSet.getString("contactPhone");

                System.out.println(company + " | " + contact + " | " + contactEmail + " | " + phone);
            }

            if (!hasCustomers) {
                System.out.println("No customers assigned!!");
            }

            // Cleaning up all the resources used :|
            customerResultSet.close();
            customerStatement.close();
            repResultSet.close();
            repStatement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}