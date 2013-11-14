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

import java.util.Date;

import org.junit.Before;

import com.premiumminds.billy.core.exceptions.NotImplementedException;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParamsImpl;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;
import com.premiumminds.billy.portugal.test.util.PTReceiptInvoiceTestUtil;
import com.premiumminds.billy.portugal.test.util.PTSimpleInvoiceTestUtil;
import com.premiumminds.billy.portugal.util.KeyGenerator;

public class PTDocumentAbstractTest extends PTPersistencyAbstractTest {

	protected PTIssuingParams parameters;

	@Before
	public void setUpParamenters() {
		KeyGenerator generator = new KeyGenerator(
				PTPersistencyAbstractTest.PRIVATE_KEY_DIR);

		this.parameters = new PTIssuingParamsImpl();
		this.parameters.setPrivateKey(generator.getPrivateKey());
		this.parameters.setPublicKey(generator.getPublicKey());
		this.parameters.setPrivateKeyVersion("1");
		this.parameters.setEACCode("31400");
	}

	@SuppressWarnings("unchecked")
	protected <T extends PTGenericInvoiceEntity> T newInvoice(TYPE type,
			SourceBilling billing) {

		switch (type) {
			case FT:
				return (T) new PTInvoiceTestUtil(PTAbstractTest.injector)
						.getInvoiceEntity(billing);
			case FS:
				return (T) new PTSimpleInvoiceTestUtil(PTAbstractTest.injector)
						.getSimpleInvoiceEntity(billing);
			case FR:
				return (T) new PTReceiptInvoiceTestUtil(PTAbstractTest.injector)
						.getReceiptInvoiceEntity(billing);
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
		DAOPTInvoice dao = this.getInstance(DAOPTInvoice.class);
		dao.beginTransaction();
		try {
			invoice.initializeEntityDates();
			this.issueNewInvoice(handler, invoice, series, new Date(invoice
					.getCreateTimestamp().getTime() + 100));
			dao.commit();
		} catch (DocumentIssuingException up) {
			dao.rollback();
			throw up;
		}
	}

	protected <T extends DocumentIssuingHandler, I extends PTGenericInvoiceEntity> void issueNewInvoice(
			T handler, I invoice, String series, Date date)
			throws DocumentIssuingException {
		this.parameters.setInvoiceSeries(series);
		invoice.setDate(date);
		handler.issue(invoice, this.parameters);
	}

}
