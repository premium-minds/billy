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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.france.services.entities.FRInvoice;

public class TestInvoiceUpdate extends FRPersistenceServiceAbstractTest {

    private FRInvoice issuedInvoice;

    @BeforeEach
    public void setUp() throws DocumentIssuingException {
        this.issuedInvoice = this.getNewIssuedInvoice();
    }

    @Test
    public void testSimpleUpdate() {
        FRInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        FRInvoice peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assertions.assertEquals(false, peristedInvoice.isCancelled());

        builder.setCancelled(true);
        this.billy.invoices().persistence().update(builder);

        peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assertions.assertEquals(true, peristedInvoice.isCancelled());

    }

    @Test
    public void testBilledUpdate() {
        FRInvoice peristedInvoice = this.billy.invoices().persistence().get(this.issuedInvoice.getUID());
        Assertions.assertEquals(false, peristedInvoice.isBilled());

        FRInvoice.Builder builder = this.billy.invoices().builder(peristedInvoice);
        builder.setBilled(true);
        Assertions.assertThrows(BillyUpdateException.class, () -> builder.setBilled(false));
    }

    @Test
    public void testBusinessFailure() {
        FRInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        Assertions.assertThrows(BillyUpdateException.class, () ->  builder.setBusinessUID(new UID()));
    }

    @Test
    public void testCustomerFailure() {
        FRInvoice.Builder builder = this.billy.invoices().builder(this.issuedInvoice);

        Assertions.assertThrows(BillyUpdateException.class, () -> builder.setCustomerUID(new UID()));
    }
}
