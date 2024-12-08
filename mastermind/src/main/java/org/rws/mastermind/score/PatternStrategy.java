package org.rws.mastermind.score;

public class PatternStrategy implements ScoreStrategy {

    /**
     * Generates a hint based on the player's guess compared to the secret code.
     * The hint shows correct characters in their correct positions and underscores for incorrect characters.
     *
     * @param guess The string representing the player's guess.
     * @return A string representing the hint.
     */
    @Override
    public String score(String guess, String secretCode, int varLength) {
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < guess.length(); i++) {
            if (i < secretCode.length() && guess.charAt(i) == secretCode.charAt(i)) {
                hint.append(guess.charAt(i)); // Correct character
            } else {
                hint.append("_"); // Placeholder for incorrect character
            }
        }
        return "Hint: " + hint;
    }
}