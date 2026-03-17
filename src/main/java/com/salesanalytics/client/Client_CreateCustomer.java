package com.salesanalytics.client;

import java.util.Scanner;
import com.salesanalytics.server.Server_CreateCustomer;

public class Client_CreateCustomer {

    public static void run(Scanner scanner) {

        System.out.print("Company Name: ");
        String companyName = scanner.nextLine();

        System.out.print("Contact Name: ");
        String contactName = scanner.nextLine();

        System.out.print("Contact Email: ");
        String contactEmail = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Assigned Rep ID: ");
        int repID = Integer.parseInt(scanner.nextLine());

        Server_CreateCustomer.run(companyName, contactName, contactEmail, phone, repID);
    }
}