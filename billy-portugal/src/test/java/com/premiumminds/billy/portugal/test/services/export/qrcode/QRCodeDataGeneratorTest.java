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
package com.premiumminds.billy.portugal.test.services.export.qrcode;

import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeDataGenerator;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QRCodeDataGeneratorTest extends PTDocumentAbstractTest {

	private static final TYPE DEFAULT_TYPE = TYPE.FT;
	private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

	private PTInvoiceIssuingHandler handler;
	private UID issuedInvoiceUID;
	private QRCodeDataGenerator underTest;
	private DAOInvoiceSeries daoInvoiceSeries;

	@Before
	public void setUp() {
		this.handler = this.getInstance(PTInvoiceIssuingHandler.class);
		this.daoInvoiceSeries = this.getInstance(DAOInvoiceSeries.class);
		this.underTest = new QRCodeDataGenerator(this.daoInvoiceSeries);
	}

	@Test
	public void testGenerateQRCodeData() {
		generateInvoice(true);
		final PTInvoice document = this.getInstance(DAOPTInvoice.class).get(this.issuedInvoiceUID);

		String result = null;
		try {
			result = underTest.generateQRCodeData(document);
		} catch (RequiredFieldNotFoundException e) {
			Assert.fail();
		}

		Assert.assertNotNull(result);

	}

	@Test
	public void testGenerateQRCodeDataNoATCUD() {
		generateInvoice(false);
		final PTInvoice document = this.getInstance(DAOPTInvoice.class).get(this.issuedInvoiceUID);

		String result = null;
		try {
			result = underTest.generateQRCodeData(document);
		} catch (RequiredFieldNotFoundException e) {
			Assert.fail();
		}

		System.out.println(result);

		Assert.assertNotNull(result);

	}

	private void generateInvoice(boolean withATCUD){
		try {
			PTInvoiceEntity invoice = this.newInvoice(DEFAULT_TYPE,
													  SOURCE_BILLING);
			if(withATCUD) {
				InvoiceSeriesEntity entity = new JPAInvoiceSeriesEntity();
				entity.setBusiness(invoice.getBusiness());
				entity.setSeries(PTPersistencyAbstractTest.DEFAULT_SERIES);
				entity.setSeriesUniqueCode("ATCUD12345");
				daoInvoiceSeries.create(entity);
			}

			this.issueNewInvoice(this.handler, invoice, PTPersistencyAbstractTest.DEFAULT_SERIES);
			this.issuedInvoiceUID = invoice.getUID();
		} catch (DocumentIssuingException e) {
			Assert.fail(e.getMessage());
		}
	}
}