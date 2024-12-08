package org.rws.mastermind.database;

/**
 * The {@code DatabaseSetup} class provides a utility method to set up the database
 * for the Mastermind game. It ensures the required database tables are created
 * if they do not already exist.
 */
public class DatabaseSetup {

    /**
     * Sets up the database by creating the necessary tables if they do not already exist.
     *
     * @param dbFile The path to the database file. This file is used to store the
     *               Mastermind game data, such as player statistics.
     */
    public static void setupDatabase(String dbFile) {
        MastermindDB db = new MastermindDB(dbFile);

        db.createTable("""
                CREATE TABLE IF NOT EXISTS players (
                    id INTEGER PRIMARY KEY,
                    name TEXT UNIQUE,
                    wins INTEGER DEFAULT 0,
                    losses INTEGER DEFAULT 0
                );
        """);

        db.closeDB();
    }
}

