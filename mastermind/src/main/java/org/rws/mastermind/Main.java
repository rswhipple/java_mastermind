package org.rws.mastermind;

import org.rws.mastermind.engine.GameEngine;
import org.rws.mastermind.engine.GameEngineFactory;
import org.rws.mastermind.database.DatabaseSetup;
import org.rws.mastermind.database.MastermindDB;
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
        int gameMode = parseArgs(args);
        if (args.length != 1 || gameMode < 0) {
            displayUsage();
            return;
        }

        // Setup database, connect, and register shutdown task
        DatabaseSetup.setupDatabase(dbFile);
        MastermindDB db = new MastermindDB(dbFile);
        registerShutdownTask(db::closeDB);

        // Setup HTTP handler and register shutdown task
        HttpHandlerImp httpHandler = new HttpHandlerImp();
        registerShutdownTask(httpHandler::cleanup);

        // Setup input handler and register shutdown task
        CLIInputHandler inputHandler = new CLIInputHandler();
        registerShutdownTask(inputHandler::cleanup);

        // Central shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            inputHandler.displayMessage("Central shutdown hook triggered. Shutting down cleanly...");
            inputHandler.setRunning(false);       

            for (Runnable task : shutdownTasks) {
                try {
                    task.run();
                } catch (Exception e) {
                    inputHandler.displayErrMessage("Error during shutdown" + e.getMessage());
                }
            }
            inputHandler.displayMessage("Goodbye!");
        }));

        // Initialize the game engine
        GameEngine game = GameEngineFactory.createEngine(gameMode, db, inputHandler, httpHandler);
        inputHandler.addListener(game);

        if (game.createGameSession()) {
            inputHandler.displayMessage("Game session created successfully.");
        }

    }

    private static int parseArgs(String[] args) {
        return switch (args[0].toLowerCase()) {
            case "cli_simple" -> {
                // Initialize simple CLI mode
                System.out.println("Starting game in Simple CLI mode...");
                yield 1;
            }
            case "cli_robust" -> {
                // Initialize robust CLI mode
                System.out.println("Starting game in Robust CLI mode...");
                yield 2;
            }
            default -> -1;
        };
    }

    private static void displayUsage() {
        String[] usage = {
                "Invalid arguments.Please use one of the following commands to start the game:",
                "",
                "1. java java_mastermind cli_simple(Starts the game with a simple command - line interface)",
                "2. java java_mastermind cli_robust(Starts the game with an advanced command - line interface)",
        };

        for (String line : usage) {
            System.out.println(line);
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