package org.rws.mastermind.codegen;

import org.rws.mastermind.interfaces.CodeGenerator;
import org.rws.mastermind.interfaces.GameSettingsProvider;
import org.rws.mastermind.models.Code;

import java.util.Random;

public class DefaultCodeGenerator implements CodeGenerator {
    private final int codeLength;
    private final String validCharacters;
    private final Random random;

    public DefaultCodeGenerator(GameSettingsProvider settingsProvider) {
        this.codeLength = settingsProvider.getCodeLength();
        this.validCharacters = settingsProvider.getCodeOptions();
        this.random = new Random();
    }

    @Override
    public Code generateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            code.append(random.nextInt(8) + 1);
        }
        code.toString();
        return new Code(code.toString(), validCharacters);
    }
}


