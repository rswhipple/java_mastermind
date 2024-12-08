package org.rws.mastermind.engine;

import java.util.List;

import org.rws.mastermind.code.Code;
import org.rws.mastermind.code.CodeFactory;
import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.models.*;
import org.rws.mastermind.settings.GameSetter;

/**
 * The GameSession class represents a game session in the Mastermind game.
 * It contains the session ID, the list of players, the number of attempts left, and the game status.
 */
public class GameSession {
    private final GameSetter settings;
    private final HttpHandler http;
    private final String sessionId;
    private final List<Player> players;
    private GameState gameState;

    /**
     * Constructs a GameSession object with the specified code generator, session ID, list of players, and number of rounds.
     *
     * @param settings The ...
     * @param http The ...
     * @param sessionId The string representing the session ID.
     * @param players The list of players in the game session.
     */
    public GameSession(GameSetter settings, HttpHandler http, String sessionId, List<Player> players) {
        this.settings = settings;
        this.http = http;
        this.sessionId = sessionId;
        this.players = players;

        Code secretCode = CodeFactory.createCode(settings, http);
        gameState = new GameState(secretCode, settings.getFeedbackType(), settings.getNumberOfRounds());
    }

    /**
     * Factory method to create a GameSession object with the specified code generator, session ID, list of players, and number of rounds.
     *
     * @param settings The ...
     * @param http The ...
     * @param sessionId The string representing the session ID.
     * @param players The list of players in the game session.
     */
    public static GameSession create(GameSetter settings, HttpHandler http, String sessionId, List<Player> players) {
        if (players == null || players.isEmpty()) {
            System.out.println("GameSession not created.");
            return null;
        }
        return new GameSession(settings, http, sessionId, players);
    }

    /**
     * Resets the current game session.
     */
    public void resetSession() {
        Code newSecretCode = CodeFactory.createCode(settings, http);
        gameState = new GameState(newSecretCode, settings.getFeedbackType(), settings.getNumberOfRounds());
    }

    /**
     * Processes the guess made by the player.
     *
     * @param guess The string representing the guess made by the player.
     */
    public String processGuess(String guess) {
        return gameState.processGuess(guess);
    }

    /**
     * End the current game session.
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
     * Gets the list of players in the game session.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() { return players; }

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
     * Gets the GameState object.
     *
     * @return The GameState object.
     */
    public GameState getGameState() { return gameState; }

    /**
     * Gets the secret code as a string.
     *
     * @return The secret code as a string.
     */
    public String getSecretCodeString() { return gameState.getSecretCodeString(); }
}
