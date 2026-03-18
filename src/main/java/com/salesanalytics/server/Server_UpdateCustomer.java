package com.salesanalytics.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.salesanalytics.util.DBConnection;

public class Server_UpdateCustomer {

    public static void run(
            String companyName,
            String contactEmail,
            String newCompanyName,
            String newContactName,
            String newContactEmail,
            String newContactPhone) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE Customers SET " +
                    "companyName = COALESCE(NULLIF(?, ''), companyName), " +
                    "contactName = COALESCE(NULLIF(?, ''), contactName), " +
                    "contactEmail = COALESCE(NULLIF(?, ''), contactEmail), " +
                    "contactPhone = COALESCE(NULLIF(?, ''), contactPhone) " +
                    "WHERE companyName = ? AND contactEmail = ?";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, newCompanyName);
            statement.setString(2, newContactName);
            statement.setString(3, newContactEmail);
            statement.setString(4, newContactPhone);
            statement.setString(5, companyName);
            statement.setString(6, contactEmail);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Customer updated successfully!");
            } else {
                System.out.println("Customer not found.");
            }

            statement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}