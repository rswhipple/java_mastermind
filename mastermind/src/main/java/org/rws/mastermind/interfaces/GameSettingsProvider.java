package org.rws.mastermind.interfaces;

/**
 * The GameSettingsProvider interface provides methods to retrieve game settings
 * such as the number of players, number of rounds, code length, and code options.
 */
public interface GameSettingsProvider {

    /**
     * Gets the number of players in the game.
     *
     * @return The number of players.
     */
    int getNumberOfPlayers();

    /**
     * Gets the number of rounds in the game.
     *
     * @return The number of rounds.
     */
    int getNumberOfRounds();

    /**
     * Gets the length of the code to be guessed.
     *
     * @return The length of the code.
     */
    int getCodeLength();

    /**
     * Gets the options available for the code.
     *
     * @return A string representing the valid code characters.
     */
    String getCodeOptions();
}
