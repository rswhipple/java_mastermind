package org.rws.mastermind.models;

import java.util.List;

public class GameSession {
    private String sessionId;
    private List<Player> players;
    private GameState gameState;

    public GameSession(String sessionId, List<Player> players) {
        this.sessionId = sessionId;
        this.players = players;
        this.gameState = new GameState(10); // Default max attempts
    }

    public String getSessionId() {
        return sessionId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }
}
