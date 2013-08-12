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
package com.premiumminds.billy.portugal.test.services.documents.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidSourceBillingException;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;

public class TestPTManualInvoiceIssuingHandler extends PTDocumentAbstractTest {

	private static final TYPE DEFAULT_TYPE = TYPE.FT;
	private static final SourceBilling SOURCE_BILLING = SourceBilling.M;

	private PTInvoiceIssuingHandler handler;

	@Before
	public void setUpNewManualInvoice() {
		this.handler = this.getInstance(PTInvoiceIssuingHandler.class);

		try {
			PTInvoiceEntity invoice = this.newInvoice(
					TestPTManualInvoiceIssuingHandler.DEFAULT_TYPE,
					PTDocumentAbstractTest.INVOICE_UID,
					PTDocumentAbstractTest.PRODUCT_UID,
					PTDocumentAbstractTest.BUSINESS_UID,
					PTDocumentAbstractTest.CUSTOMER_UID,
					TestPTManualInvoiceIssuingHandler.SOURCE_BILLING);

			this.issueNewInvoice(this.handler, invoice,
					PTDocumentAbstractTest.DEFAULT_SERIES);
		} catch (DocumentIssuingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIssuedManualInvoiceSimple() throws DocumentIssuingException {
		PTInvoiceEntity issuedInvoice = (PTInvoiceEntity) this.getInstance(
				DAOPTInvoice.class).get(
				new UID(PTDocumentAbstractTest.INVOICE_UID));

		Assert.assertEquals(PTDocumentAbstractTest.DEFAULT_SERIES,
				issuedInvoice.getSeries());
		Assert.assertTrue(1 == issuedInvoice.getSeriesNumber());
		String formatedNumber = TestPTManualInvoiceIssuingHandler.DEFAULT_TYPE
				+ " " + PTDocumentAbstractTest.DEFAULT_SERIES + "/1";
		Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());
		Assert.assertEquals(TestPTManualInvoiceIssuingHandler.SOURCE_BILLING,
				issuedInvoice.getSourceBilling());
	}

	/**
	 * Test the issue of a normal invoice in a manual series.
	 * 
	 * @throws DocumentIssuingException
	 */
	@Test(expected = InvalidSourceBillingException.class)
	public void testDifferentBilling() throws DocumentIssuingException {
		String UID1 = "invoice_uid_1";
		String PUID1 = "product_uid_1";
		String BUID1 = "business_uid_1";
		String CUID1 = "customer_uid_1";

		PTInvoiceEntity normalInvoice = this.newInvoice(
				TestPTManualInvoiceIssuingHandler.DEFAULT_TYPE, UID1, PUID1,
				BUID1, CUID1, SourceBilling.P);

		this.issueNewInvoice(this.handler, normalInvoice,
				PTDocumentAbstractTest.DEFAULT_SERIES);
	}
}
