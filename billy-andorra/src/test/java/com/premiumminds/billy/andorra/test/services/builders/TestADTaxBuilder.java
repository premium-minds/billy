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

import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.entities.ADTax;
import com.premiumminds.billy.andorra.services.entities.ADTax.Builder;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADRegionContextEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADTaxEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.test.AbstractTest;
import java.math.BigDecimal;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADTaxBuilder extends ADAbstractTest {

    private static final String ESTAX_YML = AbstractTest.YML_CONFIGS_DIR + "ESTax.yml";
    private static final String REGIONCONTEXT_YML = AbstractTest.YML_CONFIGS_DIR + "ESContext.yml";

    @Test
    public void doTestFlat() {
        MockADTaxEntity mockTax = this.loadFixture(MockADTaxEntity.class, TestADTaxBuilder.ESTAX_YML);
        Mockito.when(this.getInstance(DAOADTax.class).getEntityInstance()).thenReturn(new MockADTaxEntity());

        Builder builder = this.getInstance(Builder.class);
        BigDecimal amount = (mockTax.getTaxRateType() == TaxRateType.FLAT) ? mockTax.getFlatRateAmount()
                : mockTax.getPercentageRateValue();

        builder.setCode(mockTax.getCode()).setContextUID(mockTax.getContext().getUID())
                .setCurrency(mockTax.getCurrency()).setDescription(mockTax.getDescription())
                .setDesignation(mockTax.getDesignation()).setValidFrom(mockTax.getValidFrom())
                .setValidTo(mockTax.getValidTo()).setTaxRate(mockTax.getTaxRateType(), amount)
                .setValue(mockTax.getValue());

        ADTax tax = builder.build();

        Assertions.assertNotNull(tax);
        Assertions.assertEquals(mockTax.getCode(), tax.getCode());
        Assertions.assertEquals(mockTax.getContext(), tax.getContext());
        Assertions.assertEquals(mockTax.getCurrency(), tax.getCurrency());
        Assertions.assertEquals(mockTax.getDescription(), tax.getDescription());
        Assertions.assertEquals(mockTax.getDesignation(), tax.getDesignation());
        Assertions.assertEquals(mockTax.getTaxRateType(), tax.getTaxRateType());
        Assertions.assertEquals(mockTax.getValue(), tax.getValue());

        if (mockTax.getTaxRateType() == ADTax.TaxRateType.FLAT) {
            Assertions.assertEquals(mockTax.getFlatRateAmount(), tax.getFlatRateAmount());
            Assertions.assertNotEquals(mockTax.getPercentageRateValue(), tax.getPercentageRateValue());
        } else {
            Assertions.assertEquals(mockTax.getPercentageRateValue(), tax.getPercentageRateValue());
            Assertions.assertNotEquals(mockTax.getFlatRateAmount(), tax.getFlatRateAmount());
        }
    }

    public MockADTaxEntity loadFixture(Class<MockADTaxEntity> clazz, String path) {
        MockADTaxEntity result = this.createMockEntity(MockADTaxEntity.class, path);

        result.uid = StringID.fromValue("uid_tax");

        MockADRegionContextEntity mockContext =
                this.createMockEntity(MockADRegionContextEntity.class, TestADTaxBuilder.REGIONCONTEXT_YML);

        mockContext.uid = StringID.fromValue("uid_region_context");
        Mockito.when(this.getInstance(DAOADRegionContext.class).get(Mockito.any())).thenReturn(mockContext);
        result.context = mockContext;

        result.currency = Currency.getInstance("EUR");

        return result;
    }
}
