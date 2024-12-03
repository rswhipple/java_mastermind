package org.rws.mastermind.settings;

import org.rws.mastermind.interfaces.GameSettingsProvider;


public class DefaultSettingsProvider implements GameSettingsProvider {
    @Override
    public int getNumberOfPlayers() {
        return 1; // Default: 1 player
    }

    @Override
    public int getNumberOfRounds() {
        return 10; // Default: 10 rounds
    }

    @Override
    public int getCodeLength() {
        return 4; // Default: Code length of 4
    }

    @Override
    public String getCodeOptions() {
        return "12345678"; // Default: 12345678
    }
}

