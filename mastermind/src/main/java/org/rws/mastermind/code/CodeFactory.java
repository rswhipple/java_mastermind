package org.rws.mastermind.code;

import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.settings.GameSetter;

public class CodeFactory {
    public static Code createCode(GameSetter settings, HttpHandler http, String type) {
        if (type.equals("user")) {
            UserCodeGenerator userCodeGenerator = new UserCodeGenerator(settings);
            return userCodeGenerator.generateCode();
        }
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator(settings, http);
        return randomCodeGenerator.generateCode();
    }
}
