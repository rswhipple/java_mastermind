package org.rws.mastermind.engine;

import org.rws.mastermind.interfaces.GameSettingsProvider;
import org.rws.mastermind.interfaces.GameEngine;
import org.rws.mastermind.interfaces.InputHandler;
import org.rws.mastermind.interfaces.FeedbackGenerator;
import org.rws.mastermind.interfaces.CodeGenerator;
import org.rws.mastermind.models.GameSession;
import org.rws.mastermind.models.Code;
import org.rws.mastermind.models.Player;
import org.rws.mastermind.models.Validator;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;


public class MastermindGameEngine implements GameEngine {
    private final InputHandler input;
    private final FeedbackGenerator feedback;
    private final CodeGenerator codeGen;
    private final GameSettingsProvider settings;

    private GameSession session;
    private Code secretCode;
    private Validator validator;
    private boolean gameOver = false;

    public MastermindGameEngine(
            GameSettingsProvider gameSettingsProvider,
            InputHandler inputHandler, 
            FeedbackGenerator feedbackGenerator, 
            CodeGenerator codeGenerator
        ) {
        this.settings = gameSettingsProvider;
        this.input = inputHandler;
        this.feedback = feedbackGenerator;
        this.codeGen = codeGenerator;
        this.session = null;
        this.secretCode = null;
        this.validator = null;
    }

    @Override
    public GameSession createGameSession() {
        // If options flag is set, display code options
        if (settings.getOptionsFlag()) {
            input.displayMessage("Options Menu:");
            for (String message : settings.getOptionsMenu()) {
                input.displayMessage(message);
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
        validator = new Validator(settings.getCodeLength(), settings.getCodeCharsString());
    
        // Game loop
        while (!gameOver) {
            input.displayMessage("Make a guess: ");
            processGuess(input.getInput());

            if (session.isGameOver()) {
                gameOver = true;
            }
        }

        // Display outtro message
        for (String message : settings.getOuttro()) {
            input.displayMessage(message);
        }
        if (input.getInput() == "yes") {
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
            input.displayMessage("Congratulations! You've cracked the code!");
            session.setGameWon(true);
            gameOver = true;
            return;
        }

        String result = feedback.generateFeedback(secretCode, guess);
        input.displayMessage("Feedback: " + result);
    }

    public Player createPlayer() {
        input.displayMessage("Welcome to Mastermind! What's your name?");
        Player player = new Player(input.getInput());

        return player;
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
}
