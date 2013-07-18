/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTRegionContextEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTTaxEntity;

public class TestPTTaxBuilder extends PTAbstractTest {

	private static final String PTTAX_YML = "src/test/resources/PTTax.yml";
	private static final String REGIONCONTEXT_YML = "src/test/resources/PTContext.yml";

	@Test
	public void doTestFlat() {
		MockPTTaxEntity mockTax = loadFixture(MockPTTaxEntity.class, PTTAX_YML);
		Mockito.when(getInstance(DAOPTTax.class).getEntityInstance())
				.thenReturn(new MockPTTaxEntity());

		PTTax.Builder builder = getInstance(PTTax.Builder.class);
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

		PTTax tax = builder.build();

		assert (tax != null);
		assertEquals(mockTax.getCode(), tax.getCode());
		assertEquals(mockTax.getContext(), tax.getContext());
		assertEquals(mockTax.getCurrency(), tax.getCurrency());
		assertEquals(mockTax.getDescription(), tax.getDescription());
		assertEquals(mockTax.getDesignation(), tax.getDesignation());
		assertEquals(mockTax.getTaxRateType(), tax.getTaxRateType());
		assertEquals(mockTax.getValue(), tax.getValue());

		if (mockTax.getTaxRateType() == PTTax.TaxRateType.FLAT) {
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

	public MockPTTaxEntity loadFixture(Class<MockPTTaxEntity> clazz, String path) {
		MockPTTaxEntity result = createMockEntity(MockPTTaxEntity.class, path);

		result.uid = new UID("uid_tax");

		MockPTRegionContextEntity mockContext = createMockEntity(
				MockPTRegionContextEntity.class, REGIONCONTEXT_YML);

		mockContext.uid = new UID("uid_region_context");
		Mockito.when(
				getInstance(DAOPTRegionContext.class).get(
						Matchers.any(UID.class))).thenReturn(mockContext);
		result.context = mockContext;

		result.currency = Currency.getInstance("EUR");

		return result;
	}
}