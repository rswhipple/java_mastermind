package org.rws.mastermind.interfaces;

import org.rws.mastermind.models.GameSession;


/**
 * The GameEngine interface provides methods for creating game sessions and processing guesses.
 */
public interface GameEngine {
    /**
     * Creates a new game session with the given player names.
     * Starts the game.
     *
     * @param playerNames A list of player names.
     * @return A new GameSession object.
     */
    GameSession startGameSession();

    void resetSession();

    /**
     * Processes a guess for a given game session.
     *
     * @param sessionId The ID of the game session.
     * @param guess The String representing the player's guess.
     * @return The TurnResult object representing the result of the guess.
     */
    void processGuess(String guess);

    
}
