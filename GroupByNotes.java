package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class connects to the database and runs a GROUP BY query to count
 * how many notes each employee has handled in the customer_service table.
 */

public class GroupByNotes {

    /**
     * Main method that connects to the database, executes a GROUP BY query,
     * and displays the number of notes per employee.
     *
     * @param args command-line arguments (unused)
     */

    public static void main(String[] args) {
        Connection conn = DBC.connect();

        if (conn == null) {
            System.out.println("Error! Could not connect to the database.");
            return;
        }

        // SQL query to count notes per employee
        String sql = """
            SELECT employee_id, COUNT(*) AS note_count
            FROM customer_service
            GROUP BY employee_id
            ORDER BY employee_id;
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n=== Notes Count Per Employee ===");

            // Loop through the result set and display the count
            while (rs.next()) {
                int empId = rs.getInt("employee_id");
                int count = rs.getInt("note_count");

                System.out.println("Employee ID: " + empId + " | Note Count: " + count);
            }

            // Handles database errors and prints the full error
        } catch (SQLException e) {
            System.out.println("Error! Failed to run GROUP BY query.");
            e.printStackTrace();
        }
    }
}
