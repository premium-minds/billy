/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTRegionContextEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTTaxEntity;

public class TestPTTaxBuilder extends PTAbstractTest {

    private static final String PTTAX_YML = AbstractTest.YML_CONFIGS_DIR + "PTTax.yml";
    private static final String REGIONCONTEXT_YML = AbstractTest.YML_CONFIGS_DIR + "PTContext.yml";

    @Test
    public void doTestFlat() {
        MockPTTaxEntity mockTax = this.loadFixture(MockPTTaxEntity.class, TestPTTaxBuilder.PTTAX_YML);
        Mockito.when(this.getInstance(DAOPTTax.class).getEntityInstance()).thenReturn(new MockPTTaxEntity());

        PTTax.Builder builder = this.getInstance(PTTax.Builder.class);
        BigDecimal amount = (mockTax.getTaxRateType() == TaxRateType.FLAT) ? mockTax.getFlatRateAmount()
                : mockTax.getPercentageRateValue();

        builder.setCode(mockTax.getCode()).setContextUID(mockTax.getContext().getUID())
                .setCurrency(mockTax.getCurrency()).setDescription(mockTax.getDescription())
                .setDesignation(mockTax.getDesignation()).setValidFrom(mockTax.getValidFrom())
                .setValidTo(mockTax.getValidTo()).setTaxRate(mockTax.getTaxRateType(), amount)
                .setValue(mockTax.getValue());

        PTTax tax = builder.build();

        assert (tax != null);
        Assertions.assertEquals(mockTax.getCode(), tax.getCode());
        Assertions.assertEquals(mockTax.getContext(), tax.getContext());
        Assertions.assertEquals(mockTax.getCurrency(), tax.getCurrency());
        Assertions.assertEquals(mockTax.getDescription(), tax.getDescription());
        Assertions.assertEquals(mockTax.getDesignation(), tax.getDesignation());
        Assertions.assertEquals(mockTax.getTaxRateType(), tax.getTaxRateType());
        Assertions.assertEquals(mockTax.getValue(), tax.getValue());

        if (mockTax.getTaxRateType() == PTTax.TaxRateType.FLAT) {
            Assertions.assertEquals(mockTax.getFlatRateAmount(), tax.getFlatRateAmount());
            Assertions.assertNotEquals(mockTax.getPercentageRateValue(), tax.getPercentageRateValue());
        } else {
            Assertions.assertEquals(mockTax.getPercentageRateValue(), tax.getPercentageRateValue());
            Assertions.assertNotEquals(mockTax.getFlatRateAmount(), tax.getFlatRateAmount());
        }
    }

    public MockPTTaxEntity loadFixture(Class<MockPTTaxEntity> clazz, String path) {
        MockPTTaxEntity result = this.createMockEntity(MockPTTaxEntity.class, path);

        result.uid = new UID("uid_tax");

        MockPTRegionContextEntity mockContext =
                this.createMockEntity(MockPTRegionContextEntity.class, TestPTTaxBuilder.REGIONCONTEXT_YML);

        mockContext.uid = new UID("uid_region_context");
        Mockito.when(this.getInstance(DAOPTRegionContext.class).get(Mockito.any(UID.class))).thenReturn(mockContext);
        result.context = mockContext;

        result.currency = Currency.getInstance("EUR");

        return result;
    }
}
