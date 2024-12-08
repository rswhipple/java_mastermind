package org.rws.mastermind.feedback;

import org.rws.mastermind.code.Code;
import org.rws.mastermind.score.HighLowStrategy;
import org.rws.mastermind.score.Scorer;

/**
 * Provides feedback in the form of high/low hints for a guess in the Mastermind game.
 */
public class HighLowFeedback implements Feedback {
    private final Scorer scorer;

    /**
     * Constructs a {@code HighLowFeedback} object with the specified secret code.
     *
     * @param code The {@link Code} object representing the secret code.
     */
    public HighLowFeedback(Code code) {
        this.scorer = new Scorer(new HighLowStrategy(), code);
    }

    /**
     * Generates high/low feedback for a given guess compared to the secret code.
     *
     * @param guess The player's guess.
     * @return A string representing high/low hints for each position in the guess.
     */
    @Override
    public String generateFeedback(String guess) {
        return scorer.score(guess);
    }
}

