/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.util;

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
