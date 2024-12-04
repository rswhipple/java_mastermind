package org.rws.mastermind;

import org.rws.mastermind.engine.MastermindGameEngine;
import org.rws.mastermind.settings.CLISettingsProvider;
import org.rws.mastermind.input.CLIInputHandler;
import org.rws.mastermind.feedback.DefaultFeedbackGenerator;
import org.rws.mastermind.codegen.DefaultCodeGenerator;

public class Main {
    public static void main(String[] args) {
        CLISettingsProvider settingsProvider = new CLISettingsProvider();
        CLIInputHandler inputHandler = new CLIInputHandler();
        DefaultFeedbackGenerator feedbackGenerator = new DefaultFeedbackGenerator(settingsProvider);
        DefaultCodeGenerator codeGenerator = new DefaultCodeGenerator(settingsProvider);

        MastermindGameEngine game = new MastermindGameEngine(
                settingsProvider, 
                inputHandler,
                feedbackGenerator,
                codeGenerator
            );

        game.createGameSession();
        game.startGameSession();

        // Add shutdown handler here
    }

}