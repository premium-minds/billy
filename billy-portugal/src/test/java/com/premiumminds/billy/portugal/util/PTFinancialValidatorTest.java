package com.premiumminds.billy.portugal.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PTFinancialValidatorTest {

    @Test
    public void testValidNif() {
        PTFinancialValidator validator = new PTFinancialValidator("241250609");
        assertTrue(validator.isValid());
    }

    @Test
    public void testValidNifUsual() {
        PTFinancialValidator validator = new PTFinancialValidator("123456789");
        assertTrue(validator.isValid());
    }

    @Test
    public void testValidNif0term() {
        PTFinancialValidator validator = new PTFinancialValidator("504426290");
        assertTrue(validator.isValid());
    }

    @Test
    public void testInvalidNif() {
        PTFinancialValidator validator = new PTFinancialValidator("124456789");
        assertFalse(validator.isValid());
    }

    @Test
    public void testAnotherInvalidNif() {
        PTFinancialValidator validator = new PTFinancialValidator("505646780");
        assertFalse(validator.isValid());
    }

    @Test
    public void testValidDoubleDigit() {
        PTFinancialValidator validator = new PTFinancialValidator("451234561");
        assertTrue(validator.isValid());
    }

}