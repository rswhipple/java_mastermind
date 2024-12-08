package org.rws.mastermind.score;

public class BasicStrategy implements ScoreStrategy {

    /**
     * Scores a guess compared to the secret code.
     * The result array contains two elements:
     * - result[0]: The number of correct characters in the correct position (black pegs).
     * - result[1]: The number of correct characters in the wrong position (white pegs).
     *
     * @param guess The string representing the player's guess.
     *
     * @return An array of two integers representing the score.
     */
    @Override
    public String score(String guess, String secretCode, int varLength) {
        int[] result = new int[2];

        char[] code = secretCode.toCharArray();
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

        return String.format(
                "%d black peg(s), %d white peg(s)",
                result[0],
                result[1]
        );
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
}