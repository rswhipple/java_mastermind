package org.rws.mastermind.models;

/**
 * The Scorer class provides methods to score guesses in the Mastermind game.
 */
public class Scorer {
    private static Code secretCode;
    
    /**
     * Constructs a Scorer object with the specified code length and code.
     *
     * @param code The Code object representing the secret code.
     */
    public Scorer(Code code) {
        Scorer.secretCode = code;
    }

    /**
     * Scores a guess compared to the secret code.
     * The result array contains two elements:
     * - result[0]: The number of correct characters in the correct position (black pegs).
     * - result[1]: The number of correct characters in the wrong position (white pegs).
     *
     * @param guess The string representing the player's guess.
     * @param code The string representing the secret code.
     * @return An array of two integers representing the score.
     */
    public int[] score(String guess) {
        int[] result = new int[2];

        int varLength = secretCode.getNumVars();
        char[] code = secretCode.getCode();
        char[] currentGuess = guess.toCharArray();

        int[] codeHash = createHash(code, varLength);
        int[] guessHash = createHash(currentGuess, varLength);
            
        for (int i = 0; i < varLength; i++) {
            result[1] += Math.min(codeHash[i], guessHash[i]);
        }

        int index = 0;
        for (char ch : code) {
            if (ch == currentGuess[index]) {
                result[0] += 1;            
            }
            index++;
        }

        result[1] -= result[0];

        return result;
    }

    /**
     * Creates a hash array from the given guess.
     * The hash array represents the frequency of each character in the guess.
     *
     * @param guess The character array representing the player's guess.
     * @param varLength The length of the hash array.
     * @return An int array representing the frequency of each character in the guess.
     */
    public int[] createHash(char[] guess, int varLength) {
        // Initialize an array of size 'vars' with all elements as 0
        int[] hash = new int[varLength];
    
        // Iterate over the guess array
        for (char ch : guess) {
            // Convert the character to an integer (e.g., '0' -> 0, '1' -> 1, etc.)
            int index = ch - '0' - 1;

            // Increment the value at the index specified by guess[ch]
            hash[index]++;
        }
    
        return hash; // Return the resulting hash array
    }

    /**
     * Generates a hint based on the player's guess compared to the secret code.
     * The hint shows correct characters in their correct positions and underscores for incorrect characters.
     *
     * @param guess The string representing the player's guess.
     * @return A string representing the hint.
     */
    public String patternHint(String guess) {
        String correctPattern = secretCode.toString();
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < guess.length(); i++) {
            if (i < correctPattern.length() && guess.charAt(i) == correctPattern.charAt(i)) {
                hint.append(guess.charAt(i)); // Correct character
            } else {
                hint.append("_"); // Placeholder for incorrect character
            }
        }
        return "Hint: " + hint.toString();
    }

    public String highLowHint(String guess) {
        // TODO Implement high-low hint
        return "Hint: ";
    }
    
}