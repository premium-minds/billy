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
package com.premiumminds.billy.spain.test.services.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.export.pdf.creditreceipt.ESCreditReceiptPDFExportHandler;
import com.premiumminds.billy.spain.services.export.pdf.creditreceipt.ESCreditReceiptTemplateBundle;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESCreditReceiptTestUtil;
import com.premiumminds.billy.spain.util.Services;

public class TestESCreditReceiptPDFExportHandler extends ESPersistencyAbstractTest {

	public static final int		NUM_ENTRIES					= 10;
	public static final String	XSL_PATH					= "src/main/resources/templates/es_creditreceipt.xsl";
	public static final String	LOGO_PATH					= "src/main/resources/logoBig.png";

	@Test
	public void testPDFcreation() throws NoSuchAlgorithmException,
		ExportServiceException, URISyntaxException, DocumentIssuingException,
		IOException {

		File file = File.createTempFile("Result", ".pdf");

		InputStream xsl = new FileInputStream(
				TestESCreditReceiptPDFExportHandler.XSL_PATH);
		ESCreditReceiptTemplateBundle bundle = new ESCreditReceiptTemplateBundle(
				TestESCreditReceiptPDFExportHandler.LOGO_PATH, xsl);

		ESCreditReceiptPDFExportHandler handler = new ESCreditReceiptPDFExportHandler(
				ESAbstractTest.injector.getInstance(DAOESCreditReceipt.class));

		handler.toFile(
				file.toURI(),
				this.generateESCreditReceipt(PaymentMechanism.CASH, 
						this.getNewIssuedReceipt((new UID()).toString())), bundle);
	}

	private ESCreditReceiptEntity generateESCreditReceipt(
			PaymentMechanism paymentMechanism, ESReceiptEntity reference)
		throws DocumentIssuingException {

		Services services = new Services(ESAbstractTest.injector);

		ESIssuingParams params = this.getParameters("AC", "3000");

		ESCreditReceiptEntity creditReceipt = null;
		creditReceipt = (ESCreditReceiptEntity) services.issueDocument(
				new ESCreditReceiptTestUtil(ESAbstractTest.injector)
						.getCreditReceiptBuilder(reference), params);

		creditReceipt.setBusiness((BusinessEntity) reference.getBusiness());
		creditReceipt.setCreditOrDebit(CreditOrDebit.CREDIT);

		return creditReceipt;
	}
}
