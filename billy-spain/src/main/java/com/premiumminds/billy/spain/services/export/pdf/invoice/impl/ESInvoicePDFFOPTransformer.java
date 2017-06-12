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
package com.premiumminds.billy.spain.services.export.pdf.invoice.impl;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.spain.services.export.ESInvoiceData;
import com.premiumminds.billy.spain.services.export.pdf.ESAbstractFOPPDFTransformer;
import com.premiumminds.billy.spain.services.export.pdf.ESInvoicePDFTransformer;

public class ESInvoicePDFFOPTransformer extends ESAbstractFOPPDFTransformer<ESInvoiceData> 
implements ESInvoicePDFTransformer {
	
	public static final String PARAM_KEYS_ROOT = "invoice";
	public static final String PARAM_KEYS_INVOICE_PAYSETTLEMENT = "paymentSettlement";
	
	public ESInvoicePDFFOPTransformer(
			MathContext mathContext,
			String logoImagePath,
			InputStream xsltFileStream) {
		
		super(ESInvoiceData.class, mathContext, logoImagePath, xsltFileStream);
	}

	public ESInvoicePDFFOPTransformer(
			String logoImagePath,
			InputStream xsltFileStream) {
		
		this(BillyMathContext.get(), logoImagePath, xsltFileStream);
	}
	
	@Override
	protected ParamsTree<String, String> getNewParamsTree() {
		return new ParamsTree<String, String>(PARAM_KEYS_ROOT);
	}
	
	@Override
	protected void setHeader(ParamsTree<String, String> params, ESInvoiceData entity) {
		if (null != entity.getSettlementDescription()) {
			params.getRoot().addChild(PARAM_KEYS_INVOICE_PAYSETTLEMENT,
					entity.getSettlementDescription());
		}
		super.setHeader(params, entity);
	}

	@Override
	protected String getCustomerFinancialId(ESInvoiceData invoice) {
		return invoice.getCustomer().getTaxRegistrationNumber();
	}

}
