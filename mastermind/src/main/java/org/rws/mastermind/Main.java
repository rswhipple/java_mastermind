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
        // PARSE
        int gameMode = parseArgs(args);
        if (args.length != 1 || gameMode < 0) {
            displayUsage();
            return;
        }

        // DATABASE 
        // setup, connection, and shutdown task registration
        MastermindDB db;
        if (gameMode == 2) {
            DatabaseSetup.setupDatabase(dbFile);
            if (!checkFileExists(dbFile)) { 
                DatabaseSetup.setupDatabase(dbFile); 
            }
            db = new MastermindDB(dbFile);
            registerShutdownTask(db::closeDB);
        } else {
            db = null;
        }

        // HTTP HANDLER
        // setup and shutdown task registration
        HttpHandlerImp httpHandler = new HttpHandlerImp();
        registerShutdownTask(httpHandler::cleanup);

        // INPUT HANDLER
        // setup and shutdown task registration
        CLIInputHandler inputHandler = new CLIInputHandler();
        registerShutdownTask(inputHandler::cleanup);


        // SHUTDOWN HOOK
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            inputHandler.displayMessage("Central shutdown hook triggered. Shutting down cleanly...");
            inputHandler.setRunning(false);       

            for (Runnable task : shutdownTasks) {
                try {
                    task.run();
                } catch (Exception e) {
                    inputHandler.logError("Error during shutdown", e);
                }
            }
            inputHandler.displayMessage("Goodbye!");
        }));

        // INIT GAME ENGINE
        // create, add listener and start the game engine
        GameEngine game = GameEngineFactory.createEngine(gameMode, db, inputHandler, httpHandler);
        inputHandler.addListener(game);
        if (game != null) {
            game.startEngine();
        }

    }

    /**
     * Parses the command-line arguments to determine the game mode.
     *
     * @param args Command-line arguments.
     * @return The game mode as an integer:
     *         <ul>
     *             <li>1 for basic CLI mode</li>
     *             <li>2 for dynamic CLI mode</li>
     *             <li>-1 for invalid arguments</li>
     *         </ul>
     */
    private static int parseArgs(String[] args) {
        return switch (args[0].toLowerCase()) {
            case "cli_basic" -> {
                // Initialize basic CLI mode
                System.out.println("Starting game in Basic CLI mode...");
                yield 1;
            }
            case "cli_dynamic" -> {
                // Initialize dynamic CLI mode
                System.out.println("Starting game in Dynamic CLI mode...");
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
                "1. java java_mastermind cli_basic (Starts the game with a basic command - line interface)",
                "2. java java_mastermind cli_dynamic (Starts the game with an advanced command - line interface)",
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