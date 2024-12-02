package org.rws.mastermind.utils;

public class Scorer {
    public static int[] score(String guess, String code) {
        int[] result = new int[2];
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == code.charAt(i)) {
                result[0]++;
            } else if (code.contains(String.valueOf(guess.charAt(i)))) {
                result[1]++;
            }
        }
        return result;
    }
}