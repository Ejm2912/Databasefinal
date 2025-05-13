package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DBC (Database Connector) class used to establish a connection to the PostgreSQL database.
 *
 * This class handles the database connection so other classes can use it without repeating code.
 */

public class DBC {

    /**
     * Establishes and returns a connection to the PostgreSQL database using the provided URL and credentials.
     *
     * @return a Connection object if successful, or null if the connection fails.
     */

    public static Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/database_dashboard";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "1234");

        try {
            return DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.out.println(" Connection failed.");
            e.printStackTrace();
            return null;
        }
    }
}

