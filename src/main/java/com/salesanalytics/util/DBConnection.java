package main.java.com.salesanalytics.util;

import java.sql.Connection;
import java.sql.DriverManager;;

public class DBConnection {
    // wrong url urlr url
    private static final String URL = "jdbc:postgresql://salesanalytics.cn6sqqsw4jj5.us-east-2.rds.amazonaws.com:5432/salesanalytics";
    private static final String USER = "postgres";
    // I dont know ???? idkdidkdidk
    private static final String PASSWORD = "Password123";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
