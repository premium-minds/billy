/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

public class TestInvoiceUpdate extends PTPersistenceServiceAbstractTest {

    /*private PTInvoice issuedInvoice;

    @Before
    public void setUp() throws DocumentIssuingException {
        this.issuedInvoice = this.getNewIssuedInvoice();
    }

    @Test
    public void testSimpleUpdate() {
        PTInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        PTInvoice peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assert.assertEquals(false, peristedInvoice.isCancelled());

        builder.setCancelled(true);
        this.billy.invoices().persistence().update(builder);

        peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assert.assertEquals(true, peristedInvoice.isCancelled());

    }

    @Test(expected = BillyUpdateException.class)
    public void testBilledUpdate() {
        PTInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        PTInvoice peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assert.assertEquals(false, peristedInvoice.isBilled());

        builder = this.billy.invoices().builder(peristedInvoice);
        builder.setBilled(true);
        builder.setBilled(false);
    }

    @Test(expected = BillyUpdateException.class)
    public void testBusinessFailure() {
        PTInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        builder.setBusinessUID(new UID());
    }

    @Test(expected = BillyUpdateException.class)
    public void testCustomerFailure() {
        PTInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        builder.setCustomerUID(new UID());
    }

    @Test(expected = BillyUpdateException.class)
    public void testSourceBillingFailure() {
        PTInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        builder.setSourceBilling(SourceBilling.M);
    }*/
}
