package org.rws.mastermind.feedback;

import org.rws.mastermind.code.Code;
import org.rws.mastermind.models.Scorer;

/**
 * The DefaultFeedbackGenerator class implements the FeedbackGenerator interface
 * and provides a method to generate feedback for a guess in the Mastermind game.
 */
public class StandardFeedback implements Feedback {
    private final Scorer scorer;

    /**
     * Constructs a {@code StandardFeedback} object with the specified secret code.
     *
     * @param code The Code object representing the secret code.
     */
    public StandardFeedback(Code code) {
        this.scorer = new Scorer(code);
    }

    /**
     * Compares the player's guess to the secret code and generates feedback.
     * Feedback is provided in the form of "black pegs" and "white pegs":
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
}
