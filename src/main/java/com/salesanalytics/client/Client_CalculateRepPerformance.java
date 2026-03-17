package com.salesanalytics.client;

import java.sql.Date;
import java.sql.Timestamp;
import com.salesanalytics.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Client_CalculateRepPerformance {

    public static void run(Scanner scanner) {
        System.out.print("Enter sales rep email: ");
        String repEmail = scanner.nextLine().trim();

        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine().trim();

        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine().trim();

        String sql = """
            SELECT
                sr.email,
                COALESCE(SUM(CASE
                    WHEN os.statusName = 'Won' THEN o.dealAmount
                    ELSE 0
                END), 0) AS total_revenue,
                COUNT(CASE
                    WHEN os.statusName IN ('Won', 'Lost') THEN 1
                END) AS closed_opportunities,
                ROUND(
                    100.0 * COUNT(CASE WHEN os.statusName = 'Won' THEN 1 END)
                    / NULLIF(COUNT(CASE WHEN os.statusName IN ('Won', 'Lost') THEN 1 END), 0),
                    2
                ) AS win_rate,
                ROUND(AVG(CASE
                    WHEN os.statusName = 'Won' THEN o.dealAmount
                    ELSE NULL
                END), 2) AS avg_deal_size
            FROM SalesReps sr
            JOIN Opportunities o ON sr.salesRepID = o.salesRepID
            JOIN OpportunityStatus os ON o.statusID = os.statusID
            WHERE sr.email = ?
              AND o.actualCloseDate BETWEEN CAST(? AS timestamp) AND CAST(? AS timestamp)
            GROUP BY sr.email
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, repEmail);
            stmt.setString(2, startDate + " 00:00:00");
            stmt.setString(3, endDate + " 23:59:59");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n=== CalculateRepPerformance ===");
                System.out.println("Sales Rep: " + rs.getString("email"));
                System.out.println("Total Revenue: $" + rs.getDouble("total_revenue"));
                System.out.println("Closed Opportunities: " + rs.getInt("closed_opportunities"));
                System.out.println("Win Rate: " + rs.getDouble("win_rate") + "%");
                System.out.println("Average Deal Size: $" + rs.getDouble("avg_deal_size"));
            } else {
                System.out.println("No results found for that sales rep and date range.");
            }

        } catch (Exception e) {
            System.out.println("Error running CalculateRepPerformance.");
            e.printStackTrace();
        }
    }
}

