package org.rws.mastermind.code;

import org.rws.mastermind.settings.GameSetter;

public class UserCodeGenerator implements CodeGenerator {
    // private int codeLength;
    // private int min;
    // private int max;
    private String validCharacters;

    public UserCodeGenerator(GameSetter settings) {
        // this.codeLength = settings.getCodeLength();
        // this.validCharacters = settings.getCodeCharsString();
        // this.min = 0;
        // this.max = validCharacters.length() - 1;
    }

    /**
     * Generates a code for the Mastermind game using user-generated input.
     *
     * @param codeString The string representing the user's validated code.
     * @return A Code object representing the generated code.
     */
    @Override
    public Code generateCode() {
        // Get user input

        // Validate user input

        // Return user input as Code object

        String codeString = "1111"; // Default code
        return new Code(codeString, validCharacters);
    }
}
