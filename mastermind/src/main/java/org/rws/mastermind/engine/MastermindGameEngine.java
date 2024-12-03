package org.rws.mastermind.engine;

import org.rws.mastermind.interfaces.GameSettingsProvider;
import org.rws.mastermind.interfaces.GameEngine;
import org.rws.mastermind.interfaces.InputHandler;
import org.rws.mastermind.interfaces.FeedbackGenerator;
import org.rws.mastermind.interfaces.CodeGenerator;
import org.rws.mastermind.models.Code;
import org.rws.mastermind.models.GameSession;
import org.rws.mastermind.models.TurnResult;

import java.util.List;
import java.util.UUID;


public class MastermindGameEngine implements GameEngine {
    private final InputHandler inputHandler;
    private final FeedbackGenerator feedbackGenerator;
    private final CodeGenerator codeGenerator;
    private final GameSettingsProvider gameSettingsProvider;

    private int getNumberOfPlayers;
    private List<String> playerNames;
    private int getNumberOfRounds;
    private int getCodeLength;
    private String sessionID;
    private Code secretCode;
    private boolean gameOver = false;

    public MastermindGameEngine(
            GameSettingsProvider gameSettingsProvider,
            InputHandler inputHandler, 
            FeedbackGenerator feedbackGenerator, 
            CodeGenerator codeGenerator
        ) {
        this.gameSettingsProvider = gameSettingsProvider;
        this.inputHandler = inputHandler;
        this.feedbackGenerator = feedbackGenerator;
        this.codeGenerator = codeGenerator;
        this.sessionID = UUID.randomUUID().toString();
        this.getNumberOfPlayers = gameSettingsProvider.getNumberOfPlayers();
        this.getNumberOfRounds = gameSettingsProvider.getNumberOfRounds();
        this.getCodeLength = gameSettingsProvider.getCodeLength();
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void resetSession() {
        this.sessionID = UUID.randomUUID().toString();

    }

    @Override
    public GameSession createGameSession(GameSettingsProvider gameSettingsProvider) {
        secretCode = codeGenerator.generateCode();
        inputHandler.displayMessage("Welcome to Mastermind! Make your first guess.");

        return new GameSession(this.sessionID, playerNames);
    }


    @Override
    public TurnResult processGuess(String sessionID, String guess) {
        String feedback = feedbackGenerator.generateFeedback(secretCode, guess);
        inputHandler.displayMessage("Feedback: " + feedback);

        if (secretCode.equals(guess)) {
            inputHandler.displayMessage("Congratulations! You've cracked the code!");
            gameOver = true;
        }
    }
}
