package org.rws.mastermind.engine;

import java.util.List;
import org.rws.mastermind.models.*;
import org.rws.mastermind.interfaces.*;

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


    public GameSession(CodeGenerator codeGenerator, String sessionId, List<Player> players, int numRounds) {
        this.codeGen = codeGenerator;
        this.sessionId = sessionId;
        this.players = players;
        this.numRounds = numRounds;
        
        Code secretCode = codeGen.generateCode();
        gameState = new GameState(secretCode, numRounds);
    }

    public static GameSession create(CodeGenerator codeGen, String sessionId, List<Player> players, int numRounds) {
        if (players == null || players.isEmpty()) {
            System.out.println("GameSession not created.");
            return null;
        }
        return new GameSession(codeGen, sessionId, players, numRounds);
    }

    // Reset the session with a new secret code
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

    // Getters and setters
    public String getSessionId() { return sessionId; }
    public List<Player> getPlayers() { return players; }
    public int getAttemptsLeft() { return gameState.getAttemptsLeft(); }
    public boolean isGameWon() { return gameState.isGameWon(); }
    public boolean isGameOver() { return gameState.isGameOver(); }
    public GameState getGameState() { return gameState; }
    public String getSecretCodeString() { return gameState.getSecretCodeString(); }
}
