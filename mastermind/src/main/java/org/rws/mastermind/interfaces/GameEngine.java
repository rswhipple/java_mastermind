package org.rws.mastermind.interfaces;

/**
 * The GameEngine interface provides methods for creating game sessions and processing guesses.
 */
public interface GameEngine {

    /**
     * Processes the menu key.
     */
    void onMenuKey();   // Triggered when '#' is pressed

    /**
     * Choose settings if applicable and create a new game session.
     *
     * @return A new GameSession object.
     */
    boolean createGameSession();

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
    int processGuess(String guess);

    /**
     * Displays the welcome message.
     */
    void welcomeMessage();

    /**
     * Displays the goodbye message.
     */
    void goodbyeMessage();
}
