package com.premiumminds.billy.spain.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ESFinancialValidatorTest {

    @Test
    public void testValidCifBarcelona() {
        ESFinancialValidator validator = new ESFinancialValidator("A58250606");
        assertTrue(validator.isValid());
    }

    @Test
    public void testValidCifMadrid() {
        ESFinancialValidator validator = new ESFinancialValidator("A28250603");
        assertTrue(validator.isValid());
    }

    @Test
    public void testValidNif() {
        ESFinancialValidator validator = new ESFinancialValidator("12825060F");
        assertTrue(validator.isValid());
    }

    @Test
    public void testInvalidNumberOfDigits() {
        ESFinancialValidator validator = new ESFinancialValidator("12345678");
        assertFalse(validator.isValid());
    }

    @Test
    public void testInvalidRegex() {
        ESFinancialValidator validator = new ESFinancialValidator("ABCDEFGHI");
        assertFalse(validator.isValid());
    }

    @Test
    public void testInvalidNif() {
        ESFinancialValidator validator = new ESFinancialValidator("12825060E");
        assertFalse(validator.isValid());
    }

    @Test
    public void testInvalidCifOrganization() {
        ESFinancialValidator validator = new ESFinancialValidator("I58250606");
        assertFalse(validator.isValid());
    }

}
