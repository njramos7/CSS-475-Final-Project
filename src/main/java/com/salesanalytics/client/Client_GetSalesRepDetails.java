package com.salesanalytics.client;

import java.util.Scanner;
import com.salesanalytics.server.Server_GetSalesRepDetails;

public class Client_GetSalesRepDetails {

    public static void run(Scanner scanner) {

        System.out.print("Enter Sales Rep Email : ");
        String email = scanner.nextLine();

        Server_GetSalesRepDetails.run(email);
    }
}