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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContextEntity;
import com.premiumminds.billy.core.test.fixtures.MockProductEntity;
import com.premiumminds.billy.core.test.fixtures.MockTaxEntity;

public class TestProductBuilder extends AbstractTest {

	private static final String PRODUCT_YML = "src/test/resources/Product.yml";

	@Test
	public void doTest() {
		MockProductEntity mockProduct = loadFixture(MockProductEntity.class);
		
		Mockito.when(getInstance(DAOProduct.class).getEntityInstance()).thenReturn(new MockProductEntity());
		
		Product.Builder builder = getInstance(Product.Builder.class);
		
		builder.addTaxUID(mockProduct.getTaxes().get(0).getUID()).setCommodityCode(mockProduct.getCommodityCode()).setDescription(mockProduct.getDescription()).setNumberCode(mockProduct.getNumberCode()).setProductCode(mockProduct.getProductCode()).setProductGroup(mockProduct.getProductGroup()).setType(mockProduct.getType()).setUnitOfMeasure(mockProduct.getUnitOfMeasure()).setValuationMethod(mockProduct.getValuationMethod());

		Product product = builder.build();
		
		assert(product != null);
		
		assertEquals(mockProduct.getCommodityCode(), product.getCommodityCode());
		assertEquals(mockProduct.getDescription(), product.getDescription());
		assertEquals(mockProduct.getNumberCode(), product.getNumberCode());
		assertEquals(mockProduct.getProductCode(), product.getProductCode());
		assertEquals(mockProduct.getProductGroup(), product.getProductGroup());
		assertEquals(mockProduct.getUnitOfMeasure(), product.getUnitOfMeasure());
		assertEquals(mockProduct.getValuationMethod(), product.getValuationMethod());
		
	}
	
	public MockProductEntity loadFixture(Class<MockProductEntity> clazz) {
		MockProductEntity result = (MockProductEntity) createMockEntityFromYaml(MockProductEntity.class, PRODUCT_YML);

		result.type = ProductType.GOODS;
		
		MockTaxEntity tax = new MockTaxEntity();
		tax.uid = new UID("uid_tax");
		tax.code = "VAT";
		tax.context = new MockContextEntity();
		tax.currency = Currency.getInstance("EUR");
		tax.taxRateType = TaxRateType.PERCENTAGE;
		tax.percentageRateValue = new BigDecimal("23"); // mistake
		tax.value = new BigDecimal("23");
		result.taxes = Arrays.asList(new Tax[]{tax});
		Mockito.when(getInstance(DAOTax.class).get(Matchers.any(UID.class))).thenReturn(tax);
		
		return result;		
	}
}
