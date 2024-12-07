package org.rws.mastermind.models;

import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.interfaces.InputHandler;

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
     * Constructs a Player object with the specified name.
     *
     * @param name The name of the player.
     */
    public Player(String name, MastermindDB db, InputHandler input) {
        this.db = db;

        // Validate player name
        while (true) {  // Check if I can use isRUnning instead of true
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
                    if (playerData.size() > 0) {
                        this.name = playerData.get(1);
                        this.uniqueID = Integer.parseInt(playerData.get(0));
                        break;
                    } 
                } else {
                    input.displayMessage("Choose a different name: ");
                    name = input.validateInput().trim();
                }
            }
        }
    }

    public int addPlayerDB() {
        return db.addPlayer(name);
    }

    public void incrementWins() {
        db.incrementWins(uniqueID);
    }

    public void incrementLosses() {
        db.incrementLosses(uniqueID);
    }

    // Getters 
    public String getName() { return name;}
    public int getUniqueID() { return uniqueID; }
    public int getWins() { return db.getWinCount(uniqueID); }
    public int getLosses() { return db.getLossCount(uniqueID); }

}