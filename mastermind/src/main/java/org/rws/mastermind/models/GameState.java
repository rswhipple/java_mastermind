package org.rws.mastermind.models;


public class GameState {
    private int attemptsLeft;
    private boolean isGameWon;

    public GameState(int maxAttempts) {
        this.attemptsLeft = maxAttempts;
        this.isGameWon = false;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void decrementAttempts() {
        if (attemptsLeft > 0) {
            attemptsLeft--;
        }
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.isGameWon = gameWon;
    }

    public boolean isGameOver() {
        return attemptsLeft <= 0 || isGameWon;
    }
}
