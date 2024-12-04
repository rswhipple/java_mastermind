package org.rws.mastermind.engine;

import org.rws.mastermind.interfaces.GameSettingsProvider;
import org.rws.mastermind.interfaces.GameEngine;
import org.rws.mastermind.interfaces.InputHandler;
import org.rws.mastermind.interfaces.FeedbackGenerator;
import org.rws.mastermind.interfaces.CodeGenerator;
import org.rws.mastermind.models.Code;
import org.rws.mastermind.models.GameSession;
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

    public void resetSession() {

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

    @Override
    public GameSession startGameSession() {
        // Get Player Names
        List<Player> players = compilePlayersList();

        // Generate secretCode and create Validator
        secretCode = codeGen.generateCode();
        validator = new Validator(settings.getCodeLength(), settings.getCodeOptions());
    
        // Start the game
        String sessionID = UUID.randomUUID().toString();
        session = new GameSession(settings, sessionID, players);
       
        // Game loop
        while (!gameOver) {
            input.displayMessage("Make a guess: ");
            processGuess(input.getInput());

            if (session.isGameOver()) {
                gameOver = true;
            }
        }

        return session;
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
}
