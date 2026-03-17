package com.salesanalytics.client;

import java.util.Scanner;
import com.salesanalytics.server.Server_CreateSalesRep;

public class Client_CreateSalesRep {

    public static void run(Scanner scanner) {

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Server_CreateSalesRep.run(firstName, lastName, email);
    }
}