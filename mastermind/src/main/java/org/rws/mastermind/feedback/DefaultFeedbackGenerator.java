package org.rws.mastermind.feedback;

import org.rws.mastermind.interfaces.FeedbackGenerator;
import org.rws.mastermind.interfaces.GameSettingsProvider;
import org.rws.mastermind.models.Code;
import org.rws.mastermind.models.Scorer;

/**
 * The DefaultFeedbackGenerator class implements the FeedbackGenerator interface
 * and provides a method to generate feedback for a guess in the Mastermind game.
 */
public class DefaultFeedbackGenerator implements FeedbackGenerator {
    private final Scorer scorer;

    /**
     * Constructs a DefaultFeedbackGenerator with the specified GameSettingsProvider.
     *
     * @param settingsProvider The GameSettingsProvider object containing the game settings.
     */
    public DefaultFeedbackGenerator(GameSettingsProvider settingsProvider) {
        this.scorer = new Scorer();
    }

    /**
     * Generates feedback for a given guess compared to the secret code.
     *
     * @param code The Code object representing the secret code.
     * @param guess The player's guess.
     * @return A string representing the feedback for the guess.
     */
    @Override
    public String generateFeedback(Code code, String guess) {
        String secretCode = code.toString();

        return String.format(
                "%d black peg(s), %d white peg(s)",
                Scorer.score(guess, secretCode)[0],
                Scorer.score(guess, secretCode)[1]
        );
    }

    /**
     * Gets the Scorer object used for scoring guesses.
     *
     * @return The Scorer object.
     */
    public Scorer getScorer() {
        return scorer;
    }
}
