package org.rws.mastermind.engine;

import org.rws.mastermind.interfaces.GameSettingsProvider;
import org.rws.mastermind.interfaces.GameEngine;
import org.rws.mastermind.interfaces.InputHandler;
import org.rws.mastermind.interfaces.CodeGenerator;
import org.rws.mastermind.models.GameSession;
import org.rws.mastermind.models.Feedback;
import org.rws.mastermind.models.Code;
import org.rws.mastermind.models.Player;
import org.rws.mastermind.models.Validator;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;


public class MastermindGameEngine implements GameEngine {
    private final InputHandler input;
    private final CodeGenerator codeGen;
    private final GameSettingsProvider settings;

    private GameSession session;
    private Feedback feedback;
    private Code secretCode;
    private Validator validator;
    private boolean gameOver = false;

    public MastermindGameEngine(
            GameSettingsProvider gameSettingsProvider,
            InputHandler inputHandler, 
            CodeGenerator codeGenerator
        ) {
        this.settings = gameSettingsProvider;
        this.input = inputHandler;
        this.codeGen = codeGenerator;
        this.session = null;
        this.feedback = null;
        this.secretCode = null;
        this.validator = null;
    }

    @Override
    public GameSession createGameSession() {
        // Display welcome message and game instructions
        welcomeMessage();

        // If options flag is set, display code options
        if (settings.getOptionsFlag()) {
            input.displayMessage("Would you like to see the options menu? (yes/no)");
            if (input.validateInput() == "yes\n") {
                input.displayMessage("Options Menu:");
                for (String message : settings.getOptionsMenu()) {
                    input.displayMessage(message);
                }
            }
        }

        // Compile Player Names
        List<Player> players = compilePlayersList();

        // Create the session
        String sessionID = UUID.randomUUID().toString();
        session = new GameSession(settings, sessionID, players);

        return session;
    }

    @Override
    public void resetSession() {
        String sessionID = UUID.randomUUID().toString();
        session = new GameSession(settings, sessionID, session.getPlayers());
    }

    @Override
    public void startGameSession() {
        // Generate secretCode and create Validator
        secretCode = codeGen.generateCode();
        feedback = new Feedback(secretCode);

        // Debug mode
        showCode();

        validator = new Validator(settings.getCodeLength(), settings.getCodeCharsString());
    
        // Game loop
        while (!gameOver) {
            if (session.getAttemptsLeft() == 0) {
                input.displayMessage("\nGame over! The code was: " + secretCode.toString());
                gameOver = true;
                break;
            } else {
                input.displayMessage("\nROUND " + (11 - session.getAttemptsLeft()));
            }
            input.displayMessage("Make a guess: ");
            processGuess(getGuess());

            if (session.isGameOver()) {
                gameOver = true;
            }
        }

        // Display outtro message
        for (String message : settings.getOuttro()) {
            input.displayMessage(message);
        }
        if (input.validateInput() == "yes\n") {
            resetSession();
            startGameSession();
        } else {
            input.displayMessage("Goodbye!");
        }

    }

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
            gameOver = true;
            return;
        }

        String result = feedback.generateFeedback(guess);
        input.displayMessage("Feedback: " + result);
    }

    public String getGuess() {
        while (true) {
            try {
                return input.validateInput();
            } catch (Exception e) {
                input.displayMessage("An unexpected error occurred: " + e.getMessage());
                return null;
            }
        }
    }

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

    public List<Player> compilePlayersList() {
        List<Player> players = new ArrayList<>();
        int numPlayers = settings.getNumberOfPlayers();
        for (int i = 0; i < numPlayers; i++) {
            players.add(createPlayer());
        }

        return players;
    }

    public void welcomeMessage() {
        // Display welcome message and game instructions
        for (String message : settings.getIntro()) {
            input.displayMessage(message);
        }
        
        for (String message : settings.getGameInstructions()) {
            input.displayMessage(message);
        }
    }

    public void showCode() {
        input.displayMessage(secretCode.toString());
    }
}
