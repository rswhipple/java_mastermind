package org.rws.mastermind.settings;

/**
 * The BasicSetter class implements the GameSettingsProvider interface
 * and provides basic game settings for the Mastermind game.
 */
public class BasicSetter implements GameSetter {
    private final int numberOfPlayers;
    private final int numberOfRounds;
    private final int codeLength;
    private final String codeChars;
    private final String codeType;
    private final String feedbackType;


    /**
     * Constructs a BasicSetter with default game settings.
     */
    public BasicSetter() {
        this.numberOfPlayers = 1;
        this.numberOfRounds = 10;
        this.codeLength = 4;
        this.codeChars = "12345678";
        this.codeType = "random";
        this.feedbackType = "standard";

    }

    /**
     * Initializes the settings menu.
     * This method is not available in basic mode.
     */
    @Override
    public void initSettingsMenu() {
        System.out.println("Settings menu not available in basic mode.");
    }

    /**
     * Gets the number of players in the game.
     *
     * @return The number of players.
     */
    @Override
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    /**
     * Gets the number of rounds in the game.
     *
     * @return The number of rounds.
     */
    @Override
    public int getNumberOfRounds() {
        return this.numberOfRounds;
    }

    /**
     * Gets the length of the code to be guessed.
     *
     * @return The length of the code.
     */
    @Override
    public int getCodeLength() {
        return this.codeLength;
    }

    /**
     * Gets the valid characters for the code.
     *
     * @return A string representing the valid characters for the code.
     */
    @Override
    public String getCodeCharString() {
        return codeChars;
    }

    /**
     * Gets the options flag.
     * The options flag is used to determine whether the user can adjust the game settings.
     *
     * @return The options flag.
     */
    @Override
    public String getCodeType() {
        return this.codeType;
    }

    @Override
    public String getFeedbackType() {
        return this.feedbackType;
    }


}
