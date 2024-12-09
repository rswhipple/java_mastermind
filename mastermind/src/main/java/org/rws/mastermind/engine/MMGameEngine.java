package org.rws.mastermind.engine;

import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.input.InputHandler;
import org.rws.mastermind.models.Player;
import org.rws.mastermind.models.Validator;
import org.rws.mastermind.settings.BasicSetter;
import org.rws.mastermind.settings.GameSetter;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The MMGameEngine class implements the GameEngine interface and provides
 * simple methods to manage the game sessions and process guesses
 * for the Mastermind game.
 */
public class MMGameEngine implements GameEngine {
    protected final MastermindDB db;
    protected final InputHandler input;
    protected final HttpHandler http;
    protected GameSetter settings;

    protected GameSession session;
    protected final List<Player> players;
    protected Validator validator;


    /**
     * @param db
     * @param inputHandler
     * @param httpHandler
     */
    public MMGameEngine(
            MastermindDB db,
            InputHandler inputHandler,
            HttpHandler httpHandler
        ) {
        this.db = db;
        this.input = inputHandler;
        this.http = httpHandler;
        this.settings = new BasicSetter();
        this.session = null;
        this.players = new ArrayList<>();
        this.validator = null;

        // Display welcome message and game instructions
        welcomeMessage();
        instructions();
    }

    @Override
    public
    void onMenuKey() {}

    /**
     * Creates a new game session with the given settings and player names.
     *
     * @return True if the GameSession was created successfully.
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
        try {
            // Attempt to create the GameSession
            session = GameSession.create(settings, http, sessionID, players);
            if (session == null) {
                System.err.println("Failed to create GameSession. Returning false.");
                return false;
            }

            // Attempt to initialize the Validator
            validator = new Validator(settings.getCodeLength(), settings.getCodeCharString());
        } catch (Exception e) {
            System.err.println("Error occurred during initialization: " + e.getMessage());
            return false;
        }

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

    /**
     * Ends the current game session.
     */
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
        Player winner = null;
        // Game loop
        while (input.isRunning() && !session.isGameOver()) {
            int round = session.getNumRounds() - session.getAttemptsLeft() + 1;

            input.displayMessage("ROUND " + round + " of " + session.getNumRounds());
            input.displayMessage("Make a guess: ");

            processGuess(input.validateInput());

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
    public void processGuess(String guess) {
        // Check if guess is empty
        if (guess == null || guess.isEmpty()) {
            return;
        }

        if (!validator.isValidGuess(guess)) {
            input.displayMessage("Invalid guess. Please try again.");
            return;
        }

        input.displayMessage(session.processGuess(guess));
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
    protected void compilePlayersList() {
        int numPlayers = settings.getNumberOfPlayers();
        for (int i = 0; i < numPlayers; i++) {
            Player player = createPlayer();
            if (player == null) {
                return;
            }
            players.add(player);
        }
    }
}
