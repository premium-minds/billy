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
package com.premiumminds.billy.portugal.services.export.pdf.invoice.impl;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.services.export.PTInvoiceData;
import com.premiumminds.billy.portugal.services.export.pdf.PTAbstractFOPPDFTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.PTInvoicePDFTransformer;

public class PTInvoicePDFFOPTransformer extends PTAbstractFOPPDFTransformer<PTInvoiceData> 
implements PTInvoicePDFTransformer {

	public PTInvoicePDFFOPTransformer(
			MathContext mathContext,
			String logoImagePath,
			InputStream xsltFileStream, 
			Config config,
			String softwareCertificationId) {
		
		super(PTInvoiceData.class, mathContext, logoImagePath, xsltFileStream, config, softwareCertificationId);

	}

	public PTInvoicePDFFOPTransformer(
			String logoImagePath,
			InputStream xsltFileStream, 
			String softwareCertificationId) {
		
		this(BillyMathContext.get(), logoImagePath, xsltFileStream, new Config(), softwareCertificationId);
	}
	
	@Override
	protected ParamsTree<String, String> mapDocumentToParamsTree(PTInvoiceData entity) {

		ParamsTree<String, String> params = super.mapDocumentToParamsTree(entity);

		params.getRoot().addChild(PTParamKeys.INVOICE_HASH, getVerificationHashString(entity.getHash().getBytes()));
		params.getRoot().addChild(PTParamKeys.SOFTWARE_CERTIFICATE_NUMBER, getSoftwareCertificationId());
		
		return params;
	}
	
	@Override
	protected void setHeader(ParamsTree<String, String> params, PTInvoiceData entity) {
		if (null != entity.getSettlementDescription()) {
			params.getRoot().addChild(PTParamKeys.INVOICE_PAYSETTLEMENT,
					entity.getSettlementDescription());

		}
		super.setHeader(params, entity);
	}

}
