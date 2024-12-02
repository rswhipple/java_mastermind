package org.rws.mastermind.interfaces;

import org.rws.mastermind.models.GameSession;
import org.rws.mastermind.models.Turn;

import java.util.List;

/**
 * The GameService interface provides methods for creating game sessions and processing guesses.
 */
public interface GameService {
    /**
     * Creates a new game session with the given player names.
     *
     * @param playerNames A list of player names.
     * @return A new GameSession object.
     */
    GameSession createGameSession(List<String> playerNames);

    /**
     * Processes a guess for a given game session.
     *
     * @param sessionId The ID of the game session.
     * @param guess The Turn object representing the player's guess.
     * @return A string representing the result of the guess.
     */
    String processGuess(String sessionId, Turn guess);
}
