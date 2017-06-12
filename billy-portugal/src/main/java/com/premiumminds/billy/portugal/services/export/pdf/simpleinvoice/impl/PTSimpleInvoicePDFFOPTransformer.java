/**
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
package com.premiumminds.billy.portugal.services.export.pdf.simpleinvoice.impl;

import java.io.InputStream;
import java.math.MathContext;
import java.text.SimpleDateFormat;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.services.export.PTSimpleInvoiceData;
import com.premiumminds.billy.portugal.services.export.pdf.PTAbstractFOPPDFTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.PTSimpleInvoicePDFTransformer;

public class PTSimpleInvoicePDFFOPTransformer extends PTAbstractFOPPDFTransformer<PTSimpleInvoiceData> 
implements PTSimpleInvoicePDFTransformer {

	public PTSimpleInvoicePDFFOPTransformer(
			MathContext mathContext,
			String logoImagePath,
			InputStream xsltFileStream, 
			Config config,
			String softwareCertificationId) {
		
		super(PTSimpleInvoiceData.class, mathContext, logoImagePath, xsltFileStream, config, softwareCertificationId);

	}

	public PTSimpleInvoicePDFFOPTransformer(
			String logoImagePath,
			InputStream xsltFileStream, 
			String softwareCertificationId) {
		
		this(BillyMathContext.get(), logoImagePath, xsltFileStream, new Config(), softwareCertificationId);
	}
	
	@Override
	protected ParamsTree<String, String> mapDocumentToParamsTree( PTSimpleInvoiceData invoice) {

		ParamsTree<String, String> params = super.mapDocumentToParamsTree(invoice);

		params.getRoot().addChild(PTParamKeys.INVOICE_HASH, getVerificationHashString(invoice.getHash().getBytes()));
		params.getRoot().addChild(PTParamKeys.SOFTWARE_CERTIFICATE_NUMBER, getSoftwareCertificationId());
		
		return params;
	}
	
	@Override
	protected void setHeader(ParamsTree<String, String> params, PTSimpleInvoiceData entity) {
		final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

		params.getRoot().addChild(ParamKeys.ID, entity.getNumber());

		if (null != entity.getPayments()) {
			for(PaymentData p : entity.getPayments()) {
			params.getRoot().addChild(
					ParamKeys.INVOICE_PAYMETHOD,
					getPaymentMechanismTranslation(p.getPaymentMethod()));
			}
		}

		params.getRoot().addChild(ParamKeys.EMISSION_DATE,
				date.format(entity.getDate()));
	}
	
	@Override
	protected void setCustomer(ParamsTree<String, String> params, PTSimpleInvoiceData entity) {

		Node<String, String> customer = params.getRoot().addChild(
				ParamKeys.CUSTOMER);

		customer.addChild(ParamKeys.CUSTOMER_FINANCIAL_ID, getCustomerFinancialId(entity));
	}
	
	@Override
	protected void setTaxDetails(TaxTotals taxTotals, ParamsTree<String, String> params) {
		//Do Nothing
	}

}
