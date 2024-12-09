package org.rws.mastermind;

import org.rws.mastermind.engine.GameEngine;
import org.rws.mastermind.engine.GameEngineFactory;
import org.rws.mastermind.database.DatabaseSetup;
import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.input.CLIInputHandler;
import org.rws.mastermind.http.HttpHandlerImp;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

/**
 * The {@code Main} class serves as the entry point for the Mastermind game application.
 * It is responsible for initializing required components, setting up resources, and
 * starting the game session.
 */
public class Main {
    private static final List<Runnable> shutdownTasks = new ArrayList<>();
    private static final String dbFile = "src/main/resources/mastermind_db.sqlite3";

    /**
     * The main method initializes the application and starts the game session.
     *
     * <p>
     * The following tasks are performed:
     * <ul>
     *     <li>Parse command-line arguments to determine the game mode.</li>
     *     <li>Setup the database, input handler, and HTTP handler.</li>
     *     <li>Register shutdown tasks to ensure clean resource management.</li>
     *     <li>Create and initialize the game engine.</li>
     *     <li>Start a new game session.</li>
     * </ul>
     * </p>
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        int gameMode = parseArgs(args);
        if (args.length != 1 || gameMode < 0) {
            displayUsage();
            return;
        }

        // Setup database, connect, and register shutdown task
        if (!checkFileExists(dbFile)) { DatabaseSetup.setupDatabase(dbFile); }
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

    /**
     * Parses the command-line arguments to determine the game mode.
     *
     * @param args Command-line arguments.
     * @return The game mode as an integer:
     *         <ul>
     *             <li>1 for simple CLI mode</li>
     *             <li>2 for robust CLI mode</li>
     *             <li>-1 for invalid arguments</li>
     *         </ul>
     */
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


    /**
     * Checks if a file exists.
     *
     * @param fileName path to file (Mastermind database in this case
     * @return True if it exists, false if no.
     */
    private static boolean checkFileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * Displays usage instructions for starting the application with valid arguments.
     */
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