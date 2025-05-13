package org.example;

import java.sql.*;
import java.util.Scanner;

/**
 * This class inserts a new record into the customer_service table.
 *
 * It prompts the user for all required fields (IDs, date, points, and note),
 * validates input using Scanner, and safely inserts data into the database using
 * a PreparedStatement.
 */


public class Notes {

    /**
     * Main method that collects input from the user and inserts a new row into the
     * customer_service table using JDBC.
     *
     * @param args command-line arguments (unused)
     */

    public static void main(String[] args) {
        Connection conn = DBC.connect();

        if (conn == null) {
            System.out.println("Error! Could not connect to database.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        try {
            // Prompt user for each field required by the customer_service table
            System.out.print("Enter Customer Service ID: ");
            int csId = scanner.nextInt();
            scanner.nextLine(); //clear newline

            System.out.print("Enter Client ID: ");
            int clientId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Employee ID: ");
            int employeeId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            Date date = Date.valueOf(dateInput); // convert to SQL Date

            System.out.print("Enter Available Points: ");// Available points are entered by a supervisor as a performance metric out of 5
            int points = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Note: ");
            String note = scanner.nextLine();

            // SQL INSERT statement with placeholders for secure data insertion
            String sql = """
                INSERT INTO customer_service (customer_service_id, client_id, employee_id, date, available_points, notes)
                VALUES (?, ?, ?, ?, ?, ?)
            """;

            // Execute the insert using a prepared statement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, csId);
                pstmt.setInt(2, clientId);
                pstmt.setInt(3, employeeId);
                pstmt.setDate(4, date);
                pstmt.setInt(5, points);
                pstmt.setString(6, note);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Record inserted successfully!");
                } else {
                    System.out.println(" Error! No record was inserted.");
                }
            }
            // Handles SQL errors or invalid date format
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Failed to insert record.");
            e.printStackTrace();
        }

        scanner.close();
    }
}
