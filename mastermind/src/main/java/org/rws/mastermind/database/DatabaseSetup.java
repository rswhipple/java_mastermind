package org.rws.mastermind.database;

/**
 * The DatabaseSetup class provides a method to set up the database for the Mastermind game.
 */
public class DatabaseSetup {

    /**
     * Sets up the database by creating the necessary tables if they do not already exist.
     *
     * @param dbFile The path to the database file.
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

        
        // db.createTable("""
        //         CREATE TABLE IF NOT EXISTS games (
        //             id INTEGER PRIMARY KEY,
        //             player_id INTEGER,
        //             duration TEXT,
        //             FOREIGN KEY (player_id) REFERENCES players (id)
        //         );
        // """);

        db.closeDB();
    }
}

