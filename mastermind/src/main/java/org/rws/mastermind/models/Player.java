package org.rws.mastermind.models;

import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.input.InputHandler;

import java.util.List;

/**
 * The Player class represents a player in the Mastermind game.
 * It contains the player's name and score.
 */
public class Player {
    private final MastermindDB db;
    private String name;
    private int uniqueID;

    /**
     * Constructs a Player object with the specified name, database, and input handler.
     * The constructor validates the player's name and adds the player to the database.
     *
     * @param name The name of the player.
     * @param db The MastermindDB object for database operations.
     * @param input The InputHandler object for handling user input.
     */
    public Player(String name, MastermindDB db, InputHandler input) {
        this.db = db;

        // Construct player object without database
        if (db == null) {
            simplePlayer(name);
            return;
        }

        // Validate player name
        while (true) {  // Check if I can use isRunning instead of true
            int id = db.addPlayer(name);

            if (id > 0) {
                this.name = name;
                this.uniqueID = id;
                break;
            } else {
                input.displayMessage(name + " already exists. Continue as " + name + "? (y/n)");
                String choice = input.validateInput().trim().toLowerCase();
                if (choice.equals("y") || choice.equals("yes")) {
                    List<String> playerData = db.findPlayer(name);
                    this.uniqueID = Integer.parseInt(playerData.get(0));
                    this.name = name;
                    break;
                } else {
                    input.displayMessage("Choose a different name: ");
                    name = input.validateInput().trim();
                }
            }
        }
    }

    /**
     * Constructs a simple Player object without database
     * @param name
     */
    public void simplePlayer(String name) {
        this.name = name;
        this.uniqueID = -1;
    }

    /**
     * Increments the win count for the player.
     */
    public void incrementWins() {
        db.incrementWins(uniqueID);
    }

    /**
     * Increments the loss count for the player.
     */
    public void incrementLosses() {
        db.incrementLosses(uniqueID);
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
     * Gets the unique ID of the player.
     *
     * @return The unique ID of the player.
     */
    public int getUniqueID() {
        return uniqueID;
    }

    /**
     * Gets the win count for the player.
     *
     * @return The win count for the player.
     */
    public int getWins() {
        return db.getWinCount(uniqueID);
    }

    /**
     * Gets the loss count for the player.
     *
     * @return The loss count for the player.
     */
    public int getLosses() {
        return db.getLossCount(uniqueID);
    }

}