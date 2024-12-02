package java_mastermind.src.main.java.org.rws.mastermind.utils;

public class Validator {
    private static int codeLength;
    private static String validCharacters;

    public Validator(int codeLength, String validCharacters) {
        Validator.codeLength = codeLength;
        Validator.validCharacters = validCharacters;
    }

    public static boolean isValidGuess(String guess) {
        if (guess.length() != codeLength) {
            return false;
        }

        for (char c : guess.toCharArray()) {
            if (!validCharacters.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }
}