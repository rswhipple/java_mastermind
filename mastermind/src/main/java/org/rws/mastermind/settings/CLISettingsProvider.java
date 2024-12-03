package org.rws.mastermind.settings;

import org.rws.mastermind.interfaces.GameSettingsProvider;

import java.util.Scanner;

public class CLISettingsProvider implements GameSettingsProvider {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public int getNumberOfPlayers() {
        System.out.println("Enter number of players: ");
        return scanner.nextInt();
    }

    @Override
    public int getNumberOfRounds() {
        System.out.println("Enter number of rounds: ");
        return scanner.nextInt();
    }

    @Override
    public int getCodeLength() {
        System.out.println("Enter code length: ");
        return scanner.nextInt();
    }

    @Override
    public String getCodeOptions() {
        return "12345678"; // Default: 12345678
    }

}
