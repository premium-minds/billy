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

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockTaxEntity;

public class TestTaxBuilder extends AbstractTest {

    private static final String TAX_YML = AbstractTest.YML_CONFIGS_DIR + "Tax.yml";

    @Test
    public void doTestFlat() {
        MockTaxEntity mockTax = this.createMockEntity(MockTaxEntity.class, TestTaxBuilder.TAX_YML);

        mockTax.setCurrency(Currency.getInstance("EUR"));

        Mockito.when(this.getInstance(DAOTax.class).getEntityInstance()).thenReturn(new MockTaxEntity());

        Mockito.when(this.getInstance(DAOContext.class).get(Mockito.any()))
                .thenReturn((ContextEntity) mockTax.getContext());

        Tax.Builder builder = this.getInstance(Tax.Builder.class);
        BigDecimal amount = (mockTax.getTaxRateType() == TaxRateType.FLAT) ? mockTax.getFlatRateAmount()
                : mockTax.getPercentageRateValue();

        builder.setCode(mockTax.getCode()).setContextUID(mockTax.getContext().getUID())
                .setCurrency(mockTax.getCurrency()).setDescription(mockTax.getDescription())
                .setDesignation(mockTax.getDesignation()).setValidFrom(mockTax.getValidFrom())
                .setValidTo(mockTax.getValidTo()).setTaxRate(mockTax.getTaxRateType(), amount)
                .setValue(mockTax.getValue());

        Tax tax = builder.build();

        Assertions.assertTrue(tax != null);
        Assertions.assertEquals(mockTax.getCode(), tax.getCode());
        Assertions.assertEquals(mockTax.getContext(), tax.getContext());
        Assertions.assertEquals(mockTax.getCurrency(), tax.getCurrency());
        Assertions.assertEquals(mockTax.getDescription(), tax.getDescription());
        Assertions.assertEquals(mockTax.getDesignation(), tax.getDesignation());
        Assertions.assertEquals(mockTax.getTaxRateType(), tax.getTaxRateType());
        Assertions.assertEquals(mockTax.getValue(), tax.getValue());

        if (mockTax.getTaxRateType() == Tax.TaxRateType.FLAT) {
            Assertions.assertEquals(mockTax.getFlatRateAmount(), tax.getFlatRateAmount());
            Assertions.assertNotEquals(mockTax.getPercentageRateValue(), tax.getPercentageRateValue());
        } else {
            Assertions.assertEquals(mockTax.getPercentageRateValue(), tax.getPercentageRateValue());
            Assertions.assertNotEquals(mockTax.getFlatRateAmount(), tax.getFlatRateAmount());
        }
    }
}
