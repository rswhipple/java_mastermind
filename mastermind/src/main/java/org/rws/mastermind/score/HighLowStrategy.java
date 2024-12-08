package org.rws.mastermind.score;

public class HighLowStrategy implements ScoreStrategy {

    @Override
    public String score(String guess, String secretCode, int varLength) {
        StringBuilder feedback = new StringBuilder();

        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            char secretChar = secretCode.charAt(i);

            if (guessChar == secretChar) {
                feedback.append("correct ");
            } else if (guessChar < secretChar) {
                feedback.append("higher ");
            } else {
                feedback.append("lower ");
            }

            if (i < guess.length() - 1) {
                feedback.append("| "); // Add a separator for readability
            }
        }

        return feedback.toString().trim();
    }
}