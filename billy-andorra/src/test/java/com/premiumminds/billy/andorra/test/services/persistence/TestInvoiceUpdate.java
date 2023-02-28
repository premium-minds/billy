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

import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;

public class TestInvoiceUpdate extends ADPersistenceServiceAbstractTest {

    private ADInvoice issuedInvoice;

    @BeforeEach
    public void setUp() throws DocumentIssuingException {
        final StringID<Business> businessUID = StringID.fromValue(UUID.randomUUID().toString());
        this.createSeries(businessUID);
        this.issuedInvoice = this.getNewIssuedInvoice(businessUID);
    }

    @Test
    public void testSimpleUpdate() {
        ADInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        ADInvoice peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assertions.assertEquals(false, peristedInvoice.isCancelled());

        builder.setCancelled(true);
        this.billy.invoices().persistence().update(builder);

        peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assertions.assertEquals(true, peristedInvoice.isCancelled());

    }

    @Test
    public void testBilledUpdate() {

        ADInvoice peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assertions.assertFalse(peristedInvoice.isBilled());

        ADInvoice.Builder builder = this.billy.invoices().builder(peristedInvoice);
        builder.setBilled(true);
        Assertions.assertThrows(BillyUpdateException.class, () -> builder.setBilled(false));
    }

    @Test
    public void testBusinessFailure() {
        ADInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        Assertions.assertThrows(
            BillyUpdateException.class,
            () -> builder.setBusinessUID(StringID.fromValue(UUID.randomUUID().toString())));
    }

    @Test
    public void testCustomerFailure() {
        ADInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        Assertions.assertThrows(
            BillyUpdateException.class,
            () -> builder.setCustomerUID(StringID.fromValue(UUID.randomUUID().toString())));
    }
}
