package org.rws.mastermind.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new Validator(4, "RGBY");
    }

    @Test
    void testValidGuess() {
        assertTrue(validator.isValidGuess("RGBY"), "A valid guess should return true");
        assertTrue(validator.isValidGuess("YYYY"), "A valid guess with repeated characters should return true");
    }

    @Test
    void testInvalidLengthGuess() {
        assertFalse(validator.isValidGuess("RGB"), "A guess with fewer characters should return false");
        assertFalse(validator.isValidGuess("RGBYY"), "A guess with more characters should return false");
    }

    @Test
    void testInvalidCharacterGuess() {
        assertFalse(validator.isValidGuess("RGXZ"), "A guess with invalid characters should return false");
        assertFalse(validator.isValidGuess("ABCD"), "A guess with completely invalid characters should return false");
    }

    @Test
    void testEmptyGuess() {
        assertFalse(validator.isValidGuess(""), "An empty guess should return false");
    }
}
