package org.rws.mastermind.engine;

import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.input.CLIInputHandler;
import org.rws.mastermind.settings.CLISetter;
import org.rws.mastermind.settings.GameSetter;

import java.util.List;

/**
 * The CLIGameEngine class implements the GameEngine interface and provides
 * methods to manage the game sessions and process guesses including a robust
 * menu interface for the Mastermind game.
 */
public class CLIGameEngine extends MMGameEngine {
    protected final GameSetter settings;
    protected final CLIInputHandler input;

    /**
     *
     * @param db Class handling all queries to the SQLite game database
     * @param inputHandler The command-line interface input/output handler
     * @param http A class that handles a safe http GET connection
     */
    public CLIGameEngine(MastermindDB db, CLIInputHandler inputHandler, HttpHandler http) {
        super(db, inputHandler, http);

        this.input = inputHandler;
        this.settings = new CLISetter(inputHandler);

        instructions();
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
        String[] menuMessages = {
                "",
                "==========================",
                "*****||||  Menu  ||||*****",
                "==========================",
                "",
                "1. Game Settings",
                "2. Leaderboard",
                "3. Start New Game",
                "4. Reset Game",
                "5. Return to Game",
                "6. Exit"
        };
        input.displayMultiMessage(menuMessages);

        input.displayMessage("Choose an option: ");
        String choice = input.validateInput();

        switch (choice) {
            case "1":
                displaySettingsMenu();
                break;
            case "2":
                displayLeaderboard();
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

        input.displayMultiMessage(warning);
    }

    public void displayLeaderboard() {
        List<String> leaders = db.getLeaderboard(5);
        String[] leaderIntro = {
                "",
                "LEADERBOARD",
                ""
        };

        input.displayMultiMessage(leaderIntro);

        for (String leader : leaders) {
            input.displayMessage(leader);
        }
    }
}
