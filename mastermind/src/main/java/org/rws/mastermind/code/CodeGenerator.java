package org.rws.mastermind.code;

/**
 * The {@code CodeGenerator} interface defines a contract for generating codes
 * used in the Mastermind game. Implementing classes must provide a method to
 * generate a {@link Code} object based on specific strategies or input sources.
 */
public interface CodeGenerator {

    /**
     * Generates a code for the Mastermind game.
     *
     * @return A {@link Code} object representing the generated code.
     */
    Code generateCode();
}