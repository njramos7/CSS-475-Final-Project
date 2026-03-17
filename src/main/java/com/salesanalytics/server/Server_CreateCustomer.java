package com.salesanalytics.server;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.salesanalytics.util.DBConnection;

public class Server_CreateCustomer {
    public static void run(String companyName, String contactName, String contactEmail, String contactPhone,
            int repID) {

        String sql = "INSERT INTO Customers (companyName, contactName, contactEmail, contactPhone, assignedRepID) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, companyName);
            statement.setString(2, contactName);
            statement.setString(3, contactEmail);
            statement.setString(4, contactPhone);
            statement.setInt(5, repID);

            statement.executeUpdate();

            System.out.println("Customer created!");

            statement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
