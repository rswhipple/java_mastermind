package org.rws.mastermind.engine;

/**
 * The GameEngine interface provides methods for managing game sessions and processing guesses
 * in the Mastermind game.
 */
public interface GameEngine {

    
    /**
     * Create a new game session.
     *
     * @return A new GameSession object.
     */
    boolean createGameSession();

    /**
     * Starts the game.
     * This method must be called after the game session has been created.
     */
    void runGame();

    /**
     * Resets the current game session.
     */
    void resetSession();

    /**
     * Ends the current game session.
     */
    void endGameSession();

    /**
     * Processes a guess for a given game session.
     *
     * @param guess The String representing the player's guess.
     */
    int processGuess(String guess);

    /**
     * Executes additional behavior after the game session has ended.
     */
    void additionalBehavior();

    /**
     * Displays the secret code.
     */
    void displayCode();

    /**
     * Displays the welcome message.
     */
    void welcomeMessage();

    /**
     * Displays the goodbye message.
     */
    void goodbyeMessage();

    /**
     * Displays the game instructions.
     */
    void instructions();

    /**
     * Menu key hook handler.
     */
    void onMenuKey();
}
