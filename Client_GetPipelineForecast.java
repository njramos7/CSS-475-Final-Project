package com.salesanalytics.client;

import com.salesanalytics.server.Server_GetPipelineForecast;
import com.salesanalytics.server.Server_GetPipelineForecast.ForecastResult;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Client_GetPipelineForecast
 *
 * Prompts the user for an optional rep email, calls the server,
 * and displays the pipeline forecast grouped by stage.
 *
 * API Type : Complex Query (60 pts)
 * Author   : Joshua Ramos
 */
public class Client_GetPipelineForecast {

    /**
     * Entry point called from the Driver menu.
     */
    public static void run(Scanner scanner) {
        System.out.println("\n--- Get Pipeline Forecast ---");
        System.out.println("Enter rep email to filter by one rep, or press ENTER for all reps:");
        System.out.print("Rep email (optional): ");
        String repEmail = scanner.nextLine().trim();

        // Treat blank as "all reps"
        String emailParam = repEmail.isEmpty() ? null : repEmail;

        try {
            ForecastResult result = Server_GetPipelineForecast.execute(emailParam);

            // --------------------------------------------------
            // Display
            // --------------------------------------------------
            String scope = (emailParam != null)
                    ? "Rep: " + result.repName + " <" + emailParam + ">"
                    : "All Reps (Company-Wide)";

            System.out.println("\n========================================================");
            System.out.printf("  PIPELINE FORECAST — %s%n", scope);
            System.out.println("========================================================");

            if (result.totalOpportunities == 0) {
                System.out.println("  No open opportunities found.");
                return;
            }

            System.out.printf("  Total Open Opportunities : %d%n", result.totalOpportunities);
            System.out.printf("  Total Pipeline Value     : $%,.2f%n%n", result.totalPipelineValue);

            System.out.printf("  %-20s %16s %14s%n", "Stage", "# Opportunities", "Value");
            System.out.println("  " + "-".repeat(55));

            for (Server_GetPipelineForecast.StageBreakdown stage : result.byStage) {
                System.out.printf("  %-20s %16d %14s%n",
                        stage.stageName,
                        stage.opportunityCount,
                        String.format("$%,.2f", stage.totalValue)
                );
            }

            System.out.println("  " + "-".repeat(55));
            System.out.printf("  %-20s %16d %14s%n",
                    "TOTAL",
                    result.totalOpportunities,
                    String.format("$%,.2f", result.totalPipelineValue)
            );
            System.out.println();

        } catch (SQLException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }
}
