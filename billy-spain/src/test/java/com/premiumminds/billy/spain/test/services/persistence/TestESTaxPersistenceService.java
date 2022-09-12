/*
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
package com.premiumminds.billy.spain.test.services.persistence;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Currency;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.spain.services.entities.ESRegionContext;
import com.premiumminds.billy.spain.services.entities.ESTax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestESTaxPersistenceService extends ESPersistenceServiceAbstractTest {

    @Test
    public void testDuplicateFails() {

        final ESRegionContext barcelona = billy.contexts().continent().barcelona();

        ESTax.Builder builder1 = createBuilder(barcelona);
        ESTax.Builder builder2 = createBuilder(barcelona);

        final ESTax tax = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax);

        Assertions.assertThrows(BillyRuntimeException.class, () -> this.billy.taxes().persistence().create(builder2));

    }

    @Test
    public void testSameCodeDifferentDatesSuccess() {

        final ESRegionContext barcelona = billy.contexts().continent().barcelona();

        ESTax.Builder builder1 = createBuilder(barcelona)
                .setValidFrom(new java.util.Date(0))
                .setValidTo(new java.util.Date(100L));

        ESTax.Builder builder2 = createBuilder(barcelona)
                .setValidFrom(new java.util.Date(100L))
                .setValidTo(new java.util.Date(200L));

        final ESTax tax1 = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax1);

        final ESTax tax2 = this.billy.taxes().persistence().create(builder2);
        Assertions.assertNotNull(tax2);
    }


    @Test
    public void testSameDatesDiferentCodesSuccess() {

        final ESRegionContext barcelona = billy.contexts().continent().barcelona();

        ESTax.Builder builder1 = createBuilder(barcelona)
                .setCode("code1");

        ESTax.Builder builder2 = createBuilder(barcelona)
                .setCode("code2");

        final ESTax tax1 = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax1);

        final ESTax tax2 = this.billy.taxes().persistence().create(builder2);
        Assertions.assertNotNull(tax2);
    }


    private ESTax.Builder createBuilder(ESRegionContext madrid) {
        return this.getInstance(ESTax.Builder.class)
                .setContextUID(madrid.getUID())
                .setTaxRate(Tax.TaxRateType.PERCENTAGE, new BigDecimal("3.14"))
                .setCode("code1")
                .setDescription("description 1")
                .setValidFrom(new Date(0))
                .setValidTo(new Date(1000L))
                .setCurrency(Currency.getInstance("EUR"));
    }
}
