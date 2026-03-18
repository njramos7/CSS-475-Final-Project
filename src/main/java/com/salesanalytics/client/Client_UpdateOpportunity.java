package com.salesanalytics.client;

import com.salesanalytics.server.Server_UpdateOpportunity;
import java.util.Scanner;

public class Client_UpdateOpportunity {
    public static void run(Scanner scanner) {
        System.out.println("\n--- Update Opportunity ---");
        System.out.print("Opportunity ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New Stage (leave blank to skip): ");
        String stage = scanner.nextLine();
        System.out.print("New Status (leave blank to skip): ");
        String status = scanner.nextLine();
        System.out.print("New Amount (leave blank to skip): ");
        String amount = scanner.nextLine();
        System.out.print("New Close Date YYYY-MM-DD (leave blank to skip): ");
        String date = scanner.nextLine();
        Server_UpdateOpportunity.run(id, stage, status, amount, date);
    }
}
