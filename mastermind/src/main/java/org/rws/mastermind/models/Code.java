package org.rws.mastermind.models;


import java.util.Arrays;

public class Code {
    private final char[] code;
    private final int length;
    private final String validCharacters;

    public Code(String code, String validCharacters) {
        this.code = code.toCharArray();
        this.length = code.length();
        this.validCharacters = validCharacters;
    }

    public char[] getCode() {
        return code;
    }

    public int getLength() {
        return length;
    }

    public String getValidCharacters() {
        return validCharacters;
    }

    public boolean matches(String guess) {
        return Arrays.equals(code, guess.toCharArray());
    }

    @Override
    public String toString() {
        return new String(code);
    }
}
