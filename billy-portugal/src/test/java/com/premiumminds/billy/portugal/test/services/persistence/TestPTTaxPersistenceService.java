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
package com.premiumminds.billy.portugal.test.services.persistence;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Currency;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPTTaxPersistenceService extends PTPersistenceServiceAbstractTest {

    @Test
    public void testDuplicateFails() {

        final PTRegionContext leiria = billy.contexts().continent().leiria();

        PTTax.Builder builder1 = createBuilder(leiria);
        PTTax.Builder builder2 = createBuilder(leiria);

        final PTTax tax = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax);

        Assertions.assertThrows(BillyRuntimeException.class, () -> this.billy.taxes().persistence().create(builder2));

    }

    @Test
    public void testSameCodeDifferentDatesSuccess() {

        final PTRegionContext leiria = billy.contexts().continent().leiria();

        PTTax.Builder builder1 = createBuilder(leiria)
                .setValidFrom(new java.util.Date(0))
                .setValidTo(new java.util.Date(100L));

        PTTax.Builder builder2 = createBuilder(leiria)
                .setValidFrom(new java.util.Date(100L))
                .setValidTo(new java.util.Date(200L));

        final PTTax tax1 = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax1);

        final PTTax tax2 = this.billy.taxes().persistence().create(builder2);
        Assertions.assertNotNull(tax2);
    }


    @Test
    public void testSameDatesDiferentCodesSuccess() {

        final PTRegionContext leiria = billy.contexts().continent().leiria();

        PTTax.Builder builder1 = createBuilder(leiria)
                .setCode("code1");

        PTTax.Builder builder2 = createBuilder(leiria)
                .setCode("code2");

        final PTTax tax1 = this.billy.taxes().persistence().create(builder1);
        Assertions.assertNotNull(tax1);

        final PTTax tax2 = this.billy.taxes().persistence().create(builder2);
        Assertions.assertNotNull(tax2);
    }


    private PTTax.Builder createBuilder(PTRegionContext madrid) {
        return this.getInstance(PTTax.Builder.class)
                .setContextUID(madrid.getUID())
                .setTaxRate(Tax.TaxRateType.PERCENTAGE, new BigDecimal("3.14"))
                .setCode("code1")
                .setDescription("description 1")
                .setValidFrom(new Date(0))
                .setValidTo(new Date(1000L))
                .setCurrency(Currency.getInstance("EUR"));
    }
}
