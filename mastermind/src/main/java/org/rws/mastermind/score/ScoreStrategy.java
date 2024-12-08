package org.rws.mastermind.score;

public interface ScoreStrategy {

    String score(String guess, String secretCode, int varLength);
}