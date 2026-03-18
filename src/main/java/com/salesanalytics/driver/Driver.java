package com.salesanalytics.driver;

import com.salesanalytics.client.Client_ListOpportunitiesForRep;
import com.salesanalytics.client.Client_GetPipelineForecast;
import com.salesanalytics.client.Client_LogInteraction;
import com.salesanalytics.client.Client_UpdateOpportunity;

import java.util.Scanner;

/**
 * Driver
 *
 * Main entry point for the Sales Analytics system.
 * Presents a menu and delegates to Client_<ApiName> methods.
 *
 * CSS 475 Final Project — Team: Chill Guys
 */
public class Driver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     SALES ANALYTICS SYSTEM — CSS 475     ║");
        System.out.println("║           Team: Chill Guys               ║");
        System.out.println("╚══════════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {

                // ── Sales Rep Management ─────────────────────────
                case "1"  -> System.out.println("[Tenzin] CreateSalesRep — not yet wired.");
                case "2"  -> System.out.println("[Tenzin] CreateCustomer — not yet wired.");
                case "3"  -> System.out.println("[Tenzin] CreateOpportunity — not yet wired.");

                // ── Interaction & Opportunity ────────────────────
                case "4"  -> Client_LogInteraction.run(scanner);
                case "5"  -> Client_UpdateOpportunity.run(scanner);

                // ── Close & History ──────────────────────────────
                case "6"  -> System.out.println("[Ryan] CloseOpportunity — not yet wired.");
                case "7"  -> System.out.println("[Ryan] GetCustomerHistory — not yet wired.");

                // ── Joshua's APIs ────────────────────────────────
                case "8"  -> Client_ListOpportunitiesForRep.run(scanner);
                case "9"  -> Client_GetPipelineForecast.run(scanner);

                // ── Analytics ────────────────────────────────────
                case "10" -> System.out.println("[Vito] CalculateRepPerformance — not yet wired.");

                // ── Exit ─────────────────────────────────────────
                case "0"  -> {
                    System.out.println("\nGoodbye!");
                    running = false;
                }

                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    // -------------------------------------------------------
    // Menu
    // -------------------------------------------------------
    private static void printMenu() {
        System.out.println("""

            ┌─────────────────────────────────────────┐
            │           MAIN MENU                     │
            ├─────────────────────────────────────────┤
            │  SALES REP MANAGEMENT                   │
            │   1. Create Sales Rep      (Tenzin)     │
            │   2. Create Customer       (Tenzin)     │
            │   3. Create Opportunity    (Tenzin)     │
            ├─────────────────────────────────────────┤
            │  INTERACTION & OPPORTUNITY              │
            │   4. Log Interaction       (Brian)  ★   │
            │   5. Update Opportunity    (Brian)  ★   │
            ├─────────────────────────────────────────┤
            │  CLOSE & HISTORY                        │
            │   6. Close Opportunity     (Ryan)       │
            │   7. Get Customer History  (Ryan)       │
            ├─────────────────────────────────────────┤
            │  PIPELINE (Joshua)                      │
            │   8. List Opportunities For Rep  ★      │
            │   9. Get Pipeline Forecast       ★      │
            ├─────────────────────────────────────────┤
            │  ANALYTICS                              │
            │  10. Calculate Rep Performance (Vito)   │
            ├─────────────────────────────────────────┤
            │   0. Exit                               │
            └─────────────────────────────────────────┘""");
    }
}