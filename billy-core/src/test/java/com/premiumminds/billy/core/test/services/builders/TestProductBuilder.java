/*
 * Copyright (C) 2017 Premium Minds.
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

import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockProductEntity;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestProductBuilder extends AbstractTest {

    private static final String PRODUCT_YML = AbstractTest.YML_CONFIGS_DIR + "Product.yml";

    @Test
    public void doTest() {
        MockProductEntity mockProduct = this.createMockEntity(MockProductEntity.class, TestProductBuilder.PRODUCT_YML);

        Mockito.when(this.getInstance(DAOProduct.class).getEntityInstance()).thenReturn(new MockProductEntity());

        for (TaxEntity tax : mockProduct.getTaxes()) {
            tax.setCurrency(Currency.getInstance("EUR"));
            Mockito.when(this.getInstance(DAOTax.class).get(Mockito.any())).thenReturn(tax);
        }

        Product.Builder builder = this.getInstance(Product.Builder.class);

        builder.addTaxUID(mockProduct.getTaxes().get(0).getUID()).setCommodityCode(mockProduct.getCommodityCode())
                .setDescription(mockProduct.getDescription()).setNumberCode(mockProduct.getNumberCode())
                .setProductCode(mockProduct.getProductCode()).setProductGroup(mockProduct.getProductGroup())
                .setType(mockProduct.getType()).setUnitOfMeasure(mockProduct.getUnitOfMeasure())
                .setValuationMethod(mockProduct.getValuationMethod());

        Product product = builder.build();

        Assertions.assertTrue(product != null);

        Assertions.assertEquals(mockProduct.getCommodityCode(), product.getCommodityCode());
        Assertions.assertEquals(mockProduct.getDescription(), product.getDescription());
        Assertions.assertEquals(mockProduct.getNumberCode(), product.getNumberCode());
        Assertions.assertEquals(mockProduct.getProductCode(), product.getProductCode());
        Assertions.assertEquals(mockProduct.getProductGroup(), product.getProductGroup());
        Assertions.assertEquals(mockProduct.getUnitOfMeasure(), product.getUnitOfMeasure());
        Assertions.assertEquals(mockProduct.getValuationMethod(), product.getValuationMethod());

    }
}
