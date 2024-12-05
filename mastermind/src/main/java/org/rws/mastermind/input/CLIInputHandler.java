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

    /**
     * Constructs a CLIInputHandler with a new Scanner object for reading input from the standard input stream.
     */
    public CLIInputHandler() {
        this.scanner = new Scanner(System.in);
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
            throw new IOException("Error reading input");
        }
    }

    /**
     * Validates and retries the user input.
     *
     * @return A string representing the user's input.
     */
    @Override
    public String validateInput() {
        while (true) {
            // Get user input
            String userInput;
            try {
                userInput = getInput();
                return userInput;
            } catch (IOException e) {
                displayMessage("Error reading input. Please try again.");
                continue;
            }
        }
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
}