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
package com.premiumminds.billy.france.test.services.persistence;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Currency;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.france.services.entities.FRRegionContext;
import com.premiumminds.billy.france.services.entities.FRTax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFRTaxPersistenceService extends FRPersistenceServiceAbstractTest {

    @Test
    public void testDuplicateFails() {

        final FRRegionContext bretagne = billy.contexts().continent().bretagne();

        FRTax.Builder builder1 = createBuilder(bretagne);
        FRTax.Builder builder2 = createBuilder(bretagne);

        final FRTax tax = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax);

        Assertions.assertThrows(BillyRuntimeException.class, () -> this.billy.taxes().persistence().create(builder2));

    }

    @Test
    public void testSameCodeDifferentDatesSuccess() {

        final FRRegionContext bretagne = billy.contexts().continent().bretagne();

        FRTax.Builder builder1 = createBuilder(bretagne)
                .setValidFrom(new java.util.Date(0))
                .setValidTo(new java.util.Date(100L));

        FRTax.Builder builder2 = createBuilder(bretagne)
                .setValidFrom(new java.util.Date(100L))
                .setValidTo(new java.util.Date(200L));

        final FRTax tax1 = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax1);

        final FRTax tax2 = this.billy.taxes().persistence().create(builder2);
        Assertions.assertNotNull(tax2);
    }


    @Test
    public void testSameDatesDiferentCodesSuccess() {

        final FRRegionContext bretagne = billy.contexts().continent().bretagne();

        FRTax.Builder builder1 = createBuilder(bretagne)
                .setCode("code1");

        FRTax.Builder builder2 = createBuilder(bretagne)
                .setCode("code2");

        final FRTax tax1 = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax1);

        final FRTax tax2 = this.billy.taxes().persistence().create(builder2);
        Assertions.assertNotNull(tax2);
    }


    private FRTax.Builder createBuilder(FRRegionContext madrid) {
        return this.getInstance(FRTax.Builder.class)
                .setContextUID(madrid.getUID())
                .setTaxRate(Tax.TaxRateType.PERCENTAGE, new BigDecimal("3.14"))
                .setCode("code1")
                .setDescription("description 1")
                .setValidFrom(new Date(0))
                .setValidTo(new Date(1000L))
                .setCurrency(Currency.getInstance("EUR"));
    }
}
