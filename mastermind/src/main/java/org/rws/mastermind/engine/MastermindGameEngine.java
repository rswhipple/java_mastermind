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
    public boolean createGameSession() {
        // Compile Player Names
        List<Player> players = null;
        while (input.isRunning()) {
            players = compilePlayersList();
            if (players != null) {
                break;
            }
        }

        // Create the session
        String sessionID = UUID.randomUUID().toString();
        session = new GameSession(settings, sessionID, players);

        if (session.equals(null)) { return false; } // Error creating session, or early exit

        return true;
    }

    /**
     * Resets the current game session.
     */
    @Override
    public void resetSession() {
        gameOver = false;
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
        while (input.isRunning() && !gameOver) {
            if (session.isGameOver()) {
                if (!session.isGameWon()) {
                    input.displayMessage("\nGame over! The code was: " + secretCode.toString());
                }

                gameOver = true;

                showMenu(); // Display global menu
                return;
            } else {
                input.displayMessage("\nROUND " + (settings.getNumberOfRounds() - session.getAttemptsLeft() + 1));
            }
            input.displayMessage("Make a guess: ");
            if (processGuess(input.validateInput()) == 1) {
                input.displayMessage("Do you want to exit the entire program? (y/n)");
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
        input.displayMessage("\n==========================");
        input.displayMessage("*****||||  Menu  ||||*****");
        input.displayMessage("==========================\n");
        input.displayMessage("1. Game Settings Menu");
        input.displayMessage("2. Reset Game");
        input.displayMessage("3. Exit");
        input.displayMessage("4. Return to Game\n");

        input.displayMessage("Choose an option: ");
        String choice = input.validateInput();

        switch (choice) {
            case "1":
                displaySettingsMenu();
                break;
            case "2":
                input.displayMessage("New game session initiated...");
                resetSession();
                startGameSession();
                return;
            case "3":
                input.setRunning(false);
                goodbyeMessage();
                return;
            case "4":
                input.displayMessage("Returning to the game...");
                return;
            default:
                input.displayMessage("Invalid option. Returning to the menu...");
                showMenu();
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
            startGameSession();
        }
    }
    

    /**
     * Creates a new player by prompting the user for their name via the command-line interface.
     *
     * @return A Player object representing the new player.
     */
    public Player createPlayer() {
        input.displayMessage("\nWhat's your name?");
        while (input.isRunning()) {
            try {
                String playerName = input.validateInput();
                if (playerName == null || playerName.isEmpty()) {
                    return null;
                }
                Player player = new Player(playerName);
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
    public List<Player> compilePlayersList() {
        List<Player> players = new ArrayList<>();
        int numPlayers = settings.getNumberOfPlayers();
        for (int i = 0; i < numPlayers; i++) {
            Player player = createPlayer();
            if (player == null) {
                return null;
            }
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
