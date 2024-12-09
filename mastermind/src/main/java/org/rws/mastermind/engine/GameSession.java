package org.rws.mastermind.engine;

import java.util.List;

import org.rws.mastermind.code.Code;
import org.rws.mastermind.code.CodeFactory;
import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.models.*;
import org.rws.mastermind.settings.GameSetter;

/**
 * Represents a game session in the Mastermind game, managing session details, game state,
 * players, and interactions with the game logic.
 */
public class GameSession {
    private final GameSetter settings;
    private final HttpHandler http;
    private final String sessionId;
    private final List<Player> players;

    private Player currentPlayer;
    private int currentPlayerIndex;
    private GameState gameState;

    /**
     * Constructs a new {@code GameSession}.
     *
     * @param settings   Game settings, including configuration details.
     * @param http       HTTP handler for external interactions.
     * @param sessionId  Unique session identifier.
     * @param players    List of players participating in the session.
     */
    public GameSession(GameSetter settings, HttpHandler http, String sessionId, List<Player> players) {
        this.settings = settings;
        this.http = http;
        this.sessionId = sessionId;
        this.players = players;
        this.currentPlayerIndex = 0;
        this.currentPlayer = players.get(currentPlayerIndex);

        Code secretCode = CodeFactory.createCode(settings, http);
        gameState = new GameState(secretCode, settings.getFeedbackType(), settings.getNumberOfRounds());
    }

    /**
     * Creates a new {@code GameSession}.
     *
     * @param settings   Game settings, including configuration details.
     * @param http       HTTP handler for external interactions.
     * @param sessionId  Unique session identifier.
     * @param players    List of players participating in the session.
     * @return A new {@code GameSession}, or {@code null} if no players are provided.
     */
    public static GameSession create(GameSetter settings, HttpHandler http, String sessionId, List<Player> players) {
        if (players == null || players.isEmpty()) {
            System.out.println("GameSession not created.");
            return null;
        }
        return new GameSession(settings, http, sessionId, players);
    }

    /**
     * Resets the game session, reinitializing the game state with a new secret code.
     */
    public void resetSession() {
        Code newSecretCode = CodeFactory.createCode(settings, http);
        gameState = new GameState(newSecretCode, settings.getFeedbackType(), settings.getNumberOfRounds());
    }

    /**
     * Processes a player's guess and returns feedback.
     *
     * @param guess The player's guess.
     * @return Feedback on the guess.
     */
    public String processGuess(String guess) {
        return gameState.processGuess(guess);
    }

    /**
     * Ends the game session and finalizes the game state.
     */
    public void endSession() {
        gameState.endGame();
        System.out.println("Current game ended.");
    }

    /**
     * Gets the session ID.
     *
     * @return The session ID as a string.
     */
    public String getSessionId() { return sessionId; }

    /**
     * Gets the current player.
     *
     * @return The Player object representing the current player
     */
    public Player getCurrentPlayer() { return currentPlayer; }

    /**
     * Sets the current player.
     */
    public void incrementCurrentPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }

    /**
     * Gets the number of rounds in the game session.
     *
     * @return The number of rounds.
     */
    public int getNumRounds() { return settings.getNumberOfRounds(); }

    /**
     * Gets the number of attempts left in the game session.
     *
     * @return The number of attempts left.
     */
    public int getAttemptsLeft() { return gameState.getAttemptsLeft(); }

    /**
     * Gets the game state.
     *
     * @return True if the game is won.
     */
    public boolean isGameWon() { return gameState.isGameWon(); }

    /**
     * Gets the game state.
     *
     * @return True if the game is over.
     */
    public boolean isGameOver() { return gameState.isGameOver(); }

    /**
     * Gets the secret code as a string.
     *
     * @return The secret code as a string.
     */
    public String getSecretCodeString() { return gameState.getSecretCodeString(); }
}
