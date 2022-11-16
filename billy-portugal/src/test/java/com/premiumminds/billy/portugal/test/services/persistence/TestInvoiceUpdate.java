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

import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInvoiceUpdate extends PTPersistenceServiceAbstractTest {

    private PTInvoice issuedInvoice;

    @BeforeEach
    public void setUp() throws DocumentIssuingException {
        final String uid = StringID.fromValue(UUID.randomUUID().toString()).toString();
        this.createSeries(uid);
        this.issuedInvoice = this.getNewIssuedInvoice(uid);
    }

    @Test
    public void testSimpleUpdate() {
        PTInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        PTInvoice peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assertions.assertEquals(false, peristedInvoice.isCancelled());

        builder.setCancelled(true);
        this.billy.invoices().persistence().update(builder);

        peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assertions.assertEquals(true, peristedInvoice.isCancelled());

    }

    @Test
    public void testBilledUpdate() {

        PTInvoice peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assertions.assertEquals(false, peristedInvoice.isBilled());

        PTInvoice.Builder builder = this.billy.invoices().builder(peristedInvoice);
        builder.setBilled(true);
        Assertions.assertThrows(BillyUpdateException.class, () -> builder.setBilled(false));
    }

    @Test
    public void testBusinessFailure() {
        PTInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        Assertions.assertThrows(BillyUpdateException.class, () ->  builder.setBusinessUID(StringID.fromValue(UUID.randomUUID().toString())));
    }

    @Test
    public void testCustomerFailure() {
        PTInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        Assertions.assertThrows(BillyUpdateException.class, () -> builder.setCustomerUID(StringID.fromValue(UUID.randomUUID().toString())));
    }

    @Test
    public void testSourceBillingFailure() {
        PTInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        Assertions.assertThrows(BillyUpdateException.class, () ->  builder.setSourceBilling(SourceBilling.M));
    }
}
