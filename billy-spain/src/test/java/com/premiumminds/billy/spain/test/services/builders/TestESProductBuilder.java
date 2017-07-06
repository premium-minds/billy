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

import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.services.entities.ESProduct;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESProductEntity;

public class TestESProductBuilder extends ESAbstractTest {

  private static final String ESPRODUCT_YML = AbstractTest.YML_CONFIGS_DIR + "ESProduct.yml";

  @Test
  public void doTest() {

    MockESProductEntity mockProduct = this.createMockEntity(MockESProductEntity.class,
        TestESProductBuilder.ESPRODUCT_YML);

    Mockito.when(this.getInstance(DAOESProduct.class).getEntityInstance())
        .thenReturn(new MockESProductEntity());

    for (TaxEntity tax : mockProduct.getTaxes()) {
      tax.setCurrency(Currency.getInstance("EUR"));
      Mockito.when(this.getInstance(DAOESTax.class).get(Matchers.any(UID.class))).thenReturn(tax);
    }

    ESProduct.Builder builder = this.getInstance(ESProduct.Builder.class);

    builder.addTaxUID(mockProduct.getTaxes().get(0).getUID())
        .setCommodityCode(mockProduct.getCommodityCode())
        .setDescription(mockProduct.getDescription()).setNumberCode(mockProduct.getNumberCode())
        .setProductCode(mockProduct.getProductCode()).setProductGroup(mockProduct.getProductGroup())
        .setType(mockProduct.getType()).setUnitOfMeasure(mockProduct.getUnitOfMeasure())
        .setValuationMethod(mockProduct.getValuationMethod());

    ESProduct product = builder.build();

    Assert.assertTrue(product != null);

    Assert.assertEquals(mockProduct.getCommodityCode(), product.getCommodityCode());
    Assert.assertEquals(mockProduct.getDescription(), product.getDescription());
    Assert.assertEquals(mockProduct.getNumberCode(), product.getNumberCode());
    Assert.assertEquals(mockProduct.getProductCode(), product.getProductCode());
    Assert.assertEquals(mockProduct.getProductGroup(), product.getProductGroup());
    Assert.assertEquals(mockProduct.getUnitOfMeasure(), product.getUnitOfMeasure());
    Assert.assertEquals(mockProduct.getValuationMethod(), product.getValuationMethod());
  }

}
