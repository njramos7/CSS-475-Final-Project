package com.salesanalytics.client;

import java.util.Scanner;
import com.salesanalytics.server.Server_CloseOpportunity;

public class Client_CloseOpportunity {

    public static void run(Scanner scanner) {

        System.out.print("Opportunity ID: ");
        int opportunityID = Integer.parseInt(scanner.nextLine());

        System.out.print("Outcome Status (Won/Lost): ");
        String outcomeStatus = scanner.nextLine();

        Server_CloseOpportunity.run(opportunityID, outcomeStatus);
    }
}