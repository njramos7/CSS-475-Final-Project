package com.salesanalytics.client;

import java.util.Scanner;
import com.salesanalytics.server.Server_GetCustomerHistory;

public class Client_GetCustomerHistory {

    public static void run(Scanner scanner) {

        System.out.print("Company Name: ");
        String companyName = scanner.nextLine();

        System.out.print("Contact Email: ");
        String contactEmail = scanner.nextLine();

        Server_GetCustomerHistory.run(companyName, contactEmail);
    }
}