package org.rws.mastermind.models;

/**
 * The Scorer class provides methods to score guesses in the Mastermind game.
 */
public class Scorer {

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
    public static int[] score(String guess, String code) {
        int[] result = new int[2];
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == code.charAt(i)) {
                result[0]++;
            } else if (code.contains(String.valueOf(guess.charAt(i)))) {
                result[1]++;
            }
        }
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
}