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
package com.premiumminds.billy.andorra.test.services.persistence;

import com.premiumminds.billy.andorra.services.entities.ADRegionContext;
import com.premiumminds.billy.andorra.services.entities.ADTax;
import com.premiumminds.billy.andorra.services.entities.ADTax.Builder;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Currency;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.entities.Tax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestADTaxPersistenceService extends ADPersistenceServiceAbstractTest {

    @Test
    public void testDuplicateFails() {

        final ADRegionContext andorraLaVieja = billy.contexts().andorra().andorraLaVieja();

        Builder builder1 = createBuilder(andorraLaVieja);
        Builder builder2 = createBuilder(andorraLaVieja);

        final ADTax tax = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax);

        Assertions.assertThrows(BillyRuntimeException.class, () -> this.billy.taxes().persistence().create(builder2));

    }

    @Test
    public void testSameCodeDifferentDatesSuccess() {

        final ADRegionContext andorraLaVieja = billy.contexts().andorra().andorraLaVieja();

        ADTax.Builder builder1 = createBuilder(andorraLaVieja)
                .setValidFrom(new java.util.Date(0))
                .setValidTo(new java.util.Date(100L));

        ADTax.Builder builder2 = createBuilder(andorraLaVieja)
                .setValidFrom(new java.util.Date(100L))
                .setValidTo(new java.util.Date(200L));

        final ADTax tax1 = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax1);

        final ADTax tax2 = this.billy.taxes().persistence().create(builder2);
        Assertions.assertNotNull(tax2);
    }


    @Test
    public void testSameDatesDiferentCodesSuccess() {

        final ADRegionContext andorraLaVieja = billy.contexts().andorra().andorraLaVieja();

        ADTax.Builder builder1 = createBuilder(andorraLaVieja)
                .setCode("code1");

        ADTax.Builder builder2 = createBuilder(andorraLaVieja)
                .setCode("code2");

        final ADTax tax1 = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax1);

        final ADTax tax2 = this.billy.taxes().persistence().create(builder2);
        Assertions.assertNotNull(tax2);
    }


    private ADTax.Builder createBuilder(ADRegionContext madrid) {
        return this.getInstance(ADTax.Builder.class)
                .setContextUID(madrid.getUID())
                .setTaxRate(Tax.TaxRateType.PERCENTAGE, new BigDecimal("3.14"))
                .setCode("code1")
                .setDescription("description 1")
                .setValidFrom(new Date(0))
                .setValidTo(new Date(1000L))
                .setCurrency(Currency.getInstance("EUR"));
    }
}
