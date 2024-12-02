package org.rws.mastermind.models;


import java.util.Arrays;

public class Code {
    private final char[] code;

    public Code(String code) {
        this.code = code.toCharArray();
    }

    public char[] getCode() {
        return code;
    }

    public boolean matches(Code other) {
        return Arrays.equals(this.code, other.getCode());
    }

    @Override
    public String toString() {
        return new String(code);
    }
}
