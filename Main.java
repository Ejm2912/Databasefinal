package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Main class that connects to the PostgreSQL database and retrieves
 * all customer service notes using a basic SELECT query.
 *
 * This class demonstrates a simple read operation from the database
 * using JDBC and outputs the results to the console.
 */

public class Main {
    /**
     * Main method that establishes the database connection and
     * executes a SELECT statement to retrieve customer service notes.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {

        // Attempt to connect to the PostgreSQL database using DBC  class
        Connection conn = DBC.connect();

        if (conn == null) {
            System.out.println(" Could not establish DB connection.");
            return;
        }

        // Execute a basic SELECT query to get all notes from the customer service table
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT customer_service_id, notes FROM customer_service")) {

            System.out.println("Connected to PostgreSQL!");
            System.out.println("--- Customer Service Notes ---");

            // Loop through the results and print each record
            while (rs.next()) {
                int id = rs.getInt("customer_service_id");
                String note = rs.getString("notes");
                System.out.println("ID: " + id + " | Note: " + note);
            }

        } catch (SQLException e) {
            System.out.println("Query failed.");
            e.printStackTrace();
        }
    }
}
