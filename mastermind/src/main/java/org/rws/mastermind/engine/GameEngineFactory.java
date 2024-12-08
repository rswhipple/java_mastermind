package org.rws.mastermind.engine;


import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.input.CLIInputHandler;

/**
 * The {@code GameEngineFactory} class implements the Factory design pattern
 * to create instances of the {@link GameEngine} interface.
 */
public class GameEngineFactory {

    /**
     * Creates a {@link GameEngine} based on the specified game mode.
     *
     * @param gameMode The integer representing the game mode.
     *                 Mode 1 corresponds to {@link MMGameEngine}.
     *                 Any other mode defaults to {@link CLIGameEngine}.
     * @param db       The {@link MastermindDB} instance for database interactions.
     * @param input    The {@link CLIInputHandler} instance for handling player input.
     * @param http     The {@link HttpHandler} instance for HTTP interactions.
     * @return A {@link GameEngine} implementation based on the specified game mode.
     */
    public static GameEngine createEngine(int gameMode, MastermindDB db, CLIInputHandler input, HttpHandler http) {
        if (gameMode == 1) {
            return new MMGameEngine(db, input, http);
        }
        return new CLIGameEngine(db, input, http);
    }
}