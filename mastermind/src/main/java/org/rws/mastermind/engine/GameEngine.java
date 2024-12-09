package org.rws.mastermind.engine;

/**
 * The {@code GameEngine} interface defines the contract for managing and interacting
 * with the game logic of the Mastermind game. It provides methods for managing game sessions,
 * processing player guesses, and displaying information to the player.
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
