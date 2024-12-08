package org.rws.mastermind;

import org.rws.mastermind.engine.CLIGameEngine;
import org.rws.mastermind.code.RandomCodeGenerator;
import org.rws.mastermind.database.DatabaseSetup;
import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.settings.CLISetter;
import org.rws.mastermind.input.CLIInputHandler;
import org.rws.mastermind.http.HttpHandlerImp;

import java.util.ArrayList;
import java.util.List;

/**
 * The Main class is the entry point for the Mastermind game application.
 * It initializes the necessary components and starts the game session.
 */
public class Main {
    private static final List<Runnable> shutdownTasks = new ArrayList<>();
    private static final String dbFile = "mastermind/src/main/resources/mastermind_db.sqlite3";

    /**
     * The main method is the entry point of the application.
     * It initializes the input handler, settings provider, code generator, and game engine,
     * then creates and starts a new game session.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Setup database, connect, and register shutdown task
        DatabaseSetup.setupDatabase(dbFile);
        MastermindDB db = new MastermindDB(dbFile);
        registerShutdownTask(db::closeDB);

        // Setup input handler and register shutdown task
        CLIInputHandler inputHandler = new CLIInputHandler();
        registerShutdownTask(inputHandler::cleanup);

        // Setup HTTP handler and register shutdown task
        HttpHandlerImp httpHandler = new HttpHandlerImp();
        registerShutdownTask(httpHandler::cleanup);

        CLISetter settingsProvider = new CLISetter(inputHandler);
        RandomCodeGenerator codeGenerator = new RandomCodeGenerator(settingsProvider, httpHandler);

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

        // Initialize the game engine
        CLIGameEngine game = new CLIGameEngine(
                db,
                settingsProvider, 
                inputHandler,
                codeGenerator
            );

        inputHandler.addListener(game);

        if (game.createGameSession()) {
            inputHandler.displayMessage("Game session created successfully.");
        } 
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