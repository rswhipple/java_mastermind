package org.rws.mastermind.interfaces;

import org.rws.mastermind.models.GameSession;


/**
 * The GameEngine interface provides methods for creating game sessions and processing guesses.
 */
public interface GameEngine {

    /**
     * Choose settings if applicable and create a new game session.
     *
     * @return A new GameSession object.
     */
    GameSession createGameSession();

    /**
     * Starts the game.
     * This method must be called after the game session has been created.
     */
    void startGameSession();

    /**
     * Resets the current game session.
     */
    void resetSession();

    /**
     * Processes a guess for a given game session.
     *
     * @param guess The String representing the player's guess.
     */
    void processGuess(String guess);
}
