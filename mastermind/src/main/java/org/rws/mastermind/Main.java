package org.rws.mastermind;

import org.rws.mastermind.engine.MastermindGameEngine;
import org.rws.mastermind.settings.DefaultSettingsProvider;
import org.rws.mastermind.input.CLIInputHandler;
import org.rws.mastermind.feedback.DefaultFeedbackGenerator;
import org.rws.mastermind.codegen.DefaultCodeGenerator;

public class Main {
    public static void main(String[] args) {
        DefaultSettingsProvider settingsProvider = new DefaultSettingsProvider();
        CLIInputHandler inputHandler = new CLIInputHandler();
        DefaultFeedbackGenerator feedbackGenerator = new DefaultFeedbackGenerator(settingsProvider);
        DefaultCodeGenerator codeGenerator = new DefaultCodeGenerator(settingsProvider);

        boolean keepPlaying = true;

        MastermindGameEngine game = new MastermindGameEngine(
                settingsProvider, 
                inputHandler,
                feedbackGenerator,
                codeGenerator
            );
        while (keepPlaying) {
            game.startGameSession();
            game.resetSession();
        }

        // Add shutdown handler here
    }

}