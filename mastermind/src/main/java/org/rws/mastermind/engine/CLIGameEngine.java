package org.rws.mastermind.engine;

import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.interfaces.GameSetter;
import org.rws.mastermind.interfaces.InputHandler;
import org.rws.mastermind.codegen.BaseCodeGenerator;

public class CLIGameEngine extends MMGameEngine {
    public CLIGameEngine(MastermindDB db, GameSetter gameSetter, InputHandler inputHandler, BaseCodeGenerator codeGenerator) {
        super(db, gameSetter, inputHandler, codeGenerator);
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
                // Add functin to end current game
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
