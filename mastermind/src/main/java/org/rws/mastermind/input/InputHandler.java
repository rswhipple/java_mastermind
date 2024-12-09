package org.rws.mastermind.input;

/**
 * The InputHandler interface provides methods for handling user input and displaying messages.
 * It is intended to be implemented by classes that manage user interactions in the Mastermind game.
 */
public interface InputHandler {

    /**
     * Retrieves the input from the user.
     *
     * @return A string representing the user's input.
     */
    String getInput() throws Exception;

    /**
     * Validates the input.
     *
     * @return A string representing the user's input.
     */
    String validateInput();

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    void displayMessage(String message);

    /**
     * Displays a multi-line message to the user via the command-line interface.
     *
     * @param message The message to be displayed.
     */
    void displayMultiMessage(String[] message);

    /**
     * Logs an error message to file or console.
     *
     * @param message The error message to be displayed.
     */
    void logError(String message, Exception e);

    /**
     * Displays a warning message to file or console.
     *
     * @param message The error message to be displayed.
     */
    void logWarning(String message);

    /**
     * Displays an information message to file or console.
     *
     * @param message The error message to be displayed.
     */
    void logInfo(String message);

    /**
     * Gets the running flag to control the input loop.
     * 
     * @return The running flag.
     */
    boolean isRunning();
    /**
     * Sets the running flag to control the input loop.
     */
    void setRunning(boolean running);
}
