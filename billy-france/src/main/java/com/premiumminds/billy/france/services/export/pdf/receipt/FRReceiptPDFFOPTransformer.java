/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.services.export.pdf.receipt;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.france.services.export.FRReceiptData;
import com.premiumminds.billy.france.services.export.pdf.FRAbstractFOPPDFTransformer;
import com.premiumminds.billy.france.services.export.pdf.FRReceiptPDFTransformer;

public class FRReceiptPDFFOPTransformer extends FRAbstractFOPPDFTransformer<FRReceiptData>
        implements FRReceiptPDFTransformer {

    public static final String PARAM_KEYS_ROOT = "receipt";

    public FRReceiptPDFFOPTransformer(MathContext mathContext, String logoImagePath, InputStream xsltFileStream) {

        super(FRReceiptData.class, mathContext, logoImagePath, xsltFileStream);
    }

    public FRReceiptPDFFOPTransformer(String logoImagePath, InputStream xsltFileStream) {

        this(BillyMathContext.get(), logoImagePath, xsltFileStream);
    }

    public FRReceiptPDFFOPTransformer(FRReceiptTemplateBundle bundle) {
        super(FRReceiptData.class, BillyMathContext.get(), bundle);
    }

    @Override
    protected ParamsTree<String, String> getNewParamsTree() {
        return new ParamsTree<>(FRReceiptPDFFOPTransformer.PARAM_KEYS_ROOT);
    }

    @Override
    protected ParamsTree<String, String> mapDocumentToParamsTree(FRReceiptData entity) {

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
    protected String getCustomerFinancialId(FRReceiptData entity) {
        return "";
    }
}
