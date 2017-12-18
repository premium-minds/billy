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
package com.premiumminds.billy.france.services.export.pdf.invoice;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.france.services.export.FRInvoiceData;
import com.premiumminds.billy.france.services.export.pdf.FRAbstractFOPPDFTransformer;
import com.premiumminds.billy.france.services.export.pdf.FRInvoicePDFTransformer;

public class FRInvoicePDFFOPTransformer extends FRAbstractFOPPDFTransformer<FRInvoiceData>
        implements FRInvoicePDFTransformer {

    public static final String PARAM_KEYS_ROOT = "invoice";
    public static final String PARAM_KEYS_INVOICE_PAYSETTLEMENT = "paymentSettlement";

    public FRInvoicePDFFOPTransformer(MathContext mathContext, String logoImagePath, InputStream xsltFileStream) {

        super(FRInvoiceData.class, mathContext, logoImagePath, xsltFileStream);
    }

    public FRInvoicePDFFOPTransformer(String logoImagePath, InputStream xsltFileStream) {

        this(BillyMathContext.get(), logoImagePath, xsltFileStream);
    }

    public FRInvoicePDFFOPTransformer(FRInvoiceTemplateBundle bundle) {
        super(FRInvoiceData.class, BillyMathContext.get(), bundle);
    }

    @Override
    protected ParamsTree<String, String> getNewParamsTree() {
        return new ParamsTree<>(FRInvoicePDFFOPTransformer.PARAM_KEYS_ROOT);
    }

    @Override
    protected void setHeader(ParamsTree<String, String> params, FRInvoiceData entity) {
        if (null != entity.getSettlementDescription()) {
            params.getRoot().addChild(FRInvoicePDFFOPTransformer.PARAM_KEYS_INVOICE_PAYSETTLEMENT,
                    entity.getSettlementDescription());
        }
        super.setHeader(params, entity);
    }

    @Override
    protected String getCustomerFinancialId(FRInvoiceData invoice) {
        return invoice.getCustomer().getTaxRegistrationNumber();
    }

}
