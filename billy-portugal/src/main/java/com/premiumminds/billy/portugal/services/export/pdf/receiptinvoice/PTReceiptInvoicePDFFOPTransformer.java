/*
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
package com.premiumminds.billy.portugal.services.export.pdf.receiptinvoice;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.services.export.PTReceiptInvoiceData;
import com.premiumminds.billy.portugal.services.export.pdf.PTAbstractFOPPDFTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.PTReceiptInvoicePDFTransformer;
import java.io.InputStream;
import java.math.MathContext;
import java.time.format.DateTimeFormatter;

public class PTReceiptInvoicePDFFOPTransformer extends PTAbstractFOPPDFTransformer<PTReceiptInvoiceData>
        implements PTReceiptInvoicePDFTransformer {

    public PTReceiptInvoicePDFFOPTransformer(MathContext mathContext, String logoImagePath, InputStream xsltFileStream,
            String softwareCertificationId, Config config) {

        super(PTReceiptInvoiceData.class, mathContext, logoImagePath, xsltFileStream, softwareCertificationId, config);

    }

    public PTReceiptInvoicePDFFOPTransformer(String logoImagePath, InputStream xsltFileStream,
            String softwareCertificationId) {

        this(BillyMathContext.get(), logoImagePath, xsltFileStream, softwareCertificationId, new Config());
    }

    public PTReceiptInvoicePDFFOPTransformer(PTReceiptInvoiceTemplateBundle bundle) {
        super(PTReceiptInvoiceData.class, BillyMathContext.get(), bundle, new Config());
    }

    @Override
    protected ParamsTree<String, String> mapDocumentToParamsTree(PTReceiptInvoiceData invoice) {

        ParamsTree<String, String> params = super.mapDocumentToParamsTree(invoice);

        params.getRoot().addChild(PTParamKeys.INVOICE_HASH,
                this.getVerificationHashString(invoice.getHash()));
        invoice.getQrCodeString().ifPresent(s -> params.getRoot().addChild(PTParamKeys.QRCODE, s));
        params.getRoot().addChild(PTParamKeys.ATCUD, invoice.getAtcud());
        params.getRoot().addChild(PTParamKeys.SOFTWARE_CERTIFICATE_NUMBER, this.getSoftwareCertificationId());

        return params;
    }

    @Override
    protected void setHeader(ParamsTree<String, String> params, PTReceiptInvoiceData document) {
        params.getRoot().addChild(ParamKeys.ID, document.getNumber());

        if (null != document.getPayments()) {
            for (PaymentData p : document.getPayments()) {
                params.getRoot().addChild(ParamKeys.INVOICE_PAYMETHOD,
                        this.getPaymentMechanismTranslation(p.getPaymentMethod()));
            }
        }

        params.getRoot()
              .addChild(ParamKeys.EMISSION_DATE, DateTimeFormatter.ISO_LOCAL_DATE.format(document.getLocalDate()));
    }

    @Override
    protected void setCustomer(ParamsTree<String, String> params, PTReceiptInvoiceData document) {

        Node<String, String> customer = params.getRoot().addChild(ParamKeys.CUSTOMER);

        customer.addChild(ParamKeys.CUSTOMER_FINANCIAL_ID, this.getCustomerFinancialId(document));
    }
}
