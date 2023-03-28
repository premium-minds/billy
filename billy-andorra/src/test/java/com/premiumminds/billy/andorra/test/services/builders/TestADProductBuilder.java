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
package com.premiumminds.billy.andorra.test.services.builders;

import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.entities.ADProduct;
import com.premiumminds.billy.andorra.services.entities.ADProduct.Builder;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADProductEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.test.AbstractTest;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADProductBuilder extends ADAbstractTest {

    private static final String AD_PRODUCT_YML = AbstractTest.YML_CONFIGS_DIR + "ADProduct.yml";

    @Test
    public void doTest() {

        MockADProductEntity mockProduct =
                this.createMockEntity(MockADProductEntity.class, TestADProductBuilder.AD_PRODUCT_YML);

        Mockito.when(this.getInstance(DAOADProduct.class).getEntityInstance()).thenReturn(new MockADProductEntity());

        for (TaxEntity tax : mockProduct.getTaxes()) {
            tax.setCurrency(Currency.getInstance("EUR"));
            Mockito.when(this.getInstance(DAOADTax.class).get(Mockito.any())).thenReturn(tax);
        }

        Builder builder = this.getInstance(Builder.class);

        builder.addTaxUID(mockProduct.getTaxes().get(0).getUID()).setCommodityCode(mockProduct.getCommodityCode())
                .setDescription(mockProduct.getDescription()).setNumberCode(mockProduct.getNumberCode())
                .setProductCode(mockProduct.getProductCode()).setProductGroup(mockProduct.getProductGroup())
                .setType(mockProduct.getType()).setUnitOfMeasure(mockProduct.getUnitOfMeasure())
                .setValuationMethod(mockProduct.getValuationMethod());

        ADProduct product = builder.build();

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
