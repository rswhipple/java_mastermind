package org.rws.mastermind.feedback;

import org.rws.mastermind.code.Code;

public class FeedbackFactory {
    public static Feedback createFeedback(Code code, String type) {
        return switch (type) {
            case "pattern" -> new PatternFeedback(code);
            case "hl" -> new HighLowFeedback(code);
            default -> new StandardFeedback(code);
        };
    }
}