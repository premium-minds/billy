/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy core.
 * 
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.services.builders;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContextEntity;
import com.premiumminds.billy.core.test.fixtures.MockTaxEntity;

public class TestTaxBuilder extends AbstractTest {

	private static final String TAX_YML = "src/test/resources/Tax.yml";

	@Test
	public void doTest() {
		MockTaxEntity mockTax = loadFixture(MockTaxEntity.class);
		Mockito.when(getInstance(DAOTax.class).getEntityInstance()).thenReturn(
				new MockTaxEntity());

		Tax.Builder builder = getInstance(Tax.Builder.class);

		builder.setCode(mockTax.getCode())
				.setContextUID(mockTax.getContext().getUID())
				.setCurrency(mockTax.getCurrency())
				.setDescription(mockTax.getDescription())
				.setDesignation(mockTax.getDesignation())
				.setTaxRate(mockTax.getTaxRateType(), mockTax.getValue())
				.setValidFrom(mockTax.getValidFrom())
				.setValidTo(mockTax.getValidTo());

		Tax tax = builder.build();

		assert (tax != null);
	}

	public MockTaxEntity loadFixture(Class<MockTaxEntity> clazz) {
		MockTaxEntity result = (MockTaxEntity) createMockEntityFromYaml(
				MockTaxEntity.class, TAX_YML);

		result.uid = new UID("uid_tax");

		MockContextEntity mockContext = new MockContextEntity();
		mockContext.uid = new UID("uid_context");
		Mockito.when(getInstance(DAOContext.class).get(Matchers.any(UID.class)))
				.thenReturn(mockContext);
		result.context = mockContext;

		result.currency = Currency.getInstance("EUR");
		result.taxRateType = TaxRateType.PERCENTAGE;
		result.percentageRateValue = new BigDecimal("23"); // mistake
		result.flatRateAmount = new BigDecimal("23");
		result.value = new BigDecimal("23");
		result.validFrom = new Date();
		result.validTo = new Date();

		return result;
	}
}
