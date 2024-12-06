package org.rws.mastermind;

import org.rws.mastermind.engine.MastermindGameEngine;
import org.rws.mastermind.settings.CLISettingsProvider;
import org.rws.mastermind.input.CLIInputHandler;
import org.rws.mastermind.codegen.BaseCodeGenerator;
import org.rws.mastermind.http.HttpHandlerImp;

import java.util.ArrayList;
import java.util.List;

/**
 * The Main class is the entry point for the Mastermind game application.
 * It initializes the necessary components and starts the game session.
 */
public class Main {
    private static final List<Runnable> shutdownTasks = new ArrayList<>();

    /**
     * The main method is the entry point of the application.
     * It initializes the input handler, settings provider, code generator, and game engine,
     * then creates and starts a new game session.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        CLIInputHandler inputHandler = new CLIInputHandler();
        registerShutdownTask(inputHandler::cleanup);
        HttpHandlerImp httpHandler = new HttpHandlerImp();
        registerShutdownTask(httpHandler::cleanup);

        CLISettingsProvider settingsProvider = new CLISettingsProvider(inputHandler);
        BaseCodeGenerator codeGenerator = new BaseCodeGenerator(settingsProvider, httpHandler);

        // Central shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Central shutdown hook triggered. Shutting down cleanly...");
            inputHandler.setRunning(false);       

            for (Runnable task : shutdownTasks) {
                try {
                    task.run();
                } catch (Exception e) {
                    System.err.println("Error during shutdown" + e.getMessage());
                }
            }
            System.out.println("Goodbye!");
        }));

        // Initialize the game engine and run game
        MastermindGameEngine game = new MastermindGameEngine(
                settingsProvider, 
                inputHandler,
                codeGenerator
            );

        inputHandler.addListener(game);

        game.createGameSession();
        game.startGameSession();

    }

    /**
     * Registers a shutdown task to be executed when the application is shutting down.
     *
     * @param task The Runnable task to be executed during shutdown.
     */
    private static void registerShutdownTask(Runnable task) {
        shutdownTasks.add(task);
    }
}