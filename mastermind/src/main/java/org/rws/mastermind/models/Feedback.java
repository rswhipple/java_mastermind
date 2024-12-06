package org.rws.mastermind.models;


/**
 * The DefaultFeedbackGenerator class implements the FeedbackGenerator interface
 * and provides a method to generate feedback for a guess in the Mastermind game.
 */
public class Feedback {
    private Scorer scorer;
    private boolean hintFlag;

    /**
     * Constructs a DefaultFeedbackGenerator with the specified GameSettingsProvider.
     *
     * @param code The Code object representing the secret code.
     */
    public Feedback(Code code) {
        this.scorer = new Scorer(code);
    }

    /**
     * Generates feedback for a given guess compared to the secret code.
     *
     * @param guess The player's guess.
     * @return A string representing the feedback for the guess.
     */
    public String generateFeedback(String guess) {
        int[] score = scorer.score(guess);

        return String.format(
                "%d black peg(s), %d white peg(s)",
                score[0],
                score[1]
        );
    }

    public String generateHint(String guess) {
        return scorer.patternHint(guess);
    }
    
    public void setHintFlag(boolean hintFlag) {
        this.hintFlag = hintFlag;
    }

    public boolean getHintFlag() {
        return this.hintFlag;
    }
}
