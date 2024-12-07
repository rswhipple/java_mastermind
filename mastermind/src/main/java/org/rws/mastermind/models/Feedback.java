package org.rws.mastermind.models;

import org.rws.mastermind.code.Code;

/**
 * The DefaultFeedbackGenerator class implements the FeedbackGenerator interface
 * and provides a method to generate feedback for a guess in the Mastermind game.
 */
public class Feedback {
    private Scorer scorer;
    private int option;

    /**
     * Constructs a DefaultFeedbackGenerator with the specified GameSettingsProvider.
     *
     * @param code The Code object representing the secret code.
     */
    public Feedback(Code code) {
        this.scorer = new Scorer(code);
        this.option = 0;
    }

    /**
     * Generates feedback for a given guess compared to the secret code.
     *
     * @param guess The player's guess.
     * @return A string representing the feedback for the guess.
     */
    public String[] generateFeedback(String guess) {
        int[] score = scorer.score(guess);

        String basicFB = String.format(
                "%d black peg(s), %d white peg(s)",
                score[0],
                score[1]
        );

        String[] fb = {basicFB, ""};

        if (this.option == 1) {
            fb[1] += generatePatternHint(guess);
        } else if (this.option == 2) {
            fb[1] += generateHighLowHint(guess);
        }

        return fb;
    }

    /**
     * Generates a pattern hint for a given guess compared to the secret code.
     *
     * @param guess The player's guess.
     * @return A string representing the pattern hint for the guess.
     */
    public String generatePatternHint(String guess) {
        return scorer.patternHint(guess);
    }

    /**
     * Generates a high-low hint for a given guess compared to the secret code.
     *
     * @param guess The player's guess.
     * @return A string representing the high-low hint for the guess.
     */
    public String generateHighLowHint(String guess) {
        return scorer.highLowHint(guess);
    }

    /**
     * Gets the feedback option.
     *
     * @return The feedback option.
     */
    public int getFeedbackOption() {
        return this.option;
    }
    
    /**
     * Sets the feedback option.
     *
     * @param option The feedback option.
     */
    public void setFeedbackOption(int option) {
        this.option = option;
    }
}
