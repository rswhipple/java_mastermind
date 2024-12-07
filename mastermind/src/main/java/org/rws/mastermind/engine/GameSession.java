package org.rws.mastermind.engine;

import java.util.List;
import org.rws.mastermind.models.*;
import org.rws.mastermind.code.Code;
import org.rws.mastermind.code.CodeGenerator;

/**
 * The GameSession class represents a game session in the Mastermind game.
 * It contains the session ID, the list of players, the number of attempts left, and the game status.
 */
public class GameSession {
    private final CodeGenerator codeGen;
    private final String sessionId;
    private final List<Player> players;
    private GameState gameState;
    private final int numRounds;

    /**
     * Constructs a GameSession object with the specified code generator, session ID, list of players, and number of rounds.
     *
     * @param codeGenerator The CodeGenerator object used to generate the secret code.
     * @param sessionId The string representing the session ID.
     * @param players The list of players in the game session.
     * @param numRounds The number of rounds in the game session.
     */
    public GameSession(CodeGenerator codeGenerator, String sessionId, List<Player> players, int numRounds) {
        this.codeGen = codeGenerator;
        this.sessionId = sessionId;
        this.players = players;
        this.numRounds = numRounds;
        
        Code secretCode = codeGen.generateCode();
        gameState = new GameState(secretCode, numRounds);
    }

    /**
     * Factory method to create a GameSession object with the specified code generator, session ID, list of players, and number of rounds.
     *
     * @param codeGenerator The CodeGenerator object used to generate the secret code.
     * @param sessionId The string representing the session ID.
     * @param players The list of players in the game session.
     * @param numRounds The number of rounds in the game session.
     */
    public static GameSession create(CodeGenerator codeGen, String sessionId, List<Player> players, int numRounds) {
        if (players == null || players.isEmpty()) {
            System.out.println("GameSession not created.");
            return null;
        }
        return new GameSession(codeGen, sessionId, players, numRounds);
    }

    /**
     * Resets the current game session.
     */
    public void resetSession() {
        Code newSecretCode = codeGen.generateCode();
        gameState = new GameState(newSecretCode, numRounds);
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
    public int getNumRounds() { return numRounds; }

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
