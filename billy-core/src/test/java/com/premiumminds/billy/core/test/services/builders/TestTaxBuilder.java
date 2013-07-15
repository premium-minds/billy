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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Currency;

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
	private static final String CONTEXT_YML = "src/test/resources/Context.yml";

	@Test
	public void doTestFlat() {
		MockTaxEntity mockTax = loadFixture(MockTaxEntity.class, TAX_YML);
		Mockito.when(getInstance(DAOTax.class).getEntityInstance()).thenReturn(
				new MockTaxEntity());

		Tax.Builder builder = getInstance(Tax.Builder.class);
		BigDecimal amount = (mockTax.getTaxRateType() == TaxRateType.FLAT) ? mockTax
				.getFlatRateAmount() : mockTax.getPercentageRateValue();

		builder.setCode(mockTax.getCode())
				.setContextUID(mockTax.getContext().getUID())
				.setCurrency(mockTax.getCurrency())
				.setDescription(mockTax.getDescription())
				.setDesignation(mockTax.getDesignation())
				.setValidFrom(mockTax.getValidFrom())
				.setValidTo(mockTax.getValidTo())
				.setTaxRate(mockTax.getTaxRateType(), amount)
				.setValue(mockTax.getValue());

		Tax tax = builder.build();

		assert (tax != null);
		assertEquals(mockTax.getCode(), tax.getCode());
		assertEquals(mockTax.getContext(), tax.getContext());
		assertEquals(mockTax.getCurrency(), tax.getCurrency());
		assertEquals(mockTax.getDescription(), tax.getDescription());
		assertEquals(mockTax.getDesignation(), tax.getDesignation());
		assertEquals(mockTax.getTaxRateType(), tax.getTaxRateType());
		assertEquals(mockTax.getValue(), tax.getValue());

		if (mockTax.getTaxRateType() == Tax.TaxRateType.FLAT) {
			assertEquals(mockTax.getFlatRateAmount(), tax.getFlatRateAmount());
			assertThat(mockTax.getPercentageRateValue(),
					is(not(tax.getPercentageRateValue())));
		} else {
			assertEquals(mockTax.getPercentageRateValue(),
					tax.getPercentageRateValue());
			assertThat(mockTax.getFlatRateAmount(),
					is(not(tax.getFlatRateAmount())));
		}
	}

	public MockTaxEntity loadFixture(Class<MockTaxEntity> clazz, String path) {
		MockTaxEntity result = (MockTaxEntity) createMockEntity(
				generateMockEntityConstructor(MockTaxEntity.class), path);

		result.uid = new UID("uid_tax");

		MockContextEntity mockContext = (MockContextEntity) createMockEntity(
				generateMockEntityConstructor(MockContextEntity.class),
				CONTEXT_YML);

		mockContext.uid = new UID("uid_context");
		Mockito.when(getInstance(DAOContext.class).get(Matchers.any(UID.class)))
				.thenReturn(mockContext);
		result.context = mockContext;

		result.currency = Currency.getInstance("EUR");

		return result;
	}
}
