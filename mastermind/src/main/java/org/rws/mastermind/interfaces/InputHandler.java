package org.rws.mastermind.interfaces;

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
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    void displayErrMessage(String message);

    /**
     * Sets the running flag to control the input loop.
     */
    void setRunning(boolean running);
}
