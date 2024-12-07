package org.rws.mastermind.code;

/**
 * The CodeGenerator interface provides a method to generate a code for the Mastermind game.
 */
public interface CodeGenerator {

    /**
     * Generates a code for the Mastermind game.
     *
     * @return A Code object representing the generated code.
     */
    Code generateCode();
}