package org.rws.mastermind.engine;

import org.rws.mastermind.interfaces.GameSetter;
import org.rws.mastermind.interfaces.GameEngine;
import org.rws.mastermind.interfaces.InputHandler;
import org.rws.mastermind.codegen.BaseCodeGenerator;
import org.rws.mastermind.models.GameSession;
import org.rws.mastermind.models.Feedback;
import org.rws.mastermind.models.Code;
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
public class MastermindGameEngine implements GameEngine {
    private final InputHandler input;
    private final BaseCodeGenerator codeGen;
    private final GameSetter settings;

    private GameSession session;
    private Feedback feedback;
    private Code secretCode;
    private Validator validator;
    private boolean gameOver = false;

    public MastermindGameEngine(
            GameSetter gameSetter,
            InputHandler inputHandler, 
            BaseCodeGenerator codeGenerator
        ) {
        this.settings = gameSetter;
        this.input = inputHandler;
        this.codeGen = codeGenerator;
        this.session = null;
        this.feedback = null;
        this.secretCode = null;
        this.validator = null;

        // Display welcome message and game instructions
        welcomeMessage();
    }

    /**
     * Processes the menu key.
     */
    @Override
    public void onMenuKey() {
        showMenu();
    }

    /**
     * Creates a new game session with the given settings and player names.
     *
     * @return A new GameSession object.
     */
    @Override
    public GameSession createGameSession() {
        // Compile Player Names
        List<Player> players = compilePlayersList();

        // Create the session
        String sessionID = UUID.randomUUID().toString();
        session = new GameSession(settings, sessionID, players);

        return session;
    }

    /**
     * Resets the current game session.
     */
    @Override
    public void resetSession() {
        String sessionID = UUID.randomUUID().toString();
        session = new GameSession(settings, sessionID, session.getPlayers());
    }

    /**
     * Starts the game session.
     * This method must be called after the game session has been created.
     */
    @Override
    public void startGameSession() {
        // Generate secretCode and create Validator
        codeGen.resetCodeLength(settings.getCodeLength());
        secretCode = codeGen.generateCode();
        feedback = new Feedback(secretCode);
        validator = new Validator(settings.getCodeLength(), settings.getCodeCharsString());

        // Open hand mode
        if (settings.getOpenHandFlag()) {
            displayCode();
        }

        // Game loop
        while (!gameOver) {
            if (session.isGameOver()) {
                if (session.isGameWon()) {
                    gameOver = true;
                } else {
                    input.displayMessage("\nGame over! The code was: " + secretCode.toString());
                    gameOver = true;
                }
                showMenu(); // Display global menu
                return;
            } else {
                input.displayMessage("\nROUND " + (settings.getNumberOfRounds() - session.getAttemptsLeft() + 1));
            }
            input.displayMessage("Make a guess: ");
            if (processGuess(input.validateInput()) == 1) {
                return; // Exit program
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

        session.decrementAttempts();

        if (secretCode.matches(guess)) {
            input.displayMessage("Congratulations! You've cracked the code!\n");
            session.setGameWon(true);
            return 0;
        }

        String result = feedback.generateFeedback(guess);
        input.displayMessage("Feedback: " + result);

        // Test for hint
        String hint = feedback.generateHint(guess);
        input.displayMessage("Hint: " + hint);

        return 0;
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

    private void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. View Game Options Menu");
        System.out.println("2. Reset Game");
        System.out.println("3. Exit");
        System.out.println("4. Continue");

        System.out.print("Choose an option: ");
        String choice = input.validateInput();

        switch (choice) {
            case "1":
                displayOptionsMenu();
                break;
            case "2":
                resetSession();
                startGameSession();
                break;
            case "3":
                input.setRunning(false);
                goodbyeMessage();
                break;
            case "4":
                System.out.println("Returning to the game...");
                break;
            default:
                input.displayMessage("Invalid option. Returning to the menu...");
                showMenu();
        }
    }

    public void displayOptionsMenu() {
        settings.initOptionsMenu();
    }

    /**
     * Creates a new player by prompting the user for their name via the command-line interface.
     *
     * @return A Player object representing the new player.
     */
    public Player createPlayer() {
        input.displayMessage("\nWhat's your name?");
        while (true) {
            try {
                String playerName = input.validateInput();
                Player player = new Player(playerName);
                return player;
            } catch (Exception e) {
                input.displayMessage("An unexpected error occurred: " + e.getMessage());
                return null;
            }
        }
    }

    /**
     * Compiles a list of players by prompting the user for each player's name via the command-line interface.
     *
     * @return A list of Player objects representing the players in the game session.
     */
    public List<Player> compilePlayersList() {
        List<Player> players = new ArrayList<>();
        int numPlayers = settings.getNumberOfPlayers();
        for (int i = 0; i < numPlayers; i++) {
            players.add(createPlayer());
        }

        return players;
    }

    /**
     * Displays the secret code.
     * This method is used for debugging purposes.
     */
    public void displayCode() {
        input.displayMessage(secretCode.toString());
    }
}
