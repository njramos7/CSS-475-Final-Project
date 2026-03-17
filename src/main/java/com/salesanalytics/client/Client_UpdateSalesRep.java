package com.salesanalytics.client;

import java.util.Scanner;
import com.salesanalytics.server.Server_UpdateSalesRep;

public class Client_UpdateSalesRep {

    public static void run(Scanner scanner) {

        System.out.print("Enter current email: ");
        String oldEmail = scanner.nextLine();

        System.out.print("New First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("New Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("New Email: ");
        String newEmail = scanner.nextLine();

        Server_UpdateSalesRep.run(oldEmail, firstName, lastName, newEmail);
    }
}