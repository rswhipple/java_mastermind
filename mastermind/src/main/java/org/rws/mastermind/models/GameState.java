package org.rws.mastermind.models;

import org.rws.mastermind.code.Code;
import org.rws.mastermind.feedback.Feedback;
import org.rws.mastermind.feedback.FeedbackFactory;


/**
 * The GameState class represents the state of a game in the Mastermind game.
 * It contains the secret code, the number of attempts left, and the game status.
 */
public class GameState {
    public enum GameStateEnum { PLAYING, MENU }

    private final Code secretCode;
    private int attemptsLeft;
    private boolean isGameWon = false;
    private final Feedback feedback;
    private GameStateEnum currentState;
    
    /**
     * The GameState class represents the state of a game in the Mastermind game.
     * It contains the secret code, the number of attempts left, and the game status.
     */
    public GameState(Code secretCode, String fbType, int numRounds) {
        this.secretCode = secretCode;
        this.attemptsLeft = numRounds;
        this.feedback = FeedbackFactory.createFeedback(secretCode, fbType);
        this.currentState = GameStateEnum.PLAYING;
    }

    /**
     * Processes the player's guess and returns feedback.
     *
     * @param guess The string representing the player's guess.
     * @return A string representing the feedback for the guess.
     */
    public String processGuess(String guess) {
        if (currentState == GameStateEnum.MENU) {
            return "#";
        }
        if (currentState == GameStateEnum.PLAYING) {
            if (isGameOver()) {
                return "Game is over.";
            }
            
            decrementAttempts();
    
            if (secretCode.matches(guess)) {
                setGameWon(true);
                return "Congratulations! You've cracked the code!";
            }
    
            return "Feedback: " + feedback.generateFeedback(guess);
        }
        return "Invalid game state.";
    }

    /**
     * Gets the game state.
     */
    public GameStateEnum getGameState() {
        return currentState;
    }
    /**
     * Sets the game state.
     */
    public void setGameState(GameStateEnum state) {
        currentState = state;
    }


    /**
     * Gets the secret code as a string.
     * @return The secret code as a string.
     */
    public String getSecretCodeString() { return secretCode.toString(); }

    /**
     * Gets the number of attempts left.
     * @return The number of attempts left.
     */
    public int getAttemptsLeft() { return attemptsLeft; }

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
    public boolean isGameWon() { return isGameWon; }

    /**
     * Sets the game status to won or not won.
     *
     * @param gameWon True if the game is won, false otherwise.
     */
    public void setGameWon(boolean gameWon) { this.isGameWon = gameWon; }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() { return attemptsLeft <= 0 || isGameWon; }

    /**
     * Ends the game.
     */
    public void endGame() {
        attemptsLeft = 0;
    }
   
}
