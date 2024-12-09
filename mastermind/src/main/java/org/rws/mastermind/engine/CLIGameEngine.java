package org.rws.mastermind.engine;

import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.input.CLIInputHandler;
import org.rws.mastermind.models.Player;
import org.rws.mastermind.models.Validator;
import org.rws.mastermind.settings.CLISetter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The CLIGameEngine class implements the GameEngine interface and provides
 * methods to manage the game sessions and process guesses including a robust
 * menu interface for the Mastermind game.
 */
public class CLIGameEngine implements GameEngine {
    private final MastermindDB db;
    private final CLIInputHandler input;
    private final HttpHandler http;
    private final CLISetter settings;

    private GameSession session;
    private final List<Player> players;
    private Validator validator;

    /**
     *
     * @param db Class handling all queries to the SQLite game database
     * @param inputHandler The command-line interface input/output handler
     * @param httpHandler A class that handles a safe http GET connection
     */
    public CLIGameEngine(
            MastermindDB db,
            CLIInputHandler inputHandler,
            HttpHandler httpHandler
    ) {
        this.db = db;
        this.input = inputHandler;
        this.http = httpHandler;
        this.settings = new CLISetter(input);
        this.session = null;
        this.players = new ArrayList<>();
        this.validator = null;

        // Display welcome message and game instructions
        welcomeMessage();
        instructions();
    }


    @Override
    public void startEngine() {
        // Create a new game session
        mainMenu();
    }

    /**
     * Processes the menu key.
     */
    @Override
    public void onMenuKey() {
        mainMenu();
    }


    @Override
    public boolean createGameSession() {
        // Create a clone of the current settings
        CLISetter currentSettings = new CLISetter(settings);

        // Compile the list of players
        compilePlayersList();

        // Create the session
        String sessionID = UUID.randomUUID().toString();
        try {
            // Attempt to create the GameSession
            session = GameSession.create(currentSettings, http, sessionID, players);
            if (session == null) {
                System.err.println("Failed to create GameSession. Returning false.");
                return false;
            }

            // Attempt to initialize the Validator
            validator = new Validator(currentSettings.getCodeLength(), currentSettings.getCodeCharString());
        } catch (Exception e) {
            System.err.println("Error occurred during initialization: " + e.getMessage());
            return false;
        }

        // Run game loop
        runGame();

        return true;
    }

    /**
     * Starts the game session.
     * This method must be called after the game session has been created.
     */
    @Override
    public void runGame() {
        Player winner = null;
        // Game loop
        while (input.isRunning() && !session.isGameOver()) {
            int round = session.getNumRounds() - session.getAttemptsLeft() + 1;

            input.displayMessage("ROUND " + round + " of " + session.getNumRounds());
            input.displayMessage("Make a guess: ");

            if (processGuess(input.validateInput()) == 1 || !input.isRunning()) {
                return;
            }

            if (session.isGameOver()) {
                if (!session.isGameWon()) {
                    input.displayMessage("\nGame over! The code was: ");
                    displayCode();
                } else {
                    winner = session.getCurrentPlayer();
                    winner.incrementWins();
                    input.displayMessage("Congratulations " + winner.getName() + "!");
                }
                for (Player player : players) {
                    if (!player.equals(winner)) {
                        player.incrementLosses();
                    }
                }
                return;
            }
            session.incrementCurrentPlayer();
        }
    }

    /**
     * Processes a user guess:
     * Validates the guess.
     * Decrements the session round count.
     * Generates and displays the feedback.
     */
    @Override
    public int processGuess(String guess) {
        // Check if guess is empty
        if (guess == null || guess.isEmpty()) {
            input.displayMessage("");
            return 0;
        }

        // Check for menu key
        if (guess.equals("#")) {
            input.displayMessage("");
            input.notifyMenuKeyListeners();
            return 0;
        }

        if (!validator.isValidGuess(guess)) {
            if (!input.isRunning()) {
                return 1;
            }
            input.displayMessage("Invalid guess. Please try again.");
            return 0;
        }

        input.displayMessage(session.processGuess(guess));

        return 0;
    }

    /**
     * Resets the current game session.
     */
    @Override
    public void resetSession() {
        session.resetSession();
    }

    /**
     * Ends the current game session.
     */
    @Override
    public void endGameSession() {
        session.endSession();
    }

    /**
     * Displays the secret code.
     * This method is used for debugging purposes.
     */
    @Override
    public void displayCode() {
        input.displayMessage(session.getSecretCodeString());
    }

    /**
     * Displays the welcome message.
     */
    @Override
    public void welcomeMessage() {
        // Display welcome message
        String[] welcomeMessage = {
                "",
                "Welcome to Mastermind!",
                ""
        };

        for (String message : welcomeMessage) {
            input.displayMessage(message);
        }
    }

    /**
     * Asks whether the player wants to play, initiates the session if yes.
     * If no, exit to main shutdown.
     *
     */
    @Override
    public void goodbyeMessage(){
        // Display outro message
        String message = "Thanks for playing...";
        input.displayMessage(message);
    }

    /**
     * Displays the game instructions.
     */
    @Override
    public void instructions() {
        // Display instructions
        String[] instructions = {
                "The goal of the game is to guess the secret code.",
                "The code consists of a series of 4 numbers.",
                "Each number can be between 1 and 8.",
                "Duplicate numbers may appear.",
                "",
                "You will have a limited number of attempts to guess the code.",
                "After each guess, you will receive feedback on your guess.",
                "A black peg indicates that both the number and position are correct.",
                "A white peg means you have a correct number in the wrong position.",
                "",
                "Press '# + enter' at any point to return to the Main Menu.",
                "",
                "Good luck!",
        };

        for (String message : instructions) {
            input.displayMessage(message);
        }
    }

    /**
     * Creates a new player by prompting the user for their name via the command-line interface.
     *
     * @return A Player object representing the new player.
     */
    private Player createPlayer() {
        input.displayMessage("\nWhat's your name?");
        while (input.isRunning()) {
            try {
                String playerName = input.validateInput();
                if (playerName == null || playerName.isEmpty()) {
                    continue;
                }
                Player player = new Player(playerName, db, input);
                input.displayMessage("Welcome, " + player.getName() + "!");
                return player;
            } catch (Exception e) {
                input.displayMessage("An unexpected error occurred: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * Compiles a list of players by prompting the user for each player's name via the command-line interface.
     */
    public void compilePlayersList() {
        // Get the number of players from the settings
        int numPlayers = settings.getNumberOfPlayers();

        // Create a player for each player
        for (int i = 0; i < numPlayers; i++) {
            Player player = createPlayer();
            if (player == null) {
                return;
            }
            players.add(player);
        }
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
                "6. Exit",
                ""
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
                mainMenu();
                break;
            case "3":
                // Add function to end current game
                input.displayMessage("Starting a new game...");
                if (session != null) { endGameSession(); }
                createGameSession();
                return;
            case "4":
                resetSession();
                return;
            case "5":
                if (session.isGameOver()) {
                    createGameSession();
                }
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
