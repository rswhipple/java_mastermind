package org.rws.mastermind.code;

import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.settings.GameSetter;

import java.util.Random;

/**
 * The DefaultCodeGenerator class implements the CodeGenerator interface
 * and provides a method to generate a code for the Mastermind game.
 */
public class RandomCodeGenerator implements CodeGenerator {
    private final Random random;

    private int codeLength;
    private int min;
    private int max;
    private String validCharacters;
    private String baseUrl;
    private HttpHandler httpHandler;

    /**
     * Constructs a DefaultCodeGenerator with the specified GameSettingsProvider.
     *
     * @param settingsProvider The GameSettingsProvider object containing the game settings.
     */
    public RandomCodeGenerator(GameSetter settingsProvider, HttpHandler httpHandler) {
        this.httpHandler = httpHandler;
        this.baseUrl = "http://www.random.org/integers/";
        this.random = new Random();

        this.codeLength = settingsProvider.getCodeLength();
        this.validCharacters = settingsProvider.getCodeCharsString();
        this.min = 0;
        this.max = validCharacters.length() - 1;
    }

    /**
     * Generates a code for the Mastermind game.
     *
     * @return A Code object representing the generated code.
     */
    @Override
    public Code generateCode(){
        String params = String.format(
            "?num=%d&min=%d&max=%d&col=1&base=10&format=plain&rnd=new", 
            codeLength, min, max
        );
        String url = baseUrl + params;

        try {
            String response = httpHandler.get(url);
            return new Code(response, validCharacters);
        } catch (Exception e) {
            return backupGenerateCode();
        }
    }

    /**
     * Generates a code for the Mastermind game using a backup method.
     * @return
     */
    public Code backupGenerateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            code.append(validCharacters.charAt(random.nextInt(validCharacters.length())));
        }
        return new Code(code.toString(), validCharacters);
    }

    /**
     * Generates a code for the Mastermind game using user-generated input.
     *
     * @param codeString The string representing the user's validated code.
     * @return A Code object representing the generated code.
     */
    public Code userGeneratedCode(String codeString) {
        return new Code(codeString, validCharacters);
    }

    /**
     * Resets the code length to the specified length.
     *
     * @param length The new length of the code.
     */
    public void resetCodeLength(int length) {
        codeLength = length;
    }
}


