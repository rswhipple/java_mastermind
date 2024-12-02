package org.rws.mastermind.engine;

import org.rws.mastermind.interfaces.GameService;

public class ClassicMastermindEngine implements GameService {
    // private final InputHandler inputHandler;
    // private final FeedbackGenerator feedbackGenerator;
    // private final CodeGenerator codeGenerator;
    private String secretCode;
    private boolean gameOver = false;

    // public ClassicMastermindEngine(InputHandler inputHandler, FeedbackGenerator feedbackGenerator, CodeGenerator codeGenerator) {
    //     this.inputHandler = inputHandler;
    //     this.feedbackGenerator = feedbackGenerator;
    //     this.codeGenerator = codeGenerator;
    // }

    @Override
    public void createGameSession() {
        secretCode = codeGenerator.generateCode();
        inputHandler.displayMessage("Welcome to Mastermind! Make your first guess.");
    }


    @Override
    public void processGuess(String guess) {
        String feedback = feedbackGenerator.generateFeedback(secretCode, guess);
        inputHandler.displayMessage("Feedback: " + feedback);

        if (secretCode.equals(guess)) {
            inputHandler.displayMessage("Congratulations! You've cracked the code!");
            gameOver = true;
        }
    }
}
