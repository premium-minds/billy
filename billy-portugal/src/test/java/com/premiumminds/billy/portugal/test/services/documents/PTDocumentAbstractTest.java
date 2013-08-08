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
package com.premiumminds.billy.portugal.test.services.documents;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;

import com.premiumminds.billy.core.exceptions.NotImplementedException;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParamsImpl;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;
import com.premiumminds.billy.portugal.test.util.PTSimpleInvoiceTestUtil;
import com.premiumminds.billy.portugal.util.KeyGenerator;

public class PTDocumentAbstractTest extends PTPersistencyAbstractTest {

	protected static final String PRIVATE_KEY_DIR = "src/test/resources/keys/private.pem";
	protected static final String PRODUCT_UID = "PRODUCT_ISSUE_UID";
	protected static final String BUSINESS_UID = "BUSINESS_UID_TEST";
	protected static final String CUSTOMER_UID = "CUSTOMER_UID_TEST";
	protected static final String INVOICE_UID = "INVOICE_ISSUE_UID";
	protected static final String DEFAULT_SERIES = "DEFAULT";
	protected static final String ENTRY_UID = "ENTRY_ISSUE_UID";

	protected PTIssuingParams parameters;

	@Before
	public void setUpParamenters() {
		KeyGenerator generator = new KeyGenerator(PRIVATE_KEY_DIR);

		parameters = new PTIssuingParamsImpl();
		parameters.setPrivateKey(generator.getPrivateKey());
		parameters.setPublicKey(generator.getPublicKey());

	}

	@SuppressWarnings("unchecked")
	protected <T extends PTGenericInvoiceEntity> T newInvoice(TYPE type,
			String invoiceUID, String productUID, String businessUID,
			String customerUID, SourceBilling billing) {

		switch (type) {
			case FT:
				return (T) new PTInvoiceTestUtil(injector)
						.getSimpleInvoiceEntity(type, ENTRY_UID, invoiceUID,
								businessUID, customerUID,
								Arrays.asList(productUID), billing);
			case FS:
				return (T) new PTSimpleInvoiceTestUtil(injector)
						.getSimpleInvoiceEntity(type, ENTRY_UID, invoiceUID,
								businessUID, customerUID,
								Arrays.asList(productUID), billing);
			case NC:
				throw new NotImplementedException();
			case ND:
				throw new NotImplementedException();
			default:
				return null;
		}
	}

	protected <T extends DocumentIssuingHandler, I extends PTGenericInvoiceEntity> void issueNewInvoice(
			T handler, I invoice, String series)
			throws DocumentIssuingException {
		issueNewInvoice(handler, invoice, series, new Date(invoice
				.getCreateTimestamp().getTime() + 100));
	}

	protected <T extends DocumentIssuingHandler, I extends PTGenericInvoiceEntity> void issueNewInvoice(
			T handler, I invoice, String series, Date date)
			throws DocumentIssuingException {
		parameters.setInvoiceSeries(series);
		invoice.setDate(date);
		handler.issue(invoice, parameters);
	}

}
