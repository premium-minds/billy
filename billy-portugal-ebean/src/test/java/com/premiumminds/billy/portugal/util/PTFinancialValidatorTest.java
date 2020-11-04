/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
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