/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.spain.test.services.documents.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.ESInvoiceIssuingHandler;
import com.premiumminds.billy.spain.services.documents.ESSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.spain.services.documents.exceptions.InvalidInvoiceTypeException;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice.SourceBilling;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice.TYPE;
import com.premiumminds.billy.spain.services.entities.ESInvoice;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.services.documents.ESDocumentAbstractTest;

public class TestESInvoiceIssuingHandler extends ESDocumentAbstractTest {

	private static final TYPE DEFAULT_TYPE = TYPE.FT;
	private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

	private ESInvoiceIssuingHandler handler;
	private UID issuedInvoiceUID;

	@Before
	public void setUpNewInvoice() {
		this.handler = this.getInstance(ESInvoiceIssuingHandler.class);

		try {
			ESInvoiceEntity invoice = this.newInvoice(
					TestESInvoiceIssuingHandler.DEFAULT_TYPE,
					TestESInvoiceIssuingHandler.SOURCE_BILLING);

			this.issueNewInvoice(this.handler, invoice,
					ESPersistencyAbstractTest.DEFAULT_SERIES);
			this.issuedInvoiceUID = invoice.getUID();
		} catch (DocumentIssuingException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testIssuedInvoiceSimple() throws DocumentIssuingException {
		ESInvoice issuedInvoice = (ESInvoice) this.getInstance(
				DAOESInvoice.class).get(this.issuedInvoiceUID);

		Assert.assertEquals(ESPersistencyAbstractTest.DEFAULT_SERIES,
				issuedInvoice.getSeries());
		Assert.assertTrue(1 == issuedInvoice.getSeriesNumber());
		String formatedNumber = TestESInvoiceIssuingHandler.DEFAULT_TYPE + " "
				+ ESPersistencyAbstractTest.DEFAULT_SERIES + "/1";
		Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());
		Assert.assertEquals(TestESInvoiceIssuingHandler.SOURCE_BILLING,
				issuedInvoice.getSourceBilling());
	}

	@Test
	public void testIssuedInvoiceSameSeries() throws DocumentIssuingException {
		ESInvoice issuedInvoice = (ESInvoice) this.getInstance(
				DAOESInvoice.class).get(this.issuedInvoiceUID);
		Integer nextNumber = 2;

		ESGenericInvoiceEntity newInvoice = this.newInvoice(
				TestESInvoiceIssuingHandler.DEFAULT_TYPE,
				TestESInvoiceIssuingHandler.SOURCE_BILLING);

		UID newInvoiceUID = newInvoice.getUID();
		newInvoice.setBusiness(issuedInvoice.getBusiness());

		this.issueNewInvoice(this.handler, newInvoice,
				ESPersistencyAbstractTest.DEFAULT_SERIES);

		ESInvoice lastInvoice = (ESInvoice) this
				.getInstance(DAOESInvoice.class).get(newInvoiceUID);

		Assert.assertEquals(ESPersistencyAbstractTest.DEFAULT_SERIES,
				lastInvoice.getSeries());
		Assert.assertEquals(nextNumber, lastInvoice.getSeriesNumber());
		String formatedNumber = TestESInvoiceIssuingHandler.DEFAULT_TYPE + " "
				+ ESPersistencyAbstractTest.DEFAULT_SERIES + "/" + nextNumber;
		Assert.assertEquals(formatedNumber, lastInvoice.getNumber());
	}

	@Test
	public void testIssuedInvoiceDifferentSeries()
			throws DocumentIssuingException {
		Integer nextNumber = 1;
		String newSeries = "NEW_SERIES";

		ESGenericInvoiceEntity newInvoice = this.newInvoice(
				TestESInvoiceIssuingHandler.DEFAULT_TYPE,
				TestESInvoiceIssuingHandler.SOURCE_BILLING);

		UID newInvoiceUID = newInvoice.getUID();

		this.issueNewInvoice(this.handler, newInvoice, newSeries);

		ESInvoice issuedInvoice = (ESInvoice) this.getInstance(
				DAOESInvoice.class).get(newInvoiceUID);

		Assert.assertEquals(newSeries, issuedInvoice.getSeries());
		Assert.assertEquals(nextNumber, issuedInvoice.getSeriesNumber());
		String formatedNumber = TestESInvoiceIssuingHandler.DEFAULT_TYPE + " "
				+ newSeries + "/" + nextNumber;
		Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());
	}

	/**
	 * Test issue of invoice of different type in same series
	 * 
	 * @throws DocumentIssuingException
	 */
	@Test(expected = InvalidInvoiceTypeException.class)
	public void testIssuedInvoiceFailure() throws DocumentIssuingException {
		String series = "NEW_SERIES";

		ESGenericInvoiceEntity invoice = this.newInvoice(
				TestESInvoiceIssuingHandler.DEFAULT_TYPE,
				TestESInvoiceIssuingHandler.SOURCE_BILLING);

		this.issueNewInvoice(this.handler, invoice, series);

		ESSimpleInvoiceIssuingHandler newHandler = this
				.getInstance(ESSimpleInvoiceIssuingHandler.class);

		ESGenericInvoiceEntity diffentTypeInvoice = this.newInvoice(TYPE.FS,
				TestESInvoiceIssuingHandler.SOURCE_BILLING);
		diffentTypeInvoice.setBusiness(invoice.getBusiness());

		this.issueNewInvoice(newHandler, diffentTypeInvoice, series);
	}

//	@Test(expected = InvalidInvoiceDateException.class)
//	public void testIssuedInvoiceBeforeDate() throws DocumentIssuingException {
//		this.issueNewInvoice(this.handler, this.newInvoice(
//				TestESInvoiceIssuingHandler.DEFAULT_TYPE,
//				TestESInvoiceIssuingHandler.SOURCE_BILLING),
//				ESPersistencyAbstractTest.DEFAULT_SERIES, new Date(0));
//	}

	@Test
	public void testIssuedInvoiceSameSourceBilling()
			throws DocumentIssuingException {
		ESGenericInvoiceEntity newInvoice = this.newInvoice(
				TestESInvoiceIssuingHandler.DEFAULT_TYPE,
				TestESInvoiceIssuingHandler.SOURCE_BILLING);

		UID newInvoiceUID = newInvoice.getUID();

		this.issueNewInvoice(this.handler, newInvoice,
				ESPersistencyAbstractTest.DEFAULT_SERIES);

		ESInvoice issuedInvoice = (ESInvoice) this.getInstance(
				DAOESInvoice.class).get(newInvoiceUID);

		Assert.assertEquals(TestESInvoiceIssuingHandler.SOURCE_BILLING,
				issuedInvoice.getSourceBilling());
	}

}
