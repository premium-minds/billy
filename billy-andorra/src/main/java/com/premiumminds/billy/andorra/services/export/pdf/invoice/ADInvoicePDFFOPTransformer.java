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
package com.premiumminds.billy.andorra.services.export.pdf.invoice;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.andorra.services.export.ADInvoiceData;
import com.premiumminds.billy.andorra.services.export.pdf.ADAbstractFOPPDFTransformer;
import com.premiumminds.billy.andorra.services.export.pdf.ADInvoicePDFTransformer;

public class ADInvoicePDFFOPTransformer extends ADAbstractFOPPDFTransformer<ADInvoiceData>
        implements ADInvoicePDFTransformer
{

    public static final String PARAM_KEYS_ROOT = "invoice";
    public static final String PARAM_KEYS_INVOICE_PAYSETTLEMENT = "paymentSettlement";

    public ADInvoicePDFFOPTransformer(MathContext mathContext, String logoImagePath, InputStream xsltFileStream) {

        super(ADInvoiceData.class, mathContext, logoImagePath, xsltFileStream);
    }

    public ADInvoicePDFFOPTransformer(String logoImagePath, InputStream xsltFileStream) {

        this(BillyMathContext.get(), logoImagePath, xsltFileStream);
    }

    public ADInvoicePDFFOPTransformer(ADInvoiceTemplateBundle bundle) {
        super(ADInvoiceData.class, BillyMathContext.get(), bundle);
    }

    @Override
    protected ParamsTree<String, String> getNewParamsTree() {
        return new ParamsTree<>(ADInvoicePDFFOPTransformer.PARAM_KEYS_ROOT);
    }

    @Override
    protected void setHeader(ParamsTree<String, String> params, ADInvoiceData entity) {
        if (null != entity.getSettlementDescription()) {
            params.getRoot().addChild(
                ADInvoicePDFFOPTransformer.PARAM_KEYS_INVOICE_PAYSETTLEMENT,
                entity.getSettlementDescription());
        }
        super.setHeader(params, entity);
    }

    @Override
    protected String getCustomerFinancialId(ADInvoiceData invoice) {
        return invoice.getCustomer().getTaxRegistrationNumber();
    }

}
