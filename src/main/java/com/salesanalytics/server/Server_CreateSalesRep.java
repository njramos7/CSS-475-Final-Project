package com.salesanalytics.server;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.salesanalytics.util.DBConnection;

public class Server_CreateSalesRep {
    public static void run(String firstName, String lastName, String email) {

        String sql = "INSERT INTO SalesReps (firstName, lastName, email) VALUES (?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);

            statement.executeUpdate();

            System.out.println("Sales Rep has been created!");

            statement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
