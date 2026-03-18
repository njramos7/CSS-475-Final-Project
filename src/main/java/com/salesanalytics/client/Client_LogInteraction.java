package com.salesanalytics.client;

import com.salesanalytics.server.Server_LogInteraction;
import java.util.Scanner;

public class Client_LogInteraction {
    public static void run(Scanner scanner) {
        System.out.println("\n--- Log Interaction ---");
        System.out.print("Company Name: ");
        String companyName = scanner.nextLine();
        System.out.print("Contact Email: ");
        String contactEmail = scanner.nextLine();
        System.out.print("Rep Email: ");
        String repEmail = scanner.nextLine();
        System.out.print("Interaction Type (Call/Email/Meeting): ");
        String type = scanner.nextLine();
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Notes: ");
        String notes = scanner.nextLine();
        Server_LogInteraction.run(companyName, contactEmail, repEmail, type, date, notes);
    }
}
