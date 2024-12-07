package org.rws.mastermind.models;

import org.rws.mastermind.code.Code;

/**
 * The GameState class represents the state of a game in the Mastermind game.
 * It contains the secret code, the number of attempts left, and the game status.
 */
public class GameState {
    private Code secretCode;
    private int attemptsLeft;
    private boolean isGameWon = false;
    private final Feedback feedback;
    
    /**
     * The GameState class represents the state of a game in the Mastermind game.
     * It contains the secret code, the number of attempts left, and the game status.
     */
    public GameState(Code secretCode, int attemptsLeft) {
        this.secretCode = secretCode;
        this.attemptsLeft = attemptsLeft;
        this.feedback = new Feedback(secretCode);
    }

    /**
     * Processes the player's guess and returns feedback.
     *
     * @param guess The string representing the player's guess.
     * @return A string representing the feedback for the guess.
     */
    public String processGuess(String guess) {
        if (isGameOver()) {
            return "";
        }
        
        decrementAttempts();

        if (secretCode.matches(guess)) {
            setGameWon(true);
            return "Congratulations! You've cracked the code!";
        }

        return "Feedback: " + feedback.generateFeedback(guess);
    }

    /**
     * Sets the feedback options.
     *
     * @param option The int representing the feedback option.
     */
    public void setFeedbackOptions(int option) {
        feedback.setFeedbackOption(option);
    }

    /**
     * Gets the secret code.
     * @return The secret code as a Code object.
     */
    public Code getSecretCode() { return secretCode; }

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
