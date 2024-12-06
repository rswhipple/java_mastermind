package org.rws.mastermind.models;

import java.util.List;
import org.rws.mastermind.interfaces.GameSetter;

/**
 * The GameSession class represents a game session in the Mastermind game.
 * It contains the session ID, the list of players, the number of attempts left, and the game status.
 */
public class GameSession {
    private final String sessionId;
    private final List<Player> players;
    private int attemptsLeft;
    private boolean isGameWon;

    /**
     * Constructs a GameSession object with the specified game settings, session ID, and list of players.
     *
     * @param settings The GameSettingsProvider object containing the game settings.
     * @param sessionId The string representing the session ID.
     * @param players The list of players in the game session.
     */
    public GameSession(GameSetter settings, String sessionId, List<Player> players) {
        this.sessionId = sessionId;
        this.players = players;
        this.attemptsLeft = settings.getNumberOfRounds();
        this.isGameWon = false;
    }

    /**
     * Gets the session ID.
     *
     * @return The string representing the session ID.
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Gets the list of players in the game session.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the number of attempts left in the game session.
     *
     * @return The number of attempts left.
     */
    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    /**
     * Decrements the number of attempts left by one.
     */
    public void decrementAttempts() {
        if (attemptsLeft > 0) {
            attemptsLeft--;
        }
    }

    /**
     * Checks if the game is won.
     *
     * @return True if the game is won, false otherwise.
     */
    public boolean isGameWon() {
        return isGameWon;
    }

    /**
     * Sets the game status to won or not won.
     *
     * @param gameWon True if the game is won, false otherwise.
     */
    public void setGameWon(boolean gameWon) {
        this.isGameWon = gameWon;
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return attemptsLeft <= 0 || isGameWon;
    }
}
