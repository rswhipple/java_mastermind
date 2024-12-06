package org.rws.mastermind.interfaces;

/**
 * The GameSettingsProvider interface provides methods to retrieve game settings
 * such as the number of players, number of rounds, code length, and code options.
 */
public interface GameSettingsProvider {

    /**
     * Gets the options flag.
     * The options flag is used to determine whether the user can adjust the game settings.
     *
     * @return The options flag.
     */
    boolean getOptionsFlag();

    /**
     * Gets the introduction messages for the game.
     *
     * @return An array of strings representing the introduction messages.
     */
    String[] getIntro();

    /**
     * Gets the game instructions.
     *
     * @return An array of strings representing the game instructions.
     */
    String[] getGameInstructions();

    /**
     * Initiates the Options Menu integrated process.
     */
    void initOptionsMenu();

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
    String getCodeCharsString();

    /**
     * See if open hand mode is selected.
     *
     * @return A boolean representing the open hand mode flag.
     */
    boolean getOpenHandFlag();
}
