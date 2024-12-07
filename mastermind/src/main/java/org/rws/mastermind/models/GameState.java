package org.rws.mastermind.models;


public class GameState {
    private Code secretCode;
    private int attemptsLeft;
    private boolean isGameWon;
    private final Feedback feedback;
    
    public GameState(Code secretCode, int attemptsLeft) {
        this.secretCode = secretCode;
        this.attemptsLeft = attemptsLeft;
        this.isGameWon = false;
        this.feedback = new Feedback(secretCode);
    }

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
     * Decrements the number of attempts left by one.
     */
    public void decrementAttempts() {
        if (attemptsLeft > 0) {
            attemptsLeft--;
        }
    }

    public void setFeedbackOptions(int option) {
        feedback.setFeedbackOption(option);
    }

    // Getters and setters
    public Code getSecretCode() { return secretCode; }
    public String getSecretCodeString() { return secretCode.toString(); }
    public int getAttemptsLeft() { return attemptsLeft; }
    public boolean isGameWon() { return isGameWon; }
    public void setGameWon(boolean gameWon) { this.isGameWon = gameWon; }
    public boolean isGameOver() { return attemptsLeft <= 0 || isGameWon; }
   
}
