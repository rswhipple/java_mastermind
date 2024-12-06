package org.rws.mastermind.settings;

import org.rws.mastermind.interfaces.GameSetter;
import org.rws.mastermind.input.CLIInputHandler;

/**
 * The CLISettingsProvider class implements the GameSettingsProvider interface
 * and provides methods to retrieve game settings via the command-line interface (CLI).
 */
public class CLISetter implements GameSetter {
    private final CLIInputHandler input;
    private final boolean optionsFlag = true;
    private boolean openHandFlag = false;

    private int numberOfPlayers;
    private int numberOfRounds;
    private int codeLength;

    /**
     * Constructs a CLISettingsProvider with the specified CLIInputHandler.
     *
     * @param input The CLIInputHandler object used to handle user input via the command-line interface.
     */
    public CLISetter(CLIInputHandler input) {
        this.input = input;
        this.numberOfPlayers = 1;
        this.numberOfRounds = 10;
        this.codeLength = 4;
    }

    /**
     * Gets the introduction messages for the game.
     *
     * @return An array of strings representing the introduction messages.
     */
    @Override
    public String[] getIntro() {
        return new String[]{
                "Welcome to Mastermind!",
                ""
        };
    }

    /**
     * Gets the game instructions.
     *
     * @return An array of strings representing the game instructions.
     */ 
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
        while (true) {
            input.displayMessage("Enter number of players (max 4): ");
            String userInput = input.validateInput();
            
            try {
                // Validate input
                int userNum = Integer.parseInt(userInput);

                if (userNum > 0 && userNum < 5) {
                    numberOfPlayers = userNum;
                    input.displayMessage("Number of players set to " + numberOfPlayers);
                    break;
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
        while (true) {
            input.displayMessage("Enter number of rounds: ");
            String userInput = input.validateInput();

            try {
                // Validate input
                int userNum = Integer.parseInt(userInput);

                if (userNum > 0 && userNum < 50) {
                    numberOfRounds = userNum;
                    break;
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
        while (true) {
            input.displayMessage("Enter length of code: ");
            String userInput = input.validateInput();

            try {
                int userNum = Integer.parseInt(userInput);

                if (userNum >= 2 && userNum <= 12) {
                    codeLength = userNum;
                    break;
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

    /**
     * Gets the open hand flag.
     *
     * @return The open hand flag.
     */
    public boolean getOpenHandFlag() {
        return this.openHandFlag;
    }

    /**
     * Sets the length of the code to be guessed from the command-line interface.
     *
     */
    public void setOpenHandFlag() {
        input.displayMessage("Open hand mode selected.");
        openHandFlag = true;
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
     * Initiates the Options Menu integrated process.
     * 
    */
    @Override
    public void initOptionsMenu() {
        String[] menu = {
            "",
            "Options Menu:",
            "",
            "1. Select number of players",
            "2. Select number of rounds",
            "3. Select length of code",
            "4. Select feedback type",
            "5. Open-hand mode",
            ""
        };

        for (String message : menu) {
            input.displayMessage(message);
        }

        int option = getUserOptionSelection();
        execOptionsMenu(option);
    }

    /**
     * Gets the user's Options Menu seleciton.
     *
     * @return The int representing a valid option menu selection.
     */
    public int getUserOptionSelection() {
        while (true) {
            input.displayMessage("Enter the menu number (example '1'): ");
            int userInput;

            try {
                // Validate input
                userInput = Integer.parseInt(input.validateInput());

                if (userInput > 0 && userInput < 6) {
                    return userInput;
                } else {
                    input.displayMessage("Please enter an integer between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                input.displayMessage("Invalid input. Please enter a valid integer.");
            }
        }
    }

    /**
     * Executes the options menu using a switch/case.
     *
     * @param option An integer representing the user's selection.
     */
    public void execOptionsMenu(int option) {
        switch(option) {
            case 1:
                input.displayMessage("Multiplayer is not functional yet. Sorry!");
                break;
            case 2:
                setNumberOfRounds();
                break;
            case 3:
                setCodeLength();
                break;
            case 4:
                input.displayMessage("Feedback type is not functional yet. Sorry!");
                break;
            case 5:
                setOpenHandFlag();
                break;
            default:
                break;
        }
    }
}
