package com.salesanalytics.driver;

import com.salesanalytics.client.Client_ListOpportunitiesForRep;
import com.salesanalytics.client.Client_GetPipelineForecast;
import com.salesanalytics.client.Client_CalculateRepPerformance;
import com.salesanalytics.client.Client_CreateSalesRep;
import com.salesanalytics.client.Client_CreateCustomer;
import com.salesanalytics.client.Client_CreateOpportunity;
import com.salesanalytics.client.Client_UpdateSalesRep;
import com.salesanalytics.client.Client_ListSalesReps;
import com.salesanalytics.client.Client_GetSalesRepDetails;
import com.salesanalytics.client.Client_UpdateCustomer;

import java.util.Scanner;

/**
 * Driver
 *
 * Main entry point for the Sales Analytics system.
 * Presents a menu and delegates to Client_<ApiName> methods.
 *
 * To add your teammates' APIs:
 *   1. Import their Client class
 *   2. Add a menu option
 *   3. Call Client_XYZ.run(scanner) in the switch
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
                // case "1"  -> System.out.println("[Tenzin] CreateSalesRep — not yet wired.");
                // case "2"  -> System.out.println("[Tenzin] CreateCustomer — not yet wired.");
                // case "3"  -> System.out.println("[Tenzin] CreateOpportunity — not yet wired.");
                case "1"  -> Client_CreateSalesRep.run(scanner);
                case "11" -> Client_UpdateSalesRep.run(scanner); //update things
                case "12" -> Client_ListSalesReps.run(scanner); // some thngs tngs
                case "13" -> Client_GetSalesRepDetails.run(scanner); // things bro
                case "2"  -> Client_CreateCustomer.run(scanner);
                case "14" -> Client_UpdateCustomer.run(scanner); //update things dude
                case "3"  -> Client_CreateOpportunity.run(scanner);

                // ── Interaction & Opportunity ────────────────────
                case "4"  -> System.out.println("[Brian] LogInteraction — not yet wired.");
                case "5"  -> System.out.println("[Brian] UpdateOpportunity — not yet wired.");

                // ── Close & History ──────────────────────────────
                case "6"  -> Client_CloseOpportunity.run(scanner);
                case "7"  -> System.out.println("[Ryan] GetCustomerHistory — not yet wired.");

                // ── Joshua's APIs ────────────────────────────────
                case "8"  -> Client_ListOpportunitiesForRep.run(scanner);
                case "9"  -> Client_GetPipelineForecast.run(scanner);

                // ── Analytics ────────────────────────────────────
                case "10" -> Client_CalculateRepPerformance.run(scanner);

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
            │  11. Update Sales Rep      (Tenzin)     │
            │  12. List Sales Reps       (Tenzin)     │
            │  13. Get Sales Rep Details (Tenzin)     │
            │   2. Create Customer       (Tenzin)     │
            │  14. Update Customer       (Tenzin)     │
            │   3. Create Opportunity    (Tenzin)     │
            ├─────────────────────────────────────────┤
            │  INTERACTION & OPPORTUNITY              │
            │   4. Log Interaction       (Brian)      │
            │   5. Update Opportunity    (Brian)      │
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
