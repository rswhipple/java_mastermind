package org.rws.mastermind.settings;

import org.rws.mastermind.interfaces.GameSetter;
import org.rws.mastermind.input.CLIInputHandler;

/**
 * The CLISetter class implements the GameSetter interface
 * and provides methods to retrieve game settings via the command-line interface (CLI).
 */
public class CLISetter implements GameSetter {
    private final CLIInputHandler input;
    private final boolean optionsFlag = true;
    private boolean chooseCodeFlag = false;
    private boolean openHandFlag = false;

    private int numberOfPlayers;
    private int numberOfRounds;
    private int codeLength;

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
            "5. Choose your own code",
            "6. Open-hand mode",
            "7. Return to game",
            ""
        };
    
        for (String message : menu) {
            input.displayMessage(message);
        }
    
        int option = getSelection();
        execSelection(option);
    }
    

    /**
     * Gets the user's 'Settings Menu' seleciton.
     *
     * @return The int representing a valid option menu selection.
     */
    public int getSelection() {
        while (true) {
            input.displayMessage("Choose an option: ");
            int userInput;

            try {
                // Validate input
                userInput = Integer.parseInt(input.validateInput());

                if (userInput > 0 && userInput <= 7) {
                    return userInput;
                } else {
                    input.displayMessage("Please enter an integer between 1 and 7.");
                }
            } catch (NumberFormatException e) {
                input.displayMessage("Invalid input. Please enter a valid integer.");
            }
        }
    }

    /**
     * Executes the 'Settings Menu' using a switch/case.
     *
     * @param option An integer representing the user's selection.
     */
    public void execSelection(int option) {
        switch(option) {
            case 1:
                input.displayMessage("Multiplayer is not functional yet. Sorry!");
                return;
            case 2:
                setNumberOfRounds();
                return;
            case 3:
                setCodeLength();
                return;
            case 4:
                input.displayMessage("Feedback type is not functional yet. Sorry!");
                return;
            case 5:
                setChooseCodeFlag();
                return;
            case 6:
                setOpenHandFlag();
                return;
            default:
                input.displayMessage("Returning to the game...");
                return;
        }
    }

    // Getters
    @Override public int getNumberOfPlayers() { return this.numberOfPlayers; }
    @Override public int getNumberOfRounds() { return this.numberOfRounds; }
    @Override public int getCodeLength() { return this.codeLength; }
    @Override public String getCodeCharsString() { return "12345678"; }
    @Override public boolean getOptionsFlag() { return this.optionsFlag; }
    @Override public boolean getOpenHandFlag() { return this.openHandFlag; }
    public boolean getChooseCodeFlag() { return this.chooseCodeFlag; }

    //Setters
    /**
     * Sets the number of players in the game from the command-line interface.
     *
     */
    public void setNumberOfPlayers() {
        while (input.isRunning()) {
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
     * Sets the number of rounds in the game from the command-line interface.
     *
     */
    public void setNumberOfRounds() {
        while (input.isRunning()) {
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
     * Sets the length of the code to be guessed from the command-line interface.
     *
     */
    public void setCodeLength() {
        while (input.isRunning()) {
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
     * Sets the length of the code to be guessed from the command-line interface.
     *
     */
    public void setOpenHandFlag() {
        while (input.isRunning()) {
            input.displayMessage("Do you want to play with an Open Hand? (y/n): ");
            String userInput = input.validateInput().toLowerCase();
            
            try {
                // Validate input
                if (userInput.equals("y") || userInput.equals("yes") || userInput.equals("ye")) {
                    openHandFlag = true;
                    input.displayMessage("Open hand mode selected.");
                    break;
                } else if (userInput.equals("n") || userInput.equals("no")) {
                    chooseCodeFlag = false;
                    input.displayMessage("Open hand mode DE-selected.");
                    break;
                } else {
                    input.displayMessage("Invalid input. Please enter 'y' or 'n'.");
                }
            } catch (Exception e) {
                input.displayMessage("Invalid input.");
            }
        }
    }

    /**
     * Sets the number of players in the game from the command-line interface.
     *
     */
    public void setChooseCodeFlag() {
        while (input.isRunning()) {
            input.displayMessage("Do you want to choose your own code? (y/n): ");
            String userInput = input.validateInput().toLowerCase();
            input.displayMessage(userInput);
            
            try {
                // Validate input
                if (userInput.equals("y") || userInput.equals("yes") || userInput.equals("ye"))  {
                    chooseCodeFlag = true;
                    input.displayMessage("You can now choose your own code.");
                    break;
                } else if (userInput.equals("n") || userInput.equals("no"))  {
                    chooseCodeFlag = false;
                    input.displayMessage("A random code will be generated.");
                    break;
                } else {
                    input.displayMessage("Invalid input. Please enter 'y' or 'n'.");
                }
            } catch (Exception e) {
                input.displayMessage("Invalid input.");
            }
        }
    }
}
