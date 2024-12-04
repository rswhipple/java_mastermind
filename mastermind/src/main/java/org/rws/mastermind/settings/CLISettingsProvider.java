package org.rws.mastermind.settings;

import org.rws.mastermind.interfaces.GameSettingsProvider;

import java.util.Scanner;

/**
 * The CLISettingsProvider class implements the GameSettingsProvider interface
 * and provides methods to retrieve game settings via the command-line interface (CLI).
 */
public class CLISettingsProvider implements GameSettingsProvider {
    private final Scanner scanner = new Scanner(System.in);

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
    public String getCodeOptions() {
        return "12345678"; // Default: 12345678
    }
}
