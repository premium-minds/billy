/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Currency;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTProductEntity;

public class TestPTProductBuilder extends PTAbstractTest {

	private static final String PTPRODUCT_YML = "src/test/resources/PTProduct.yml";

	@Test
	public void doTest() {

		MockPTProductEntity mockProduct = createMockEntity(
				MockPTProductEntity.class, PTPRODUCT_YML);

		Mockito.when(getInstance(DAOPTProduct.class).getEntityInstance())
				.thenReturn(new MockPTProductEntity());

		for (TaxEntity tax : mockProduct.getTaxes()) {
			tax.setCurrency(Currency.getInstance("EUR"));
			Mockito.when(
					getInstance(DAOPTTax.class).get(Matchers.any(UID.class)))
					.thenReturn(tax);
		}

		PTProduct.Builder builder = getInstance(PTProduct.Builder.class);

		builder.addTaxUID(mockProduct.getTaxes().get(0).getUID())
				.setCommodityCode(mockProduct.getCommodityCode())
				.setDescription(mockProduct.getDescription())
				.setNumberCode(mockProduct.getNumberCode())
				.setProductCode(mockProduct.getProductCode())
				.setProductGroup(mockProduct.getProductGroup())
				.setType(mockProduct.getType())
				.setUnitOfMeasure(mockProduct.getUnitOfMeasure())
				.setValuationMethod(mockProduct.getValuationMethod());

		PTProduct product = builder.build();

		assertTrue(product != null);

		assertEquals(mockProduct.getCommodityCode(), product.getCommodityCode());
		assertEquals(mockProduct.getDescription(), product.getDescription());
		assertEquals(mockProduct.getNumberCode(), product.getNumberCode());
		assertEquals(mockProduct.getProductCode(), product.getProductCode());
		assertEquals(mockProduct.getProductGroup(), product.getProductGroup());
		assertEquals(mockProduct.getUnitOfMeasure(), product.getUnitOfMeasure());
		assertEquals(mockProduct.getValuationMethod(),
				product.getValuationMethod());
	}

}
