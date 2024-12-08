package org.rws.mastermind.feedback;

import org.rws.mastermind.code.Code;

public class FeedbackCreatorFactory {
    public static FeedbackCreator createFeedback(Code code, String type) {
        return switch (type) {
            case "pattern" -> new PatternFeedback(code);
            case "hl" -> new HighLowFeedback(code);
            default -> new StandardFeedback(code);
        };
    }
}