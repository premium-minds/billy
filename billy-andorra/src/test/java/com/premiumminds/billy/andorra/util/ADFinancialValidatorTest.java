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

public class ADFinancialValidatorTest {

    @Test
    public void testValidResidentPerson() {
        ADFinancialValidator validator = new ADFinancialValidator("F123456Z");
        assertTrue(validator.isValid());
    }

    @Test
    public void testInvalidResidentPerson() {
        ADFinancialValidator validator = new ADFinancialValidator("F987654Z");
        assertFalse(validator.isValid());
    }

    @Test
    public void testValidNonResidentPerson() {
        ADFinancialValidator validator = new ADFinancialValidator("E987654Z");
        assertTrue(validator.isValid());
    }

    @Test
    public void testInvalidNonResidentPerson() {
        ADFinancialValidator validator = new ADFinancialValidator("E799999Z");
        assertFalse(validator.isValid());
    }

    @Test
    public void testValidLimitedCompany() {
        ADFinancialValidator validator = new ADFinancialValidator("A799999Z");
        assertTrue(validator.isValid());
    }

	@Test
	public void testInvalidLimitedCompany() {
		ADFinancialValidator validator = new ADFinancialValidator("A699999Z");
		assertFalse(validator.isValid());
	}

	@Test
	public void testValidPrivateLimitedCompany() {
		ADFinancialValidator validator = new ADFinancialValidator("L700000Z");
		assertTrue(validator.isValid());
	}

	@Test
	public void testInvalidPrivateLimitedCompany() {
		ADFinancialValidator validator = new ADFinancialValidator("L800000Z");
		assertFalse(validator.isValid());
	}

	@Test
	public void testValidNonResidentLegalEntity() {
		ADFinancialValidator validator = new ADFinancialValidator("E000000Z");
		assertTrue(validator.isValid());
	}

	@Test
	public void testValidJointOwnershipArrangements() {
		ADFinancialValidator validator = new ADFinancialValidator("C999999H");
		assertTrue(validator.isValid());
	}

	@Test
	public void testValidPublicEntity() {
		ADFinancialValidator validator = new ADFinancialValidator("D000000A");
		assertTrue(validator.isValid());
	}

	@Test
	public void testValidTaxGroup() {
		ADFinancialValidator validator = new ADFinancialValidator("G192837L");
		assertTrue(validator.isValid());
	}

	@Test
	public void testValidCollectiveInvestmentScheme() {
		ADFinancialValidator validator = new ADFinancialValidator("O675849O");
		assertTrue(validator.isValid());
	}

	@Test
	public void testValidAssociationOrFoundation() {
		ADFinancialValidator validator = new ADFinancialValidator("P194826Y");
		assertTrue(validator.isValid());
	}

	@Test
	public void testValidParapublicEntity() {
		ADFinancialValidator validator = new ADFinancialValidator("U741852W");
		assertTrue(validator.isValid());
	}

	@Test
	public void testInvalidRegex() {
		ADFinancialValidator validator = new ADFinancialValidator("A12Z456H");
		assertFalse(validator.isValid());
	}

}
