package org.rws.mastermind.feedback;

import org.rws.mastermind.interfaces.FeedbackGenerator;
import org.rws.mastermind.interfaces.GameSettingsProvider;
import org.rws.mastermind.utils.Validator;
import org.rws.mastermind.utils.Scorer;

public class DefaultFeedbackGenerator implements FeedbackGenerator {
    private final Validator validator;
    private final Scorer scorer;

    public DefaultFeedbackGenerator(GameSettingsProvider settingsProvider) {
        int codeLength = settingsProvider.getCodeLength();
        String validCharacters = settingsProvider.getCodeOptions();
        this.validator = new Validator(codeLength, validCharacters);
        this.scorer = new Scorer();
    }

    @Override
    public String generateFeedback(String secretCode, String guess) {
        if (!Validator.isValidGuess(guess)) {
            return "Invalid guess. Please try again.";
        }

        return String.format(
                "%d black peg(s), %d white peg(s)",
                Scorer.score(guess, secretCode)[0],
                Scorer.score(guess, secretCode)[1]
        );
    }

    public Validator getValidator() {
        return validator;
    }

    public Scorer getScorer() {
        return scorer;
    }
}
