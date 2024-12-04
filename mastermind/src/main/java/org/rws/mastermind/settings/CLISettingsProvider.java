package org.rws.mastermind.settings;

import java.util.Scanner;

/**
 * The CLISettingsProvider class implements the GameSettingsProvider interface
 * and provides methods to retrieve game settings via the command-line interface (CLI).
 */
public class CLISettingsProvider extends DefaultSettingsProvider {
    private final Scanner scanner = new Scanner(System.in);
    private final boolean optionsFlag = false;

    /**
     * Gets the options flag.
     * The options flag is used to determine whether the user can adjust the game settings.
     *
     * @return The options flag.
     */
    @Override
    public boolean getOptionsFlag() {
        return this.optionsFlag;
    }

    /**
     * Gets the Options Menu for game settings from the command-line interface.
     *
     * @return The number of players.
     */
    @Override
    public String[] getOptionsMenu() {
        return new String[]{
                "1. Select number of players",
                "2. Select number of rounds",
                "3. Select length of code",
                "4. Select feedback type",
        };
    }

    /**
     * Gets the number of players in the game from the command-line interface.
     *
     * @return The number of players.
     */
    @Override
    public int getNumberOfPlayers() {
        System.out.println("Enter number of players: ");
        return scanner.nextInt();
    }

    /**
     * Gets the number of rounds in the game from the command-line interface.
     *
     * @return The number of rounds.
     */
    @Override
    public int getNumberOfRounds() {
        System.out.println("Enter number of rounds: ");
        return scanner.nextInt();
    }

    /**
     * Gets the length of the code to be guessed from the command-line interface.
     *
     * @return The length of the code.
     */
    @Override
    public int getCodeLength() {
        System.out.println("Enter code length: ");
        return scanner.nextInt();
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
