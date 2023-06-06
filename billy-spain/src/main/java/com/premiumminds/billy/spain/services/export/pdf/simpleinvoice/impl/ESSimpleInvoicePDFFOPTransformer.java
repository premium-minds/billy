/*
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
package com.premiumminds.billy.spain.services.export.pdf.simpleinvoice.impl;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractFOPPDFTransformer;
import com.premiumminds.billy.spain.services.export.ESSimpleInvoiceData;
import com.premiumminds.billy.spain.services.export.pdf.ESAbstractFOPPDFTransformer;
import com.premiumminds.billy.spain.services.export.pdf.ESSimpleInvoicePDFTransformer;
import com.premiumminds.billy.spain.services.export.pdf.simpleinvoice.ESSimpleInvoiceTemplateBundle;
import java.time.format.DateTimeFormatter;

public class ESSimpleInvoicePDFFOPTransformer extends ESAbstractFOPPDFTransformer<ESSimpleInvoiceData>
        implements ESSimpleInvoicePDFTransformer {

    public static final String PARAM_KEYS_ROOT = "invoice";

    public ESSimpleInvoicePDFFOPTransformer(MathContext mathContext, String logoImagePath, InputStream xsltFileStream) {

        super(ESSimpleInvoiceData.class, mathContext, logoImagePath, xsltFileStream);
    }

    public ESSimpleInvoicePDFFOPTransformer(String logoImagePath, InputStream xsltFileStream) {

        this(BillyMathContext.get(), logoImagePath, xsltFileStream);
    }

    public ESSimpleInvoicePDFFOPTransformer(ESSimpleInvoiceTemplateBundle bundle) {
        super(ESSimpleInvoiceData.class, BillyMathContext.get(), bundle);
    }

    @Override
    protected ParamsTree<String, String> getNewParamsTree() {
        return new ParamsTree<>(ESSimpleInvoicePDFFOPTransformer.PARAM_KEYS_ROOT);
    }

    @Override
    public void setHeader(ParamsTree<String, String> params, ESSimpleInvoiceData entity) {
        params.getRoot().addChild(ParamKeys.ID, entity.getNumber());

        if (null != entity.getPayments()) {
            for (PaymentData p : entity.getPayments()) {
                params.getRoot().addChild(ParamKeys.INVOICE_PAYMETHOD,
                        this.getPaymentMechanismTranslation(p.getPaymentMethod()));
            }
        }

        params.getRoot().addChild(
            ParamKeys.EMISSION_DATE,
            entity
                .getLocalDate()
                .map(DateTimeFormatter.ISO_LOCAL_DATE::format)
                .orElse(AbstractFOPPDFTransformer.DATE_FORMAT.format(entity.getDate())));
    }

    @Override
    protected void setCustomer(ParamsTree<String, String> params, ESSimpleInvoiceData entity) {
        Node<String, String> customer = params.getRoot().addChild(ParamKeys.CUSTOMER);
        customer.addChild(ParamKeys.CUSTOMER_FINANCIAL_ID, this.getCustomerFinancialId(entity));
    }
    @Override
    public String getCustomerFinancialId(ESSimpleInvoiceData entity) {
        return entity.getCustomer().getTaxRegistrationNumber();
    }
}
