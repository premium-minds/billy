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
package com.premiumminds.billy.spain.services.export.pdf.receipt;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.spain.services.export.ESReceiptData;
import com.premiumminds.billy.spain.services.export.pdf.ESAbstractFOPPDFTransformer;
import com.premiumminds.billy.spain.services.export.pdf.ESReceiptPDFTransformer;

public class ESReceiptPDFFOPTransformer extends ESAbstractFOPPDFTransformer<ESReceiptData> 
implements ESReceiptPDFTransformer {
	
	public static final String PARAM_KEYS_ROOT = "receipt";
	
	public ESReceiptPDFFOPTransformer(
			MathContext mathContext,
			String logoImagePath,
			InputStream xsltFileStream) {
		
		super(ESReceiptData.class, mathContext, logoImagePath, xsltFileStream);
	}
	
	public ESReceiptPDFFOPTransformer(
			String logoImagePath,
			InputStream xsltFileStream) {
		
		this(BillyMathContext.get(), logoImagePath, xsltFileStream);
	}
	
	public ESReceiptPDFFOPTransformer(ESReceiptTemplateBundle bundle) {
        super(ESReceiptData.class, BillyMathContext.get(), bundle);
    }
	
	@Override
	protected ParamsTree<String, String> getNewParamsTree() {
		return new ParamsTree<String, String>(PARAM_KEYS_ROOT);
	}
	
	@Override
	protected ParamsTree<String, String> mapDocumentToParamsTree(ESReceiptData entity) {

		ParamsTree<String, String> params = getNewParamsTree();
		TaxTotals taxTotals = new TaxTotals();

		setHeader(params, entity);
		setBusiness(params, entity);
		setEntries(taxTotals, params, entity);
		setTaxDetails(taxTotals, params);
		setTaxValues(params, entity);
		
		return params;
	}
	
	@Override
	protected String getCustomerFinancialId(ESReceiptData entity) {
		return "";
	}
}
