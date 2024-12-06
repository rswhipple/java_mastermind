package org.rws.mastermind.engine;

import org.rws.mastermind.interfaces.GameSettingsProvider;
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
    private final GameSettingsProvider settings;

    private GameSession session;
    private Feedback feedback;
    private Code secretCode;
    private Validator validator;
    private boolean gameOver = false;

    public MastermindGameEngine(
            GameSettingsProvider gameSettingsProvider,
            InputHandler inputHandler, 
            BaseCodeGenerator codeGenerator
        ) {
        this.settings = gameSettingsProvider;
        this.input = inputHandler;
        this.codeGen = codeGenerator;
        this.session = null;
        this.feedback = null;
        this.secretCode = null;
        this.validator = null;

        // Display welcome message and game instructions
        welcomeMessage();

        // If options flag is set, display code options
        if (settings.getOptionsFlag()) { getOptions(); }
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

        // Game loop
        showCode(); // Debug mode
        while (!gameOver) {
            if (session.isGameOver()) {
                if (session.isGameWon()) {
                    gameOver = true;
                } else {
                    input.displayMessage("\nGame over! The code was: " + secretCode.toString());
                    gameOver = true;
                }
                goodbyeMessage();
                return;
            } else {
                input.displayMessage("\nROUND " + (settings.getNumberOfRounds() - session.getAttemptsLeft() + 1));
            }
            input.displayMessage("Make a guess: ");
            processGuess(input.validateInput());

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
        if (!validator.isValidGuess(guess)) {
            input.displayMessage("Invalid guess. Please try again.");
            return;
        }

        session.decrementAttempts();

        if (secretCode.matches(guess)) {
            input.displayMessage("Congratulations! You've cracked the code!\n");
            session.setGameWon(true);
            return;
        }

        String result = feedback.generateFeedback(guess);
        input.displayMessage("Feedback: " + result);
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
        for (String message : settings.getOuttro()) {
            input.displayMessage(message);
        }

        if (input.validateInput().equals("yes")) {
            gameOver = false;

            // If options flag is set, display code options
            if (settings.getOptionsFlag()) { getOptions(); }

            resetSession();
            startGameSession();
        } 
    }

    public void getOptions() {
        input.displayMessage("Would you like to see the options menu? (yes/no)");
        if (input.validateInput().equals("yes")) {
            settings.initOptionsMenu();
        }
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
    public void showCode() {
        input.displayMessage(secretCode.toString());
    }
}
