package org.rws.mastermind.models;

/**
 * The Validator class provides methods to validate guesses in the Mastermind game.
 * It checks if the guess has the correct length and contains only valid characters.
 */
public class Validator {
    private static int codeLength;
    private static String validCharacters;

    /**
     * Constructs a Validator object with the specified code length and valid characters.
     *
     * @param codeLength The length of the code.
     * @param validCharacters The string representing the valid characters for the code.
     */
    public Validator(int codeLength, String validCharacters) {
        Validator.codeLength = codeLength;
        Validator.validCharacters = validCharacters;
    }

    /**
     * Checks if the given guess is valid.
     * A valid guess has the correct length and contains only valid characters.
     *
     * @param guess The string representing the player's guess.
     * @return True if the guess is valid, false otherwise.
     */
    public boolean isValidGuess(String guess) {
        if (guess.length() != codeLength) {
            return false;
        }

        for (char c : guess.toCharArray()) {
            if (!validCharacters.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }
}