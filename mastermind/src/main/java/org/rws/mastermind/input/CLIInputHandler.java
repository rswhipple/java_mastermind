package org.rws.mastermind.input;

import org.rws.mastermind.interfaces.InputHandler;

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
     * Gets the input from the user via the command-line interface.
     *
     * @return A string representing the user's input.
     */
    @Override
    public String getInput() {
        return scanner.nextLine();
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