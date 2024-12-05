package org.rws.mastermind.input;

import org.rws.mastermind.interfaces.InputHandler;

import java.io.IOException;
import java.util.Scanner;

/**
 * The CLIInputHandler class implements the InputHandler interface
 * and provides methods to handle user input and display messages
 * via the command-line interface (CLI).
 */
public class CLIInputHandler implements InputHandler {
    private Scanner scanner;
    private volatile boolean running = true; // Flag to control the input loop


    /**
     * Constructs a CLIInputHandler with a new Scanner object for reading input from the standard input stream.
     */
    public CLIInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Retrieves the input from the user via the command-line interface.
     *
     * @return A string representing the user's input.
     */
    @Override
    public String getInput() throws IOException {
        try {
            return scanner.nextLine();
        } catch (Exception e) {
            throw new IOException("Error reading input", e);
        }
    }

    /**
     * Validates and retries the user input.
     *
     * @return A string representing the user's input.
     */
    @Override
    public String validateInput() {
        while (running) {
            try {
                // Get user input using the getInput method
                String userInput = getInput();
                if (userInput != null && !userInput.isBlank()) {
                    return userInput.trim(); // Return trimmed, non-blank input
                }
            } catch (IOException e) {
                if (!running) {
                    // If running is false, exit the loop gracefully
                    break;
                }
                // Handle interrupted input or other IO issues
                displayMessage("Error reading input. Please try again.");
                continue;
            }
        }
        return null;    // Return null if input is not valid (or loop is stopped with 'ctr + c')
    }         

    /**
     * Displays a message to the user via the command-line interface.
     *
     * @param message The message to be displayed.
     */
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Closes the scanner.
     */
    public void cleanup() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}