package org.rws.mastermind.settings;

import org.rws.mastermind.input.CLIInputHandler;

/**
 * The CLISetter class implements the GameSetter interface
 * and provides methods to retrieve game settings via the command-line interface (CLI).
 */
public class CLISetter extends BasicSetter {
    private final CLIInputHandler input;
    private final int numberOfPlayers;
    private int numberOfRounds;
    private int codeLength;
    private final String codeChars;
    private String codeType;
    private String feedbackType;

    /**
     * Constructs a CLISetter with the specified CLIInputHandler.
     *
     * @param input The CLIInputHandler object used to handle user input via the command-line interface.
     */
    public CLISetter(CLIInputHandler input) {
        this.input = input;
        this.numberOfPlayers = 1;
        this.numberOfRounds = 10;
        this.codeLength = 4;
        this.codeChars = "12345678";
        this.codeType = "random";
        this.feedbackType = "standard";
    }

    // Copy Constructor
    public CLISetter(CLISetter original) {
        this.input = original.input;
        this.numberOfPlayers = original.numberOfPlayers;
        this.numberOfRounds = original.numberOfRounds;
        this.codeLength = original.codeLength;
        this.codeChars = original.codeChars;
        this.codeType = original.codeType;
        this.feedbackType = original.feedbackType;
    }

    /**
     * Initiates the 'Settings Menu' integrated process.
     * 
    */
    @Override
    public void initSettingsMenu() {
        String[] menu = {
            "",
            "========================================",
            "*****||||  Game Settings Menu  ||||*****",
            "========================================",
            "",
            "1. Select number of players",
            "2. Select number of rounds",
            "3. Select length of code",
            "4. Select feedback type",
            "5. Set your own code",
            "6. Return to game",
            ""
        };
    
        input.displayMultiMessage(menu);

        String message = "Choose an option: ";
        int min = 1;
        int max = 6;
        int option = getSelection(message, min, max);
        execSelection(option);
    }

    /**
     * Executes the 'Settings Menu' using a switch/case.
     *
     * @param option An integer representing the user's selection.
     */
    public void execSelection(int option) {
        switch(option) {
            case 1:
                setNumberOfPlayers();
                input.displayMessage("Multiplayer is not functional yet. Sorry!");
                return;
            case 2:
                setNumberOfRounds();
                return;
            case 3:
                setCodeLength();
                return;
            case 4:
                setFeedbackType();
                return;
            case 5:
                setCodeType();
                return;
            case 6:
            default:
                input.displayMessage("Returning to the game...");
        }
    }

    // Getters
    @Override public int getNumberOfPlayers() { return this.numberOfPlayers; }
    @Override public int getNumberOfRounds() { return this.numberOfRounds; }
    @Override public int getCodeLength() { return this.codeLength; }
    @Override public String getCodeCharString() { return this.codeChars; }
    @Override public String getCodeType() { return this.codeType; }
    @Override public String getFeedbackType() { return this.feedbackType; }

    /**
     * Sets the number of players in the game from the command-line interface.
     *
     */
    public void setNumberOfPlayers() {
//        String message = "Enter number of players: ";
//        int min = 1;
//        int max = 5;
//        numberOfPlayers = getSelection(message, min, max);
    }

    /**
     * Sets the number of rounds in the game from the command-line interface.
     *
     */
    public void setNumberOfRounds() {
        String message = "Enter number of rounds: ";
        int min = 1;
        int max = 50;
        numberOfRounds = getSelection(message, min, max);

        input.displayMessage("\nThe number of rounds has been set to: " + numberOfRounds);
    }

    /**
     * Sets the length of the code to be guessed from the command-line interface.
     *
     */
    public void setCodeLength() {
        String message = "Enter length of code: ";
        int min = 3;
        int max = 12;

        codeLength = getSelection(message, min, max);
    }

    /**
     * Sets the number of players in the game from the command-line interface.
     *
     */
    public void setCodeType() {
        String[] codeMenu = {
                "",
                "Choose Code Type",
                "",
                "1. Random Generation",
                "2. User Provided",
                ""
        };

        input.displayMultiMessage(codeMenu);

        String message = "Choose an option: ";
        int min = 1;
        int max = 2;
        int option = getSelection(message, min, max);

        if (option == 2) {
            codeType = "user";
        } else {
            codeType = "random";
        }
    }

    public void setFeedbackType() {
        String[] feedbackMenu = {
                "",
                "Choose Feedback Type",
                "",
                "1. Standard Mastermind",
                "2. Pattern Hints (shows when you have a correct number and where)",
                "3. High Low Hint",
                ""
        };

        input.displayMultiMessage(feedbackMenu);

        String message = "Choose an option: ";
        int min = 1;
        int max = 3;
        int option = getSelection(message, min, max);

        switch (option) {
            case 1 -> feedbackType = "standard";
            case 2 -> feedbackType = "pattern";
            case 3 -> feedbackType = "hl";
        }
    }

    /**
     * Gets the user's 'Settings Menu' selection.
     *
     * @return The int representing a valid option menu selection.
     */
    public int getSelection(String message, int min, int max) {
        while (input.isRunning()) {
            input.displayMessage(message);
            int userInput;

            try {
                // Validate input
                userInput = Integer.parseInt(input.validateInput());

                if (userInput >= min && userInput <= max) {
                    return userInput;
                } else {
                    input.displayMessage("Please enter an integer between" + min + "and " + max + ".");
                }
            } catch (NumberFormatException e) {
                input.displayMessage("Invalid input. Please enter a valid integer.");
            }
        }

        return 0;
    }
}
