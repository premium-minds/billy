/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.test.services.builders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockProductEntity;

public class TestProductBuilder extends AbstractTest {

	private static final String PRODUCT_YML = "src/test/resources/Product.yml";

	@Test
	public void doTest() {
		MockProductEntity mockProduct = (MockProductEntity) createMockEntityFromYaml(
				MockProductEntity.class, PRODUCT_YML);

		DAOProduct mockDaoProduct = this.getMock(DAOProduct.class);

		Mockito.when(mockDaoProduct.getEntityInstance()).thenReturn(
				new MockProductEntity());

		Product.Builder builder = new Product.Builder(mockDaoProduct);

		builder.setDescription(mockProduct.getDescription())
				.setCommodityCode(mockProduct.getCommodityCode())
				.setNumberCode(mockProduct.getNumberCode())
				.setProductCode(mockProduct.getProductCode())
				.setProductGroup(mockProduct.getProductGroup())
				.setType(mockProduct.getType())
				.setUnitOfMeasure(mockProduct.getUnitOfMeasure())
				.setValuationMethod(mockProduct.getValuationMethod());

		Product product = builder.build();

		assert (product != null);
		assertEquals(mockProduct.getCommodityCode(), product.getCommodityCode());
		assertEquals(mockProduct.getDescription(), product.getDescription());
		assertEquals(mockProduct.getNumberCode(), product.getNumberCode());
		assertEquals(mockProduct.getProductCode(), product.getProductCode());
		assertEquals(mockProduct.getProductGroup(), product.getProductGroup());
		assertEquals(mockProduct.getType(), product.getType());
		assertEquals(mockProduct.getUnitOfMeasure(), product.getUnitOfMeasure());
		assertEquals(mockProduct.getValuationMethod(),
				product.getValuationMethod());

	}
}
