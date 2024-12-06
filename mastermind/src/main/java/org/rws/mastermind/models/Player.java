package org.rws.mastermind.models;

import org.rws.mastermind.database.MastermindDB;

/**
 * The Player class represents a player in the Mastermind game.
 * It contains the player's name and score.
 */
public class Player {
    private final MastermindDB db;
    private String name;
    private int uniqueID;

    /**
     * Constructs a Player object with the specified name.
     *
     * @param name The name of the player.
     */
    public Player(String name, MastermindDB db) {
        this.db = db;
        this.name = name;
        this.uniqueID = db.addPlayer(name); // Add player to database
        // TODO handle reuse of player names
    }

    public int addPlayerDB() {
        return db.addPlayer(name);
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number of wins a player has.
     *
     * @return The integer representing the number of wins.
     */
    public int getWins() {
        return db.getWinCount(uniqueID);
    }

    /**
     * Gets the number of losses a player has.
     *
     * @return The integer representing the number of losses.
     */
    public int getLosses() {
        return db.getLossCount(uniqueID);
    }

}