/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.export;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNotePDFExportHandler;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNoteTemplateBundle;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTCreditNoteTestUtil;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;
import com.premiumminds.billy.portugal.util.PaymentMechanism;

public class TestPTCreditNotePDFExportHandler extends PTPersistencyAbstractTest{
	public static final int NUM_ENTRIES = 10; 
	public static final String XSL_PATH = "src/main/resources/pt_creditnote.xsl";
	public static final String PDF_PATH = "src/main/resources/Result.pdf";
	public static final String LOGO_PATH = "src/main/resources/logoBig.png";

	public static final String BUSINESS_EMAIL = "testmail@premium-minds.com";
	public static final String BUSINESS_PHONE = "21 123 1234";
	public static final String BUSINESS_FAX = "21 321 4321";
	public static final String CUSTOMER_NUMBER = "123456";
	public static final String SOFTWARE_CERTIFICATE_NUMBER = "4321";
	public static final byte[] SAMPLE_HASH = {0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf};
	
	@Test
	public void testPDFcreation() throws NoSuchAlgorithmException, ExportServiceException, FileNotFoundException {
		InputStream xsl = new FileInputStream(XSL_PATH);
		
		PTCreditNoteTemplateBundle bundle = 
				new PTCreditNoteTemplateBundle(LOGO_PATH, xsl, PDF_PATH, BUSINESS_EMAIL, BUSINESS_PHONE, BUSINESS_FAX, SOFTWARE_CERTIFICATE_NUMBER);
		PTCreditNotePDFExportHandler handler = new PTCreditNotePDFExportHandler();
		handler.toFile(generatePTCreditNote(PaymentMechanism.CASH), bundle);
	}

	private PTCreditNoteEntity generatePTCreditNote(PaymentMechanism paymentMechanism) {
		PTInvoiceTestUtil invoiceUtil = new PTInvoiceTestUtil(injector);
		PTInvoiceEntity invoice = invoiceUtil.getInvoiceEntity();
		
		DAOPTInvoice dao = injector.getInstance(DAOPTInvoice.class);
		dao.create(invoice);
		
		PTCreditNoteTestUtil creditNoteUtil = new PTCreditNoteTestUtil(injector);
		PTCreditNoteEntity creditNote = creditNoteUtil.getCreditNoteEntity();
		creditNote.setCustomer((CustomerEntity)invoice.getCustomer());
		creditNote.setBusiness((BusinessEntity)invoice.getBusiness());
		creditNote.setCreditOrDebit(CreditOrDebit.CREDIT);
		creditNote.setHash("mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
		
		return creditNote;
	}
}
