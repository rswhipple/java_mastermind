package org.rws.mastermind.settings;

import org.rws.mastermind.interfaces.GameSettingsProvider;

/**
 * The DefaultSettingsProvider class implements the GameSettingsProvider interface
 * and provides default game settings for the Mastermind game.
 */
public class DefaultSettingsProvider implements GameSettingsProvider {

    /**
     * Gets the number of players in the game.
     *
     * @return The number of players.
     */
    @Override
    public int getNumberOfPlayers() {
        return 2; // Default: 2 players
    }

    /**
     * Gets the number of rounds in the game.
     *
     * @return The number of rounds.
     */
    @Override
    public int getNumberOfRounds() {
        return 10; // Default: 10 rounds
    }

    /**
     * Gets the length of the code to be guessed.
     *
     * @return The length of the code.
     */
    @Override
    public int getCodeLength() {
        return 4; // Default: 4 characters
    }

    /**
     * Gets the options available for the code.
     *
     * @return A string representing the code options.
     */
    @Override
    public String getCodeOptions() {
        return "12345678"; // Default: 12345678
    }
}

