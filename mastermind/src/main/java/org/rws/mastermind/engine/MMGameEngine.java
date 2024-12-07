package org.rws.mastermind.engine;

import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.interfaces.GameSetter;
import org.rws.mastermind.interfaces.GameEngine;
import org.rws.mastermind.interfaces.InputHandler;
import org.rws.mastermind.codegen.BaseCodeGenerator;
import org.rws.mastermind.models.Player;
import org.rws.mastermind.models.Validator;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The MastermindGameEngine class implements the GameEngine interface
 * and provides methods to manage the game sessions and process guesses
 * for the Mastermind game.
 */
public class MMGameEngine implements GameEngine {
    private final MastermindDB db;
    private final InputHandler input;
    private final BaseCodeGenerator codeGen;
    private final GameSetter settings;

    private GameSession session;
    private List<Player> players;
    private Validator validator;

    public MMGameEngine(
            MastermindDB db,
            GameSetter gameSetter,
            InputHandler inputHandler, 
            BaseCodeGenerator codeGenerator
        ) {
        this.db = db;
        this.settings = gameSetter;
        this.input = inputHandler;
        this.codeGen = codeGenerator;
        this.session = null;
        this.players = new ArrayList<>();
        this.validator = null;

        // Display welcome message and game instructions
        welcomeMessage();
    }

    /**
     * Creates a new player by prompting the user for their name via the command-line interface.
     *
     * @return A Player object representing the new player.
     */
    @Override
    public Player createPlayer() {
        input.displayMessage("\nWhat's your name?");
        while (input.isRunning()) {
            try {
                String playerName = input.validateInput();
                input.displayMessage("Welcome, " + playerName + "!");
                if (playerName == null || playerName.isEmpty()) {
                    return null;
                }
                Player player = new Player(playerName, db);
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
     *
     * @return A list of Player objects representing the players in the game session.
     */
    @Override
    public void compilePlayersList() {
        int numPlayers = settings.getNumberOfPlayers();
        for (int i = 0; i < numPlayers; i++) {
            Player player = createPlayer();
            if (player == null) {
                return;
            }
            players.add(player);
        }
    }

    /**
     * Creates a new game session with the given settings and player names.
     *
     * @return A new GameSession object.
     */
    @Override
    public boolean createGameSession() {
        // Compile Player Names
        if (players == null || players.isEmpty()) {
            while (input.isRunning()) {
                compilePlayersList();
                if (players != null) {
                    break;
                }
            }
        }

        // Create the session
        String sessionID = UUID.randomUUID().toString();
        session = GameSession.create(codeGen, sessionID, players, settings.getNumberOfRounds());
        if (session.equals(null)) { return false; } // Error creating session, or early exit
        validator = new Validator(settings.getCodeLength(), settings.getCodeCharsString());

        // Run game loop
        runGame();
        mainMenu();

        return true;
    }

    /**
     * Resets the current game session.
     */
    @Override
    public void resetSession() {
        session.resetSession();
    }

    /**
     * Starts the game session.
     * This method must be called after the game session has been created.
     */
    @Override
    public void runGame() {
        // Open hand mode
        if (settings.getOpenHandFlag()) {
            displayCode();
        }

        // Game loop
        while (input.isRunning() && !session.isGameOver()) {
            if (session.isGameOver()) {
                if (!session.isGameWon()) {
                    input.displayMessage("\nGame over! The code was: ");
                    displayCode();
                }
                return;
            } else {
                input.displayMessage("\nROUND " + (settings.getNumberOfRounds() - session.getAttemptsLeft() + 1));
            }
            input.displayMessage("Make a guess: ");
            if (processGuess(input.validateInput()) == 1) {
                input.displayMessage("Did you want to exit the entire program? (y/n)");
                String choice = input.validateInput();
                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                    input.setRunning(false);
                } 
            }
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
            return 1;
        }

        if (!validator.isValidGuess(guess)) {
            input.displayMessage("Invalid guess. Please try again.");
            return 0;
        }

        input.displayMessage(session.processGuess(guess));

        return 0;
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
        // Display welcome message and game instructions
        for (String message : settings.getIntro()) {
            input.displayMessage(message);
        }
        
        for (String message : settings.getGameInstructions()) {
            input.displayMessage(message);
        }
    }

    /**
     * Asks whether the player wants to play, reinitiates the session if yes.
     * If no, exit to main shutdown.
     * 
     */
    @Override
    public void goodbyeMessage(){
        // Display outtro message
        String message = "Thanks for playing...";
        input.displayMessage(message);
    }

    /**
     * Processes the menu key.
     */
    @Override
    public void onMenuKey() {
        mainMenu();
    }

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
        int endCurrentGame = settings.initOptionsMenu();
    
        // Only end the current game if explicitly requested
        if (endCurrentGame == 1) {
            input.displayMessage("Ending the current game...");
            resetSession();
        }
    }
}
