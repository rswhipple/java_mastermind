package org.rws.mastermind.models;

import javax.xml.validation.Validator;

import org.rws.mastermind.utils.Scorer;

public class TurnResult {
    private final boolean validGuess;
    private final String feedback;
    private final boolean isWin;

    public TurnResult(boolean validGuess, String feedback, boolean isWin) {
        this.validGuess = validGuess;
        this.feedback = feedback;
        this.isWin = isWin;
    }

    // Getters
    public boolean isValidGuess() {
        return validGuess;
    }

    public String getFeedback() {
        return feedback;
    }

    public boolean isWin() {
        return isWin;
    }

    @Override
    public String toString() {
        return "TurnResult{" +
                "validGuess=" + validGuess +
                ", feedback='" + feedback + '\'' +
                ", isWin=" + isWin +
                '}';
    }

    // Static factory method to process the guess
    public static TurnResult processTurn(
            String guess,
            Validator validator,
            Code secretCode,
            Scorer scorer
    ) {
        return new TurnResult(false, guess, false);
    }
}