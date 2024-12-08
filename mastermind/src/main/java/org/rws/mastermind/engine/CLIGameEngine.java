package org.rws.mastermind.engine;

import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.input.CLIInputHandler;
import org.rws.mastermind.settings.CLISetter;
import org.rws.mastermind.settings.GameSetter;

public class CLIGameEngine extends MMGameEngine {
    protected final GameSetter settings;
    protected final CLIInputHandler input;


    public CLIGameEngine(MastermindDB db, CLIInputHandler inputHandler, HttpHandler http) {
        super(db, inputHandler, http);

        this.input = inputHandler;
        this.settings = new CLISetter(inputHandler);
    }

    /**
     * Creates a new game session.
     */
    @Override
    public void additionalBehavior() {
        mainMenu();
    }

    /**
     * Processes the menu key.
     */
    @Override
    public void onMenuKey() {
        mainMenu();
    }

    /**
     * Displays and implements the main menu.
     */
    private void mainMenu() {
        input.displayMessage("\n==========================");
        input.displayMessage("*****||||  Menu  ||||*****");
        input.displayMessage("==========================\n");
        input.displayMessage("1. Game Settings");
        input.displayMessage("2. Leaderboard");
        input.displayMessage("3. Start New Game");
        input.displayMessage("4. Reset Game");
        input.displayMessage("5. Return to Game");
        input.displayMessage("6. Exit");

        input.displayMessage("Choose an option: ");
        String choice = input.validateInput();

        switch (choice) {
            case "1":
                displaySettingsMenu();
                break;
            case "2":
                input.displayMessage("LEADERBOARD");
                break;
            case "3":
                // Add function to end current game
                input.displayMessage("Starting a new game...");
                endGameSession();
                createGameSession();
                return;
            case "4":
                resetSession();
                return;
            case "5":
                return; // This may need some work
            case "6":
                input.setRunning(false);
                goodbyeMessage();
                return;
            default:
                input.displayMessage("Invalid option. Returning to the menu...");
                mainMenu();
        }
    }

    /**
     * Displays the game settings menu.
     */
    public void displaySettingsMenu() {
        settings.initSettingsMenu();

        String[] warning = {
            "",
            "All changes will be implemented in your next game.",
            "To apply changes choose 'Start New Game' from the main menu.",
            ""
        };

        for (String message : warning) {
            input.displayMessage(message);
        }
    }
    
}
