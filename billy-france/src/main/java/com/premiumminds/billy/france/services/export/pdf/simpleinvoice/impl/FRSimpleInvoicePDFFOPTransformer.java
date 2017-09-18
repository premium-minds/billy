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
package com.premiumminds.billy.france.services.export.pdf.simpleinvoice.impl;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractFOPPDFTransformer;
import com.premiumminds.billy.france.services.export.FRSimpleInvoiceData;
import com.premiumminds.billy.france.services.export.pdf.FRAbstractFOPPDFTransformer;
import com.premiumminds.billy.france.services.export.pdf.FRSimpleInvoicePDFTransformer;
import com.premiumminds.billy.france.services.export.pdf.simpleinvoice.FRSimpleInvoiceTemplateBundle;

public class FRSimpleInvoicePDFFOPTransformer extends FRAbstractFOPPDFTransformer<FRSimpleInvoiceData>
        implements FRSimpleInvoicePDFTransformer {

    public static final String PARAM_KEYS_ROOT = "invoice";

    public FRSimpleInvoicePDFFOPTransformer(MathContext mathContext, String logoImagePath, InputStream xsltFileStream) {

        super(FRSimpleInvoiceData.class, mathContext, logoImagePath, xsltFileStream);
    }

    public FRSimpleInvoicePDFFOPTransformer(String logoImagePath, InputStream xsltFileStream) {

        this(BillyMathContext.get(), logoImagePath, xsltFileStream);
    }

    public FRSimpleInvoicePDFFOPTransformer(FRSimpleInvoiceTemplateBundle bundle) {
        super(FRSimpleInvoiceData.class, BillyMathContext.get(), bundle);
    }

    @Override
    protected ParamsTree<String, String> getNewParamsTree() {
        return new ParamsTree<>(FRSimpleInvoicePDFFOPTransformer.PARAM_KEYS_ROOT);
    }

    @Override
    public void setHeader(ParamsTree<String, String> params, FRSimpleInvoiceData entity) {
        params.getRoot().addChild(ParamKeys.ID, entity.getNumber());

        if (null != entity.getPayments()) {
            for (PaymentData p : entity.getPayments()) {
                params.getRoot().addChild(ParamKeys.INVOICE_PAYMETHOD,
                        this.getPaymentMechanismTranslation(p.getPaymentMethod()));
            }
        }

        params.getRoot().addChild(ParamKeys.EMISSION_DATE,
                AbstractFOPPDFTransformer.DATE_FORMAT.format(entity.getDate()));
    }

    @Override
    protected void setCustomer(ParamsTree<String, String> params, FRSimpleInvoiceData entity) {
        Node<String, String> customer = params.getRoot().addChild(ParamKeys.CUSTOMER);
        customer.addChild(ParamKeys.CUSTOMER_FINANCIAL_ID, this.getCustomerFinancialId(entity));
    }

    @Override
    protected void setTaxDetails(TaxTotals taxTotals, ParamsTree<String, String> params) {
        // Do nothing
    }

    @Override
    public String getCustomerFinancialId(FRSimpleInvoiceData entity) {
        return entity.getCustomer().getTaxRegistrationNumber();
    }

}
