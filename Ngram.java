package org.example;

import java.sql.*;
import java.util.Scanner;

/**
 * This class calls the custom SQL function get_unigram_count(word)
 * to count how many times a specific word appears in all customer service notes.
 *
 * The user enters a word, and the program queries the database and
 * displays the word frequency results.
 */


public class Ngram {

    /**
     * Main method that prompts the user for a word and calls the PostgreSQL function
     * get_unigram_count() to retrieve how often that word appears in the notes column.
     *
     * @param args command-line arguments (unused)
     */

    public static void main(String[] args) {
        Connection conn = DBC.connect();

        if (conn == null) {
            System.out.println("Error! Could not connect to the database.");
            return;
        }

        // Ask the user to enter a word to search for
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word to count (e.g., agent): ");
        String word = scanner.nextLine().trim();

        // SQL call to the user-defined function
        String sql = "SELECT * FROM get_unigram_count(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n=== Frequency of '" + word + "' ===");
            boolean found = false;

            // Loop through and display the result
            while (rs.next()) {
                found = true;
                String resultWord = rs.getString("word");
                int count = rs.getInt("count");

                System.out.println("Word: " + resultWord + " | Count: " + count);
            }

            if (!found) {
                System.out.println("Word not found in any notes.");
            }

            // Handles errors from the function call or database
        } catch (SQLException e) {
            System.out.println("Error! Failed to call get_unigram_count function.");
            e.printStackTrace();
        }

        scanner.close();
    }
}
