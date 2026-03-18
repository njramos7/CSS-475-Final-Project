package com.salesanalytics.server;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import com.salesanalytics.util.DBConnection;

public class Server_ListSalesReps {

    public static void run() {

        String sql = "SELECT salesRepID, firstName, lastName, email FROM SalesReps";

        try {
            Connection con = DBConnection.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            System.out.println("\n------------ Sales Reps ---------------\n");

            while (rs.next()) {
                int id = rs.getInt("salesRepID");
                String first = rs.getString("firstName");
                String last = rs.getString("lastName");
                String email = rs.getString("email");

                // System.out.println(id + " | " + first + " " + last + " | " + email);
                System.out.printf("%-4d %-14s %-10s %-32s\n", id, first, last, email);
            }

            rs.close();
            statement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}