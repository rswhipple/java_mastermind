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
    protected final MastermindDB db;
    protected final InputHandler input;
    protected final BaseCodeGenerator codeGen;
    protected final GameSetter settings;

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

        // Hook for additional behavior in subclasses
        additionalBehavior();

        return true;
    }

    /**
     * Resets the current game session.
     */
    @Override
    public void resetSession() {
        session.resetSession();
    }

    @Override
    public void endGameSession() {
        session.endSession();
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
                int round = session.getNumRounds() - session.getAttemptsLeft() + 1;
                input.displayMessage("\nROUND " + round + " of " + session.getNumRounds());
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
     * Optional additional behavior to create a hook for subclasses.
     */
    @Override
    public void additionalBehavior() {
        // Placeholder for additional behavior
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
            "Welcome to Mastermind!",
            ""
        };

        for (String message : welcomeMessage) {
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
     * Displays the game instructions.
     */
    @Override
    public void instructions() {
        // Display instructions
        String[] instructions = {
            "The goal of the game is to guess the secret code.",
            "The code consists of a series of 4 numbers.",
            "Each number can be between 1 and 8.",
            "You have a limited number of attempts to guess the code.",
            "After each guess, you will receive feedback on your guess.",
            "A black peg indicates that both the number and position are correct.",
            "A white peg means you have a correct number in the wrong position.",
            "You will have 10 attempts to guess the code.",
            "Good luck!",
            ""
        };

        for (String message : instructions) {
            input.displayMessage(message);
        }
    }

}
