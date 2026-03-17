package com.salesanalytics.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.salesanalytics.util.DBConnection;

public class Server_UpdateSalesRep {

    public static void run(String oldEmail, String newFirstName, String newLastName, String newEmail) {

        String sql = "UPDATE SalesReps SET firstName = ?, lastName = ?, email = ? WHERE email = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, newFirstName);
            statement.setString(2, newLastName);
            statement.setString(3, newEmail);
            statement.setString(4, oldEmail);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Sales Rep updated!");
            } else {
                System.out.println("No matching sales rep found.");
            }

            statement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}