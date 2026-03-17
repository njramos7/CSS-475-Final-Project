package com.salesanalytics.client;

import java.util.Scanner;
import java.sql.Timestamp;
import com.salesanalytics.server.Server_CreateOpportunity;

public class Client_CreateOpportunity {

    public static void run(Scanner scanner) {

        System.out.print("Customer ID: ");
        int customerID = Integer.parseInt(scanner.nextLine());

        System.out.print("Sales Rep ID: ");
        int salesRepID = Integer.parseInt(scanner.nextLine());

        System.out.print("Stage ID: ");
        int stageID = Integer.parseInt(scanner.nextLine());

        System.out.print("Status ID: ");
        int statusID = Integer.parseInt(scanner.nextLine());

        System.out.print("Deal Amount: ");
        double dealAmount = Double.parseDouble(scanner.nextLine());

        // Auto-generate timestamp (easy mode)
        Timestamp expectedCloseDate = new Timestamp(System.currentTimeMillis());

        Server_CreateOpportunity.run(
            customerID,
            salesRepID,
            stageID,
            statusID,
            dealAmount,
            expectedCloseDate
        );
    }
}