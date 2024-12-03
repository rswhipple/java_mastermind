package org.rws.mastermind.interfaces;

import org.rws.mastermind.models.Code;

/**
 * The FeedbackGenerator interface provides a method to generate feedback for a guess in the Mastermind game.
 */
public interface FeedbackGenerator {

    /**
     * Generates feedback for a given guess compared to the secret code.
     *
     * @param secretCode A Code object representing the secret code to be guessed.
     * @param guess The player's guess.
     * @return A string representing the feedback for the guess.
     */
    String generateFeedback(Code code, String guess);
}