package org.rws.mastermind.codegen;

import org.rws.mastermind.interfaces.CodeGenerator;
import org.rws.mastermind.interfaces.GameSettingsProvider;
import org.rws.mastermind.models.Code;

import java.util.Random;

/**
 * The DefaultCodeGenerator class implements the CodeGenerator interface
 * and provides a method to generate a code for the Mastermind game.
 */
public class DefaultCodeGenerator implements CodeGenerator {
    private final Random random;

    private int codeLength;
    private String validCharacters;

    /**
     * Constructs a DefaultCodeGenerator with the specified GameSettingsProvider.
     *
     * @param settingsProvider The GameSettingsProvider object containing the game settings.
     */
    public DefaultCodeGenerator(GameSettingsProvider settingsProvider) {
        this.codeLength = settingsProvider.getCodeLength();
        this.validCharacters = settingsProvider.getCodeCharsString();
        this.random = new Random();
    }

    /**
     * Generates a code for the Mastermind game.
     *
     * @return A Code object representing the generated code.
     */
    @Override
    public Code generateCode(int lenght) {
        codeLength = lenght;
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            code.append(validCharacters.charAt(random.nextInt(validCharacters.length())));
        }
        return new Code(code.toString(), validCharacters);
    }
}


