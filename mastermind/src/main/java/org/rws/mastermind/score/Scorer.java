package org.rws.mastermind.score;

import org.rws.mastermind.code.Code;

/**
 * The Scorer class provides methods to score guesses in the Mastermind game.
 */
public class Scorer {
    private final ScoreStrategy strategy;
    private static Code secretCode;

    /**
     * Constructs a Scorer object with the specified code length and code.
     *
     * @param code The Code object representing the secret code.
     */
    public Scorer(ScoreStrategy strategy, Code code) {
        this.strategy = strategy;
        Scorer.secretCode = code;
    }

    /**
     * Scores a guess compared to the secret code.
     * The result array contains two elements:
     * - result[0]: The number of correct characters in the correct position (black pegs).
     * - result[1]: The number of correct characters in the wrong position (white pegs).
     *
     * @param guess The string representing the player's guess.
     * @return An array of two integers representing the score.
     */
    public String score(String guess) {
        return strategy.score(guess, secretCode.toString(), secretCode.getNumVars());
    }
    
}