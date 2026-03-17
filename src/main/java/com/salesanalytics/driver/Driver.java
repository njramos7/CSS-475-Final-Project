package com.salesanalytics.driver;

import java.util.Scanner;

// import com.salesanalytics.client.Client_CreateSalesRep;
// import com.salesanalytics.client.Client_UpdateSalesRep;
// import com.salesanalytics.client.Client_ListSalesReps;
// import com.salesanalytics.client.Client_GetSalesRepDetails;

// import com.salesanalytics.client.Client_CreateCustomer;
// import com.salesanalytics.client.Client_UpdateCustomer;
// import com.salesanalytics.client.Client_GetCustomerHistory;

// import com.salesanalytics.client.Client_CreateOpportunity;
// import com.salesanalytics.client.Client_UpdateOpportunity;
// import com.salesanalytics.client.Client_ListOpportunitiesForRep;
// import com.salesanalytics.client.Client_CloseOpportunity;

// import com.salesanalytics.client.Client_LogInteraction;
// import com.salesanalytics.client.Client_GetPipelineForecast;
// import com.salesanalytics.client.Client_CalculateRepPerformance;

public class Driver {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int choice = -1;

        System.out.println("-----------------------------------------");
        System.out.println(" Welcome to the Sales Analytics System");
        System.out.println(" CSS 475 Final Project - Chill Guys");
        System.out.println("-----------------------------------------\n");

        while (choice != 0) {

            System.out.println("--------------- Main Menu ---------------");

            System.out.println("Sales Rep Management");
            System.out.println("1. Create Sales Rep");
            System.out.println("2. Update Sales Rep");
            System.out.println("3. List Sales Reps");
            System.out.println("4. Get Sales Rep Details");
            System.out.println();

            System.out.println("Customer Management");
            System.out.println("5. Create Customer");
            System.out.println("6. Update Customer");
            System.out.println("7. Get Customer History");
            System.out.println();

            System.out.println("Opportunity Management");
            System.out.println("8. Create Opportunity");
            System.out.println("9. Update Opportunity");
            System.out.println("10. List Opportunities For Rep");
            System.out.println("11. Close Opportunity");
            System.out.println();

            System.out.println("Interaction / Analytics");
            System.out.println("12. Log Interaction");
            System.out.println("13. Get Pipeline Forecast");
            System.out.println("14. Calculate Rep Performance");
            System.out.println();

            System.out.println("0. Exit");
            System.out.println("-----------------------------------------");

            System.out.print("\nEnter your choice: ");

            if (scan.hasNextInt()) {
                choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
/*
                    case 1:
                        Client_CreateSalesRep.run(scan);
                        break;

                    case 2:
                        Client_UpdateSalesRep.run(scan);
                        break;

                    case 3:
                        Client_ListSalesReps.run(scan);
                        break;

                    case 4:
                        Client_GetSalesRepDetails.run(scan);
                        break;

                    case 5:
                        Client_CreateCustomer.run(scan);
                        break;

                    case 6:
                        Client_UpdateCustomer.run(scan);
                        break;

                    case 7:
                        Client_GetCustomerHistory.run(scan);
                        break;

                    case 8:
                        Client_CreateOpportunity.run(scan);
                        break;

                    case 9:
                        Client_UpdateOpportunity.run(scan);
                        break;

                    case 10:
                        Client_ListOpportunitiesForRep.run(scan);
                        break;

                    case 11:
                        Client_CloseOpportunity.run(scan);
                        break;

                    case 12:
                        Client_LogInteraction.run(scan);
                        break;

                    case 13:
                        Client_GetPipelineForecast.run(scan);
                        break;

                    case 14:
                        Client_CalculateRepPerformance.run(scan);
                        break;
*/
                    case 0:
                        System.out.println("\nExiting system. Goodbye.");
                        break;

                    default:
                        System.out.println("\nInvalid option. Please try again.");
                }

            } else {
                System.out.println("\nPlease enter a number.");
                scan.nextLine();
            }

            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                scan.nextLine();
            }
        }

        scan.close();
    }
}