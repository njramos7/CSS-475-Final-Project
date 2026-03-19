package com.salesanalytics.client;

import com.salesanalytics.server.Server_ListOpportunitiesForRep;
import com.salesanalytics.server.Server_ListOpportunitiesForRep.OpportunitySummary;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Client_ListOpportunitiesForRep {

    // enter point called from the menu.
    public static void run(Scanner scanner) {
        System.out.println("\n--- List Opportunities For Rep ---");
        System.out.print("Enter rep email: ");
        String repEmail = scanner.nextLine().trim();

        if (repEmail.isEmpty()) {
            System.out.println("ERROR: Email cannot be blank.");
            return;
        }

        try {
            List<OpportunitySummary> results = Server_ListOpportunitiesForRep.execute(repEmail);

            if (results.isEmpty()) {
                System.out.println("\nNo opportunities found for: " + repEmail);
                return;
            }

            //driver menu results
            System.out.println("\n========================================================");
            System.out.printf("  OPPORTUNITIES FOR: %s%n", repEmail.toUpperCase());
            System.out.println("========================================================");
            System.out.printf("  Total found: %d%n%n", results.size());

            System.out.printf("  %-6s %-22s %-15s %-15s %14s  %-12s%n",
                    "Opp #", "Company", "Stage", "Status", "Deal Amount", "Exp. Close");
            System.out.println("  " + "-".repeat(90));

            for (OpportunitySummary opp : results) {
                System.out.printf("  %-6d %-22s %-15s %-15s $%,12.2f  %-12s%n",
                        opp.opportunityID,
                        truncate(opp.companyName, 21),
                        truncate(opp.stageName,   14),
                        truncate(opp.statusName,  14),
                        opp.dealAmount,
                        opp.expectedCloseDate != null ? opp.expectedCloseDate : "N/A"
                );
            }

            System.out.println("  " + "-".repeat(90));

            double totalValue = results.stream().mapToDouble(o -> o.dealAmount).sum();
            long   openCount  = results.stream().filter(o -> "Open".equalsIgnoreCase(o.statusName)).count();
            long   wonCount   = results.stream().filter(o -> "Won".equalsIgnoreCase(o.statusName)).count();
            long   lostCount  = results.stream().filter(o -> "Lost".equalsIgnoreCase(o.statusName)).count();

            System.out.printf("%n  Summary:%n");
            System.out.printf("    Open : %d | Won : %d | Lost : %d%n", openCount, wonCount, lostCount);
            System.out.printf("    Total Deal Value (all): $%,.2f%n", totalValue);
            System.out.println();

        } catch (SQLException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}
