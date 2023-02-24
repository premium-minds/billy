/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.services.builders;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.services.entities.FRTax;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRRegionContextEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRTaxEntity;

public class TestFRTaxBuilder extends FRAbstractTest {

    private static final String FRTAX_YML = AbstractTest.YML_CONFIGS_DIR + "FRTax.yml";
    private static final String REGIONCONTEXT_YML = AbstractTest.YML_CONFIGS_DIR + "FRContext.yml";

    @Test
    public void doTestFlat() {
        MockFRTaxEntity mockTax = this.loadFixture(MockFRTaxEntity.class, TestFRTaxBuilder.FRTAX_YML);
        Mockito.when(this.getInstance(DAOFRTax.class).getEntityInstance()).thenReturn(new MockFRTaxEntity());

        FRTax.Builder builder = this.getInstance(FRTax.Builder.class);
        BigDecimal amount = (mockTax.getTaxRateType() == TaxRateType.FLAT) ? mockTax.getFlatRateAmount()
                : mockTax.getPercentageRateValue();

        builder.setCode(mockTax.getCode()).setContextUID(mockTax.getContext().getUID())
                .setCurrency(mockTax.getCurrency()).setDescription(mockTax.getDescription())
                .setDesignation(mockTax.getDesignation()).setValidFrom(mockTax.getValidFrom())
                .setValidTo(mockTax.getValidTo()).setTaxRate(mockTax.getTaxRateType(), amount)
                .setValue(mockTax.getValue());

        FRTax tax = builder.build();

        Assertions.assertNotNull(tax);
        Assertions.assertEquals(mockTax.getCode(), tax.getCode());
        Assertions.assertEquals(mockTax.getContext(), tax.getContext());
        Assertions.assertEquals(mockTax.getCurrency(), tax.getCurrency());
        Assertions.assertEquals(mockTax.getDescription(), tax.getDescription());
        Assertions.assertEquals(mockTax.getDesignation(), tax.getDesignation());
        Assertions.assertEquals(mockTax.getTaxRateType(), tax.getTaxRateType());
        Assertions.assertEquals(mockTax.getValue(), tax.getValue());

        if (mockTax.getTaxRateType() == FRTax.TaxRateType.FLAT) {
            Assertions.assertEquals(mockTax.getFlatRateAmount(), tax.getFlatRateAmount());
            Assertions.assertNotEquals(mockTax.getPercentageRateValue(), tax.getPercentageRateValue());
        } else {
            Assertions.assertEquals(mockTax.getPercentageRateValue(), tax.getPercentageRateValue());
            Assertions.assertNotEquals(mockTax.getFlatRateAmount(), tax.getFlatRateAmount());
        }
    }

    public MockFRTaxEntity loadFixture(Class<MockFRTaxEntity> clazz, String path) {
        MockFRTaxEntity result = this.createMockEntity(MockFRTaxEntity.class, path);

        result.uid = StringID.fromValue("uid_tax");

        MockFRRegionContextEntity mockContext =
                this.createMockEntity(MockFRRegionContextEntity.class, TestFRTaxBuilder.REGIONCONTEXT_YML);

        mockContext.uid = StringID.fromValue("uid_region_context");
        Mockito.when(this.getInstance(DAOFRRegionContext.class).get(Mockito.any())).thenReturn(mockContext);
        result.context = mockContext;

        result.currency = Currency.getInstance("EUR");

        return result;
    }
}
