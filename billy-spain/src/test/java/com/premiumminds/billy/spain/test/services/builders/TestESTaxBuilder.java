/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.test.services.builders;

import java.math.BigDecimal;
import java.util.Currency;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.services.entities.ESTax;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESRegionContextEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESTaxEntity;

public class TestESTaxBuilder extends ESAbstractTest {

	private static final String	ESTAX_YML			= AbstractTest.YML_CONFIGS_DIR
															+ "ESTax.yml";
	private static final String	REGIONCONTEXT_YML	= AbstractTest.YML_CONFIGS_DIR
															+ "ESContext.yml";

	@Test
	public void doTestFlat() {
		MockESTaxEntity mockTax = this.loadFixture(MockESTaxEntity.class,
				TestESTaxBuilder.ESTAX_YML);
		Mockito.when(this.getInstance(DAOESTax.class).getEntityInstance())
				.thenReturn(new MockESTaxEntity());

		ESTax.Builder builder = this.getInstance(ESTax.Builder.class);
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

		ESTax tax = builder.build();

		assert (tax != null);
		Assert.assertEquals(mockTax.getCode(), tax.getCode());
		Assert.assertEquals(mockTax.getContext(), tax.getContext());
		Assert.assertEquals(mockTax.getCurrency(), tax.getCurrency());
		Assert.assertEquals(mockTax.getDescription(), tax.getDescription());
		Assert.assertEquals(mockTax.getDesignation(), tax.getDesignation());
		Assert.assertEquals(mockTax.getTaxRateType(), tax.getTaxRateType());
		Assert.assertEquals(mockTax.getValue(), tax.getValue());

		if (mockTax.getTaxRateType() == ESTax.TaxRateType.FLAT) {
			Assert.assertEquals(mockTax.getFlatRateAmount(),
					tax.getFlatRateAmount());
			Assert.assertThat(mockTax.getPercentageRateValue(), CoreMatchers
					.is(CoreMatchers.not(tax.getPercentageRateValue())));
		} else {
			Assert.assertEquals(mockTax.getPercentageRateValue(),
					tax.getPercentageRateValue());
			Assert.assertThat(mockTax.getFlatRateAmount(),
					CoreMatchers.is(CoreMatchers.not(tax.getFlatRateAmount())));
		}
	}

	public MockESTaxEntity loadFixture(Class<MockESTaxEntity> clazz, String path) {
		MockESTaxEntity result = this.createMockEntity(MockESTaxEntity.class,
				path);

		result.uid = new UID("uid_tax");

		MockESRegionContextEntity mockContext = this.createMockEntity(
				MockESRegionContextEntity.class,
				TestESTaxBuilder.REGIONCONTEXT_YML);

		mockContext.uid = new UID("uid_region_context");
		Mockito.when(
				this.getInstance(DAOESRegionContext.class).get(
						Matchers.any(UID.class))).thenReturn(mockContext);
		result.context = mockContext;

		result.currency = Currency.getInstance("EUR");

		return result;
	}
}