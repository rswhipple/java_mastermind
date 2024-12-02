package java_mastermind.src.main.java.org.rws.mastermind.interfaces;

/**
 * The InputHandler interface provides methods for handling user input and displaying messages.
 * It is intended to be implemented by classes that manage user interactions in the Mastermind game.
 */
public interface InputHandler {

    /**
     * Gets the input from the user.
     *
     * @return A string representing the user's input.
     */
    String getInput();

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    void displayMessage(String message);
}
