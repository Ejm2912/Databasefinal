package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * This class updates the note field in the customer_service table for a specific record.
 *
 * The user is prompted to enter the customer_service_id to locate the record, and then
 * enter a new note to replace the existing one.
 */

public class UpdateNotes {

    /**
     * Main method that connects to the database and updates a customer service note
     * based on user input.
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

        // Ask for the record ID to update
        System.out.print("Enter the Customer Service ID to update: ");
        int csId = scanner.nextInt();
        scanner.nextLine();

        // Ask for the new note content
        System.out.print("Enter the new note: ");
        String newNote = scanner.nextLine();

        // SQL update statement to change the note based on the given ID
        String sql = "UPDATE customer_service SET notes = ? WHERE customer_service_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newNote);
            pstmt.setInt(2, csId);

            int rowsUpdated = pstmt.executeUpdate();

            // Confirm if update was successful
            if (rowsUpdated > 0) {
                System.out.println(" Notes updated successfully for ID: " + csId);
            } else {
                System.out.println(" No record was found with customer_service_id: " + csId);
            }
            // Handles SQL errors
        } catch (SQLException e) {
            System.out.println("Error! Failed to update note.");
            e.printStackTrace();
        }

        scanner.close();
    }
}
