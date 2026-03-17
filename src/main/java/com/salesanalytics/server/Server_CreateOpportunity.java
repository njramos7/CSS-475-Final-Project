package com.salesanalytics.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.salesanalytics.util.DBConnection;


public class Server_CreateOpportunity {
    public static void run(int customerID, int salesRepID, int stageID, int statusID, double dealAmount, Timestamp expectedCloseDate) {
    
        String sql = "INSERT INTO Opportunities (customerID, salesRepID, stageID, statusID, dealAmount, expectedCloseDate) VALUES (?, ?, ?, ?, ?, ?)";
    
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
    
            statement.setInt(1, customerID);
            statement.setInt(2, salesRepID);
            statement.setInt(3, stageID);
            statement.setInt(4, statusID);
            statement.setDouble(5, dealAmount);
            statement.setTimestamp(6, expectedCloseDate);
    
            statement.executeUpdate();
    
            System.out.println("Opportunity created!");
    
            statement.close();
            con.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
