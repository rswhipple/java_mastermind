/**
 * The {@code CodeFactory} class provides a method to create a {@link Code} instance
 * based on the game settings and HTTP handler provided. It dynamically selects
 * the appropriate code generation strategy based on the code type specified in
 * the settings.
 *
 * <p>
 * This factory supports two types of code generation:
 * <ul>
 *   <li>User-generated codes using {@link UserCodeGenerator}</li>
 *   <li>Randomly generated codes using {@link RandomCodeGenerator}</li>
 * </ul>
 * </p>
 */
package org.rws.mastermind.code;

import org.rws.mastermind.http.HttpHandler;
import org.rws.mastermind.settings.GameSetter;


public class CodeFactory {

    /**
     * Creates a {@link Code} instance based on the settings provided.
     *
     * @param settings The {@link GameSetter} instance containing the game configuration,
     * @param http     The {@link HttpHandler} instance used for network interactions
     *                 if required by the random code generator.
     * @return A {@link Code} instance generated using the appropriate strategy.
     *
     * <p>
     * Example usage:
     * <pre>
     * GameSetter settings = new GameSetter();
     * HttpHandler httpHandler = new HttpHandler();
     * Code code = CodeFactory.createCode(settings, httpHandler);
     * </pre>
     * </p>
     */
    public static Code createCode(GameSetter settings, HttpHandler http) {
        String type = settings.getCodeType();
        if (type.equals("user")) {
            UserCodeGenerator userCodeGenerator = new UserCodeGenerator(settings);
            return userCodeGenerator.generateCode();
        }
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator(settings, http);
        return randomCodeGenerator.generateCode();
    }
}
