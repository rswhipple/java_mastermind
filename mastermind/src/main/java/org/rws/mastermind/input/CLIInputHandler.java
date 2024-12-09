package org.rws.mastermind.input;

import org.rws.mastermind.engine.GameEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * The CLIInputHandler class implements the InputHandler interface
 * and provides methods to handle user input and display messages
 * via the command-line interface (CLI).
 */
public class CLIInputHandler implements InputHandler {
    private static final Logger logger = LoggerFactory.getLogger(CLIInputHandler.class);

    private Scanner scanner;
    private volatile boolean running = true; // Flag to control the input loop
    private final List<GameEngine> listeners = new ArrayList<>();

    /**
     * Constructs a CLIInputHandler with a new Scanner object for reading input from the standard input stream.
     */
    public CLIInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Adds a GameEngine object to the list of listeners.
     *
     * @param listener The GameEngine object to be added to the list of listeners.
     */
    public void addListener(GameEngine listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all listeners that the menu key has been pressed.
     */
    public void notifyMenuKeyListeners() {
        for (GameEngine listener : listeners) {
            listener.onMenuKey();
        }
    }

    /**
     * Sets the running flag to control the input loop.
     */
    @Override
    public boolean isRunning() {
        return running;
    }

    /**
     * Sets the running flag to control the input loop.
     */
    @Override
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
            if (Thread.currentThread().isInterrupted()) {
                // Handle Ctrl+C scenario (interruption)
                throw new IOException("Input interrupted (Ctrl+C detected)", e);
            }
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

                // Return trimmed, non-blank input
                if (!userInput.isBlank()) {
                    return userInput.trim();
                }
            } catch (IOException e) {
                if (e.getMessage().contains("Ctrl+C")) {
                    logInfo("Ctrl+C detected.");
                    displayMessage("Shutting down...");
                    running = false; // Set running to false to stop the loop
                    break;
                }
                if (!running) {
                    // If running is false, exit the loop gracefully
                    displayMessage("Shutting down...");
                    break;
                }
                // Handle interrupted input or other IO issues
                logError("An error occurred while validating input", e);
                displayMessage("Error reading input. Please try again.");
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
     * Logs an error message to file.
     *
     * @param message The message to be displayed.
     * @param e The exception that caused the warning.
     */
    @Override
    public void logError(String message, Exception e) {
        logger.error(message, e);
    }

    /**
     * Logs a warning message to file.
     *
     * @param message The error message to be displayed.
     */
    @Override
    public void logWarning(String message) {
        logger.warn(message);
    }

    /**
     * Logs an informational message to file.
     *
     * @param message The error message to be displayed.
     */
    @Override
    public void logInfo(String message) {
        logger.info(message);
    }

    /**
     * Displays a multi-line message to the user via the command-line interface.
     *
     * @param message The message to be displayed.
     */
    public void displayMultiMessage(String[] message) {
        for (String line : message) {
            displayMessage(line);
        }
    }

    /**
     * Closes the scanner.
     */
    public void cleanup() {
        running = false;

        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}