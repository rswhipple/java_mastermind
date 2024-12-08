package org.rws.mastermind.feedback;

import org.rws.mastermind.code.Code;
import org.rws.mastermind.models.Scorer;

/**
 * The DefaultFeedbackGenerator class implements the FeedbackGenerator interface
 * and provides a method to generate feedback for a guess in the Mastermind game.
 */
public class PatternFeedback implements Feedback {
    private final Scorer scorer;

    /**
     * Constructs a {@code PatternFeedback} object with the specified secret code.
     *
     * @param code The {@link Code} object representing the secret code.
     */
    public PatternFeedback(Code code) {
        this.scorer = new Scorer(code);
    }

    /**
     * Generates a pattern hint for a given guess compared to the secret code.
     *
     * @param guess The player's guess.
     * @return A string representing the pattern hint for the guess.
     */
    public String generateFeedback(String guess) {
        return scorer.patternHint(guess);
    }
}
