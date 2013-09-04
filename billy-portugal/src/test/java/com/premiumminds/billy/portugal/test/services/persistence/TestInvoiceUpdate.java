/**
 * Copyright (C) 2013 Premium Minds.
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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

public class TestInvoiceUpdate extends PTPersistenceServiceAbstractTest {

	private PTInvoice	issuedInvoice;

	@Before
	public void setUp() throws DocumentIssuingException {
		this.issuedInvoice = getNewIssuedInvoice();
	}

	@Test
	public void testSimpleUpdate() {
		PTInvoice.Builder builder = billy.invoices().builder(issuedInvoice);

		PTInvoice peristedInvoice = billy.invoices().persistence().get(issuedInvoice.getUID());
		assertEquals(false, peristedInvoice.isCancelled());

		builder.setCancelled(true);
		billy.invoices().persistence().update(builder);

		peristedInvoice = billy.invoices().persistence().get(issuedInvoice.getUID());
		assertEquals(true, peristedInvoice.isCancelled());

	}

	@Test(expected = BillyUpdateException.class)
	public void testBilledUpdate() {
		PTInvoice.Builder builder = billy.invoices().builder(issuedInvoice);

		PTInvoice peristedInvoice = billy.invoices().persistence().get(issuedInvoice.getUID());	
		assertEquals(false, peristedInvoice.isBilled());

		builder = billy.invoices().builder(peristedInvoice);
		builder.setBilled(true);
		builder.setBilled(false);
	}

	@Test(expected = BillyUpdateException.class)
	public void testBusinessFailure() {
		PTInvoice.Builder builder = billy.invoices().builder(issuedInvoice);

		builder.setBusinessUID(new UID());
	}

	@Test(expected = BillyUpdateException.class)
	public void testCustomerFailure() {
		PTInvoice.Builder builder = billy.invoices().builder(issuedInvoice);

		builder.setCustomerUID(new UID());
	}

	@Test(expected = BillyUpdateException.class)
	public void testSourceBillingFailure() {
		PTInvoice.Builder builder = billy.invoices().builder(issuedInvoice);

		builder.setSourceBilling(SourceBilling.M);
	}
}
