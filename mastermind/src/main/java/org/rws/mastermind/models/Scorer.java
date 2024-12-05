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
     * Scores a guess compared to the secret code with more detailed feedback.
     * The result array contains four elements:
     * - result[0]: The number of correct characters in the correct position (black pegs).
     * - result[1]: The number of correct characters in the wrong position (white pegs).
     * - result[2]: The total difference between the number of occurrences of each character in the guess and the code.
     *
     * @param guess The string representing the player's guess.
     * @param code The string representing the secret code.
     * @return An array of four integers representing the detailed score.
     */
    public static int[] detailedScore(String guess, String code) {
        int[] result = new int[3];

        // add logic here
        
        return result;
    }

    public int[] createHash(char[] guess, int varLength) {
        // Initialize an array of size 'vars' with all elements as 0
        int[] hash = new int[varLength];
    
        // Iterate over the guess array
        for (char ch : guess) {
            // Convert the character to an integer (e.g., '0' -> 0, '1' -> 1, etc.)
            int index = ch - '0';

            // Increment the value at the index specified by guess[ch]
            hash[index]++;
        }
    
        return hash; // Return the resulting hash array
    }
    
}