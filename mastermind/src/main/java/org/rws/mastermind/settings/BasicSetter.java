package org.rws.mastermind.settings;

import org.rws.mastermind.interfaces.GameSetter;

public class BasicSetter implements GameSetter {
    private int numberOfPlayers;
    private int numberOfRounds;
    private int codeLength;
    private boolean optionsFlag;
    private boolean openHandFlag;

    public BasicSetter() {
        this.numberOfPlayers = 1;
        this.numberOfRounds = 10;
        this.codeLength = 4;
        this.optionsFlag = false;
        this.openHandFlag = false;
    }

    @Override
    public void initOptionsMenu() {
        System.out.println("Options menu not available in basic mode.");
    };
    
    // Getters and setters
    @Override public int getNumberOfPlayers() { return this.numberOfPlayers; }
    @Override public int getNumberOfRounds() { return this.numberOfRounds; }
    @Override public int getCodeLength() { return this.codeLength; }
    @Override public String getCodeCharsString() { return "12345678"; }
    @Override public boolean getOptionsFlag() { return this.optionsFlag; }
    @Override public boolean getOpenHandFlag() { return this.openHandFlag; }

}
