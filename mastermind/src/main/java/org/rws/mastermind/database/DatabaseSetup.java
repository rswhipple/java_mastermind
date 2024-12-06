package org.rws.mastermind.database;

public class DatabaseSetup {
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

        db.createTable("""
                CREATE TABLE IF NOT EXISTS games (
                    id INTEGER PRIMARY KEY,
                    player_id INTEGER,
                    duration TEXT,
                    FOREIGN KEY (player_id) REFERENCES players (id)
                );
        """);

        db.closeDB();
    }
}

