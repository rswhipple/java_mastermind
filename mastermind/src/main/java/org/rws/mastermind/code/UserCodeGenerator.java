package org.rws.mastermind.code;

import org.rws.mastermind.settings.GameSetter;

import java.util.Scanner;

/**
 * The {@code UserCodeGenerator} class implements the {@link CodeGenerator} interface
 * and generates a code for the Mastermind game based on user-provided input.
 * <p>
 * This generator prompts the user to enter a code and validates it against
 * the valid characters specified in the game settings.
 * </p>
 */
public class UserCodeGenerator implements CodeGenerator {
    private final int codeLength;
    private final String validCharacters;

    /**
     * Constructs a {@code UserCodeGenerator} with the specified game settings.
     *
     * @param settings The {@link GameSetter} object containing the game configuration,
     *                 including the code length and valid characters.
     */
    public UserCodeGenerator(GameSetter settings) {
        this.codeLength = settings.getCodeLength();
        this.validCharacters = settings.getCodeCharString();
    }

    /**
     * Generates a code for the Mastermind game using user-generated input.
     * Prompts the user for input, validates the input, and returns it as a {@link Code} object.
     *
     * @return A {@link Code} object representing the user-generated code.
     * @throws IllegalArgumentException if the user input is invalid (e.g., wrong length or invalid characters).
     */
    @Override
    public Code generateCode() {
        Scanner scanner = new Scanner(System.in);
        String code;

        // Get user input
        while (true) {
            System.out.println("Enter a code of length " + codeLength + ".");
            System.out.println("Use only the following characters: " + validCharacters);
            code = scanner.nextLine();

            if (isValidCode(code)) {
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }

        return new Code(code, validCharacters);
    }

    /**
     * Validates the user-provided code.
     *
     * @param code The string representing the user input.
     * @return {@code true} if the input is valid; {@code false} otherwise.
     */
    private boolean isValidCode(String code) {
        if (code.length() != codeLength) {
            return false;
        }

        for (char c : code.toCharArray()) {
            if (validCharacters.indexOf(c) == -1) {
                return false;
            }
        }

        return true;
    }
}
