Project: Customer Service Dashboard (Java + PostgreSQL)

This is a 2-layer system:
- DBC.java handles the database connection.
- while other classes manage logic and user input separately.

This project is a simple backend system for tracking customer service activity. It allows users to:

- Add and update service notes
- Filter notes by keyword and export to a text file
- Count word frequency in notes (ngram)
- Group notes by employee to track performance

Files:
- DBC.java → handles database connection
- Notes.java → adds new records
- UpdateNotes.java → updates existing notes
- FilteredNotes.java → filters notes and writes results to a text file (visible in File Explorer)
- GroupByNotes.java → shows note count per employee
- Ngram.java → counts word usage in notes
- Main.java → displays all notes

All input is done through the console using `Scanner`.
