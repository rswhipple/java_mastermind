package org.rws.mastermind.feedback;

/**
 * Defines the contract for generating feedback about the accuracy of a guess in the Mastermind game.
 */
public interface Feedback {

    /**
     * Generates feedback about the accuracy of a Mastermind guess.
     *
     * @param guess The user's guess as a string.
     * @return A string representing feedback on the guess.
     */
    String generateFeedback(String guess);
}