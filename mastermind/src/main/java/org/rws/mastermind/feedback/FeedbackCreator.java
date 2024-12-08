package org.rws.mastermind.feedback;

/**
 *
 */
public interface FeedbackCreator {

    /**
     * Generates feedback about the accuracy of a Mastermind guess.
     *
     * @param guess The user's guess.
     * @return A String representing the feedback.
     */
    String generateFeedback(String guess);
}