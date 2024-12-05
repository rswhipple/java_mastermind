package org.rws.mastermind.settings;

import org.rws.mastermind.input.CLIInputHandler;

/**
 * The CLISettingsProvider class implements the GameSettingsProvider interface
 * and provides methods to retrieve game settings via the command-line interface (CLI).
 */
public class CLISettingsProvider extends DefaultSettingsProvider {

    private final CLIInputHandler input;
    private final boolean optionsFlag = true;

    private int numberOfPlayers;
    private int numberOfRounds;
    private int codeLength;

    public CLISettingsProvider(CLIInputHandler input) {
        this.input = input;
        this.numberOfPlayers = 1;
        this.numberOfRounds = 10;
        this.codeLength = 4;
    }


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
                "5. Open-hand mode"
        };
    }

    /**
     * Gets the number of players in the game from the command-line interface.
     *
     * @return The number of players.
     */
    @Override
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    /**
     * Sets the number of players in the game from the command-line interface.
     *
     */
    public void setNumberOfPlayers() {
        input.displayMessage("Enter number of players (max 4): ");
        String userInput = input.validateInput();

        while (true) {
            // Validate input
            int numPlayers = Integer.parseInt(userInput);
            try {
                if (numPlayers > 0 && numPlayers < 5) {
                    numberOfPlayers = numPlayers;
                } else {
                    input.displayMessage("Please enter an integer between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                input.displayMessage("Invalid input. Please enter a valid integer.");
            }
        }
    }

    /**
     * Gets the number of rounds in the game from the command-line interface.
     *
     * @return The number of rounds.
     */
    @Override
    public int getNumberOfRounds() {
        return this.numberOfRounds;
    }

    /**
     * Sets the number of rounds in the game from the command-line interface.
     *
     */
    public void setNumberOfRounds() {
        input.displayMessage("Enter number of rounds: ");
        String userInput = input.validateInput();

        while (true) {
            // Validate input
            int numRounds = Integer.parseInt(userInput);
            try {
                if (numRounds > 0 && numRounds < 50) {
                    numberOfPlayers = numRounds;
                } else {
                    input.displayMessage("The maximum number of rounds is 50.");
                }
            } catch (NumberFormatException e) {
                input.displayMessage("Invalid input. Please enter a valid integer.");
            }
        }
    }

    /**
     * Gets the length of the code to be guessed from the command-line interface.
     *
     * @return The length of the code.
     */
    @Override
    public int getCodeLength() {
        return this.codeLength;
    }

    /**
     * Sets the length of the code to be guessed from the command-line interface.
     *
     */
    public void setCodeLength() {
        input.displayMessage("Enter length of code: ");
        String userInput = input.validateInput();

        while (true) {
            try {
                int length = Integer.parseInt(userInput);
                if (length >= 2 && length <= 12) {
                    codeLength = length;
                } else {
                    input.displayMessage("Please enter an integer between 2 and 12.");
                }
            } catch (NumberFormatException e) {
                input.displayMessage("Invalid input. Please enter a valid integer.");
            }
        }
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
