package org.rws.mastermind.engine;


import org.rws.mastermind.database.MastermindDB;
import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.input.CLIInputHandler;

/**
 * The GameEngineFactory implements the Factory design pattern
 * to create a new GameEngine.
 */
public class GameEngineFactory {
    
    public static GameEngine createEngine(int gameMode, MastermindDB db, CLIInputHandler input, HttpHandler http) {
        if (gameMode == 1) {
            return new MMGameEngine(db, input, http);
        }
        return new CLIGameEngine(db, input, http);
    }
}