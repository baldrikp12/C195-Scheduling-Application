package kbaldr2.controller;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//wgu.ucertify.com:3306/";
    private static final String databaseName = "";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String localdDB = "jdbc:mysql://localhost:3306/client_schedule";
    private static final String password = "Passw0rd!"; // Password
    public static Connection connection = null;  // Connection Interface
    
    public static void openConnection() {
        
        try {
            Class.forName(driver); // Locate Driver
            //connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            connection = DriverManager.getConnection(localdDB, "root", "Bypass12"); // Reference Connection object
            System.out.println("DB controller: Connection successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    
    public static void closeConnection() {
        
        try {
            connection.close();
            System.out.println("DB controller: Connection closed!");
        } catch (Exception e) {
            System.out.println("DB controller: Error:" + e.getMessage());
        }
    }
    
    public static Connection getConnection() {
        
        return connection;
    }
    
}
