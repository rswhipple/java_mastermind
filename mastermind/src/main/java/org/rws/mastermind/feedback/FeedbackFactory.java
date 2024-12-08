package org.rws.mastermind.feedback;

import org.rws.mastermind.code.Code;

/**
 * Factory class for creating {@link Feedback} objects based on the specified type.
 */
public class FeedbackFactory {

    /**
     * Creates a {@link Feedback} instance.
     *
     * @param code The {@link Code} object representing the secret code to compare guesses against.
     * @param type The feedback type, which determines the implementation:
     *             <ul>
     *                 <li>{@code "pattern"}: {@link PatternFeedback}</li>
     *                 <li>{@code "hl"}: {@link HighLowFeedback}</li>
     *                 <li>Default: {@link StandardFeedback}</li>
     *             </ul>
     * @return A {@link Feedback} implementation based on the specified type.
     */
    public static Feedback createFeedback(Code code, String type) {
        return switch (type) {
            case "pattern" -> new PatternFeedback(code);
            case "hl" -> new HighLowFeedback(code);
            default -> new StandardFeedback(code);
        };
    }
}