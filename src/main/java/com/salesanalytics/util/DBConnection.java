package com.salesanalytics.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection - Manages PostgreSQL connection to AWS RDS.
 *
 * Update DB_HOST with your actual AWS RDS endpoint.
 * Credentials are read from environment variables for security.
 *
 * Usage:
 *   Connection conn = DBConnection.getConnection();
 */
public class DBConnection {

    // -------------------------------------------------------
    // UPDATE THIS with your AWS RDS endpoint (from console)
    // Example: mydb.abc123xyz.us-west-2.rds.amazonaws.com
    // -------------------------------------------------------
    private static final String DB_HOST = System.getenv().getOrDefault(
            "DB_HOST", "salesanalytics.cn6sqqsw4jj5.us-east-2.rds.amazonaws.com");

    private static final String DB_PORT = "5432";
    private static final String DB_NAME = "salesanalytics";

    // Set these as environment variables, or replace with literal strings for testing
    private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "postgres");
    private static final String DB_PASS = System.getenv().getOrDefault("DB_PASS", "");
    
    private static final String JDBC_URL =
            "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    private DBConnection() {} // Utility class — no instantiation

    /**
     * Returns a new JDBC connection to the AWS RDS PostgreSQL database.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC driver not found. Add postgresql jar to classpath.", e);
        }
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    /**
     * Quietly closes a connection (null-safe).
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }
}
