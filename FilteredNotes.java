package org.example;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.IOException;

/**
 * This class filters customer service notes based on a keyword entered by the user.
 *
 * It searches the PostgreSQL database using a case-insensitive query and writes the
 * matching results to a text file called "filtered_notes.txt". If no matches are found,
 * an Error message is written to the file instead.
 */

public class FilteredNotes {

    /**
     * Main method that connects to the database, prompts the user for a search keyword,
     * filters the notes using a SELECT statement, and writes the results to a file.
     *
     * @param args command-line arguments (unused)
     */

    public static void main(String[] args) {
    Connection conn = DBC.connect();

    if (conn == null) {
        System.out.println(" Error! Could not establish DB connection.");
        return;
    }

        //  Ask the user for a keyword
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a keyword to search in customer service notes: ");
        String keyword = scanner.nextLine().trim();

     //SQL query
    String sql = "SELECT customer_service_id, notes FROM customer_service WHERE notes ILIKE ?";

    //  create a file writer to output the results
    try (PreparedStatement pstmt = conn.prepareStatement(sql);
        FileWriter writer = new FileWriter("filtered_notes.txt")){
        pstmt.setString(1, "%" + keyword + "%");
        ResultSet rs = pstmt.executeQuery();

        System.out.println(" Results of Notes containing: " + keyword + "' ");

        boolean hasResults = false;

        // Loop through the result set and write matching notes to the file
        while (rs.next()) {
            hasResults = true;
            int id = rs.getInt("customer_service_id");
            String note = rs.getString("notes");
            if (note == null || note.trim().isEmpty()) {
                note = "No notes available.";
            }
            String line = "ID: " + id + " | Note: " + note;
            System.out.println(line);
            writer.write(line + "\n");
        }
        // If no results were found Error message
        if(!hasResults){
            String message = "Error! No matching results found for:" + keyword;
            System.out.println(message);
            writer.write(message + "\n");}
        else {
            System.out.println("Results saved to 'filtered_notes.txt'");
        }

        // Handles database and file errors and prints the full error
    } catch (SQLException | IOException e) {
        System.out.println(" Error! Query failed.");
        e.printStackTrace();
    }
    scanner.close();
}
}


