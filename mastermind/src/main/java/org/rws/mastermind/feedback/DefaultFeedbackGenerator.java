package org.rws.mastermind.feedback;

import org.rws.mastermind.interfaces.FeedbackGenerator;
import org.rws.mastermind.interfaces.GameSettingsProvider;
import org.rws.mastermind.models.Code;
import org.rws.mastermind.utils.Scorer;

public class DefaultFeedbackGenerator implements FeedbackGenerator {
    private final Scorer scorer;

    public DefaultFeedbackGenerator(GameSettingsProvider settingsProvider) {
        this.scorer = new Scorer();
    }

    @Override
    public String generateFeedback(Code code, String guess) {
        String secretCode = code.toString();

        return String.format(
                "%d black peg(s), %d white peg(s)",
                Scorer.score(guess, secretCode)[0],
                Scorer.score(guess, secretCode)[1]
        );
    }

    public Scorer getScorer() {
        return scorer;
    }
}
