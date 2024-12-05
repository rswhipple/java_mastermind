package org.rws.mastermind;

import org.rws.mastermind.engine.MastermindGameEngine;
import org.rws.mastermind.settings.CLISettingsProvider;
import org.rws.mastermind.input.CLIInputHandler;
import org.rws.mastermind.codegen.DefaultCodeGenerator;

public class Main {
    public static void main(String[] args) {
        CLIInputHandler inputHandler = new CLIInputHandler();
        CLISettingsProvider settingsProvider = new CLISettingsProvider(inputHandler);
        DefaultCodeGenerator codeGenerator = new DefaultCodeGenerator(settingsProvider);

        MastermindGameEngine game = new MastermindGameEngine(
                settingsProvider, 
                inputHandler,
                codeGenerator
            );

        game.createGameSession();
        game.startGameSession();

        // Add shutdown handler here
    }

}