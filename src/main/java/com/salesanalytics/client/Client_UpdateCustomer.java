package com.salesanalytics.client;

import java.util.Scanner;
import com.salesanalytics.server.Server_UpdateCustomer;

public class Client_UpdateCustomer {

    public static void run(Scanner scanner) {

        System.out.print("Current Company Name: ");
        String companyName = scanner.nextLine();

        System.out.print("Current Contact Email: ");
        String contactEmail = scanner.nextLine();

        System.out.println("Leave fields blank to keep current values.");

        System.out.print("New Company Name: ");
        String newCompanyName = scanner.nextLine();

        System.out.print("New Contact Name: ");
        String newContactName = scanner.nextLine();

        System.out.print("New Contact Email: ");
        String newContactEmail = scanner.nextLine();

        System.out.print("New Contact Phone: ");
        String newContactPhone = scanner.nextLine();

        Server_UpdateCustomer.run(
                companyName,
                contactEmail,
                newCompanyName,
                newContactName,
                newContactEmail,
                newContactPhone);
    }
}