package org.rws.mastermind.settings;

import org.rws.mastermind.interfaces.GameSettingsProvider;

/**
 * The DefaultSettingsProvider class implements the GameSettingsProvider interface
 * and provides default game settings for the Mastermind game.
 */
public class DefaultSettingsProvider implements GameSettingsProvider {
    private final boolean optionsFlag = false;

    @Override
    public boolean getOptionsFlag() {
        return this.optionsFlag;
    }

    @Override
    public String[] getIntro() {
        return new String[]{
                "Welcome to Mastermind!",
                ""
        };
    }
    @Override
    public String[] getGameInstructions() {
        return new String[]{
                "The goal of the game is to guess the secret code.",
                "The code consists of a series of numbers.",
                "Each number can be between 1 and 8.",
                "You have a limited number of attempts to guess the code.",
                "After each guess, you will receive feedback on your guess.",
                "A black peg indicates that both the number and position are correct.",
                "A white peg means you have a correct number in the wrong position.",
                "You will have 10 attempts to guess the code.",
                "Good luck!",
                ""
        };
    }

    @Override
    public String[] getOptionsMenu() {
        return null;
    }

    @Override
    public String[] getOuttro() {
        return new String[]{
                "Do you want to play again?",
                "Enter 'yes' to play again or any other key to exit."
        };
    }
    /**
     * Gets the number of players in the game.
     *
     * @return The number of players.
     */
    @Override
    public int getNumberOfPlayers() {
        return 1; // Default: 1 players
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
    public String getCodeCharsString() {
        return "12345678"; // Default: 12345678
    }
}

