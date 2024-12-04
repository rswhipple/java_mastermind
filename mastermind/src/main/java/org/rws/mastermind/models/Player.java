package org.rws.mastermind.models;

/**
 * The Player class represents a player in the Mastermind game.
 * It contains the player's name and score.
 */
public class Player {
    private String name;
    private int score;

    /**
     * Constructs a Player object with the specified name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
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
     * Gets the score of the player.
     *
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Increments the score of the player by one.
     */
    public void incrementScore() {
        score++;
    }
}