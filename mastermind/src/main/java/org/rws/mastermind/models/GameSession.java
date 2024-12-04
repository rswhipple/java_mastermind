package org.rws.mastermind.models;

import java.util.List;

import org.rws.mastermind.interfaces.GameSettingsProvider;

public class GameSession {
    private String sessionId;
    private List<Player> players;
    private int attemptsLeft;
    private boolean isGameWon;

    public GameSession(GameSettingsProvider settings, String sessionId, List<Player> players) {
        this.sessionId = sessionId;
        this.players = players;
        this.attemptsLeft = settings.getNumberOfRounds();
        this.isGameWon = false;
    }

    public String getSessionId() {
        return sessionId;
    }

    public List<Player> getPlayers() {
        return players;
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
