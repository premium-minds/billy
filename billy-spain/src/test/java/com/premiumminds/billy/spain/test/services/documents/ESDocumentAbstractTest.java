/**
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.spain.test.services.documents;

import java.util.Date;

import org.junit.Before;

import com.premiumminds.billy.core.exceptions.NotImplementedException;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParamsImpl;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESInvoiceTestUtil;
import com.premiumminds.billy.spain.test.util.ESReceiptTestUtil;
import com.premiumminds.billy.spain.test.util.ESSimpleInvoiceTestUtil;

public class ESDocumentAbstractTest extends ESPersistencyAbstractTest {

	protected ESIssuingParams parameters;
	
	public enum INVOICE_TYPE {
		FT, // Invoice
		RC, // Receipt
		FS, // Simple Invoice
		NC	// Credit Note 
	}
	
	public enum SOURCE_BILLING {
		APPLICATION
		, MANUAL
	}

	@Before
	public void setUpParamenters() {
		this.parameters = new ESIssuingParamsImpl();
		this.parameters.setEACCode("31400");
	}
	
	protected <T extends ESGenericInvoiceEntity> T newInvoice(INVOICE_TYPE type) {
		return newInvoice(type, SOURCE_BILLING.APPLICATION);
	}

	@SuppressWarnings("unchecked")
	protected <T extends ESGenericInvoiceEntity> T newInvoice(INVOICE_TYPE type, SOURCE_BILLING source) {

		switch (type) {
			case FT:
				return (T) new ESInvoiceTestUtil(ESAbstractTest.injector)
						.getInvoiceEntity(source);
			case RC:
				return (T) new ESReceiptTestUtil(ESAbstractTest.injector)
				.getReceiptEntity();
			case FS:
				return (T) new ESSimpleInvoiceTestUtil(ESAbstractTest.injector)
						.getSimpleInvoiceEntity();
			case NC:
				throw new NotImplementedException();
			default:
				return null;
		}
	}

	protected <T extends DocumentIssuingHandler, I extends ESGenericInvoiceEntity> void issueNewInvoice(
			T handler, I invoice, String series)
			throws DocumentIssuingException {
		DAOESInvoice dao = this.getInstance(DAOESInvoice.class);
		try {
			dao.beginTransaction();
			invoice.initializeEntityDates();
			this.issueNewInvoice(handler, invoice, series, new Date(invoice
					.getCreateTimestamp().getTime() + 100));
			dao.commit();
		} catch (DocumentIssuingException up) {
			dao.rollback();
			throw up;
		}
	}

	protected <T extends DocumentIssuingHandler, I extends ESGenericInvoiceEntity> void issueNewInvoice(
			T handler, I invoice, String series, Date date)
			throws DocumentIssuingException {
		this.parameters.setInvoiceSeries(series);
		invoice.setDate(date);
		handler.issue(invoice, this.parameters);
	}

}
