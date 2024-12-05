package org.rws.mastermind.models;

import java.util.Arrays;

/**
 * The Code class represents a code in the Mastermind game.
 * It contains the code characters, the length of the code, and the valid characters for the code.
 */
public class Code {
    private final char[] code;
    private final int length;
    private final String validCharacters;
    private final int numVariables;

    /**
     * Constructs a Code object with the specified code and valid characters.
     *
     * @param code The string representing the code.
     * @param validCharacters The string representing the valid characters for the code.
     */
    public Code(String code, String validCharacters) {
        this.code = code.toCharArray();
        this.length = code.length();
        this.validCharacters = validCharacters;
        this.numVariables = validCharacters.length();
    }

    /**
     * Gets the code as a character array.
     *
     * @return A character array representing the code.
     */
    public char[] getCode() {
        return code;
    }

    /**
     * Gets the length of the code.
     *
     * @return The length of the code.
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the valid characters for the code.
     *
     * @return A string representing the valid characters for the code.
     */
    public String getValidCharacters() {
        return validCharacters;
    }

    /**
     * Gets the number of valid characters for the code.
     *
     * @return An integer representing the number of valid characters for the code.
     */
    public int getNumVars() {
        return numVariables;
    }

    /**
     * Checks if the given guess matches the code.
     *
     * @param guess The string representing the player's guess.
     * @return True if the guess matches the code, false otherwise.
     */
    public boolean matches(String guess) {
        return Arrays.equals(code, guess.toCharArray());
    }

    /**
     * Returns a string representation of the code.
     *
     * @return A string representing the code.
     */
    @Override
    public String toString() {
        return new String(code);
    }
}
