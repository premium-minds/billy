/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.services.export.pdf.receipt;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.andorra.services.export.ADReceiptData;
import com.premiumminds.billy.andorra.services.export.pdf.ADAbstractFOPPDFTransformer;
import com.premiumminds.billy.andorra.services.export.pdf.ADReceiptPDFTransformer;

public class ADReceiptPDFFOPTransformer extends ADAbstractFOPPDFTransformer<ADReceiptData>
        implements ADReceiptPDFTransformer
{

    public static final String PARAM_KEYS_ROOT = "receipt";

    public ADReceiptPDFFOPTransformer(MathContext mathContext, String logoImagePath, InputStream xsltFileStream) {

        super(ADReceiptData.class, mathContext, logoImagePath, xsltFileStream);
    }

    public ADReceiptPDFFOPTransformer(String logoImagePath, InputStream xsltFileStream) {

        this(BillyMathContext.get(), logoImagePath, xsltFileStream);
    }

    public ADReceiptPDFFOPTransformer(ADReceiptTemplateBundle bundle) {
        super(ADReceiptData.class, BillyMathContext.get(), bundle);
    }

    @Override
    protected ParamsTree<String, String> getNewParamsTree() {
        return new ParamsTree<>(ADReceiptPDFFOPTransformer.PARAM_KEYS_ROOT);
    }

    @Override
    protected ParamsTree<String, String> mapDocumentToParamsTree(ADReceiptData entity) {

        ParamsTree<String, String> params = this.getNewParamsTree();
        TaxTotals taxTotals = new TaxTotals();

        this.setHeader(params, entity);
        this.setBusiness(params, entity);
        this.setEntries(taxTotals, params, entity);
        this.setTaxDetails(taxTotals, params);
        this.setTaxValues(params, entity);

        return params;
    }

    @Override
    protected String getCustomerFinancialId(ADReceiptData entity) {
        return "";
    }
}
