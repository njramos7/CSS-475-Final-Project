package com.salesanalytics.driver;

import com.salesanalytics.client.Client_CreateSalesRep;
import com.salesanalytics.client.Client_UpdateSalesRep;
import com.salesanalytics.client.Client_ListSalesReps;
import com.salesanalytics.client.Client_GetSalesRepDetails;
import com.salesanalytics.client.Client_CreateCustomer;
import com.salesanalytics.client.Client_UpdateCustomer;
import com.salesanalytics.client.Client_GetCustomerHistory;
import com.salesanalytics.client.Client_CreateOpportunity;
import com.salesanalytics.client.Client_UpdateOpportunity;
import com.salesanalytics.client.Client_CloseOpportunity;
import com.salesanalytics.client.Client_ListOpportunitiesForRep;
import com.salesanalytics.client.Client_LogInteraction;
import com.salesanalytics.client.Client_GetPipelineForecast;
import com.salesanalytics.client.Client_CalculateRepPerformance;

import java.util.Scanner;

/**
 * Driver
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
                case "1"  -> Client_CreateSalesRep.run(scanner);
                case "2"  -> Client_UpdateSalesRep.run(scanner);
                case "3"  -> Client_ListSalesReps.run(scanner);
                case "4"  -> Client_GetSalesRepDetails.run(scanner);

                // ── Customer Management ──────────────────────────
                case "5"  -> Client_CreateCustomer.run(scanner);
                case "6"  -> Client_UpdateCustomer.run(scanner);
                case "7"  -> Client_GetCustomerHistory.run(scanner);

                // ── Opportunity Management ───────────────────────
                case "8"  -> Client_CreateOpportunity.run(scanner);
                case "9"  -> Client_UpdateOpportunity.run(scanner);
                case "10" -> Client_CloseOpportunity.run(scanner);
                case "11" -> Client_ListOpportunitiesForRep.run(scanner);

                // ── Interaction & Analytics ──────────────────────
                case "12" -> Client_LogInteraction.run(scanner);
                case "13" -> Client_GetPipelineForecast.run(scanner);
                case "14" -> Client_CalculateRepPerformance.run(scanner);

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

    private static void printMenu() {
        System.out.println("""

            ┌─────────────────────────────────────────┐
            │           MAIN MENU                     │
            ├─────────────────────────────────────────┤
            │  SALES REP MANAGEMENT                   │
            │   1.  Create Sales Rep              ★   │
            │   2.  Update Sales Rep              ★   │
            │   3.  List Sales Reps               ★   │
            │   4.  Get Sales Rep Detail          ★   │
            ├─────────────────────────────────────────┤
            │  CUSTOMER MANAGEMENT                    │
            │   5.  Create Customer               ★   │
            │   6.  Update Customer               ★   │
            │   7.  Get Customer History          ★   │
            ├─────────────────────────────────────────┤
            │  OPPORTUNITY MANAGEMENT                 │
            │   8.  Create Opportunity            ★   │
            │   9.  Update Opportunity            ★   │
            │  10.  Close Opportunity             ★   │
            │  11.  List Opps For Rep             ★   │
            ├─────────────────────────────────────────┤
            │  INTERACTION & ANALYTICS                │
            │  12.  Log Interaction               ★   │
            │  13.  Get Pipeline Forecast.        ★   │
            │  14.  Calc Rep Performance          ★   │
            ├─────────────────────────────────────────┤
            │   0.  Exit                              │
            └─────────────────────────────────────────┘""");
    }
}
