package org.rws.mastermind.code;

import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.settings.GameSetter;

import java.util.Random;

/**
 * The {@code RandomCodeGenerator} class implements the {@link CodeGenerator} interface
 * and provides methods to generate a code for the Mastermind game.
 * It uses either an online random number generator service or a local fallback
 * mechanism to create the code.
 */
public class RandomCodeGenerator implements CodeGenerator {
    private final Random random;

    private int codeLength;
    private final int min;
    private final int max;
    private final String validCharacters;
    private final String baseUrl;
    private final HttpHandler httpHandler;

    /**
     * Constructs a {@code RandomCodeGenerator} with the specified game settings and HTTP handler.
     *
     * @param settingsProvider The {@link GameSetter} object containing the game settings,
     *                         such as code length and valid characters.
     * @param httpHandler       The {@link HttpHandler} used for making HTTP requests to external services.
     */
    public RandomCodeGenerator(GameSetter settingsProvider, HttpHandler httpHandler) {
        this.httpHandler = httpHandler;
        this.baseUrl = "http://www.random.org/integers/";
        this.random = new Random();

        this.codeLength = settingsProvider.getCodeLength();
        this.validCharacters = settingsProvider.getCodeCharString();
        this.min = 0;
        this.max = validCharacters.length() - 1;
    }

    /**
     * Generates a code for the Mastermind game using an external random number generator service.
     * Falls back to a local generation method in case of errors.
     *
     * @return A {@link Code} object representing the generated code.
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
     * Generates a code locally using random numbers as a fallback mechanism.
     *
     * @return A {@link Code} object representing the generated code.
     */
    public Code backupGenerateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            code.append(validCharacters.charAt(random.nextInt(validCharacters.length())));
        }
        return new Code(code.toString(), validCharacters);
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


