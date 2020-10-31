/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.pdf.invoice;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.services.export.PTInvoiceData;
import com.premiumminds.billy.portugal.services.export.pdf.PTAbstractFOPPDFTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.PTInvoicePDFTransformer;
import java.io.InputStream;
import java.math.MathContext;

public class PTInvoicePDFFOPTransformer extends PTAbstractFOPPDFTransformer<PTInvoiceData>
        implements PTInvoicePDFTransformer {

    public PTInvoicePDFFOPTransformer(MathContext mathContext, String logoImagePath, InputStream xsltFileStream,
									  String softwareCertificationId, Config config) {

        super(PTInvoiceData.class, mathContext, logoImagePath, xsltFileStream, softwareCertificationId, config);

    }

    public PTInvoicePDFFOPTransformer(String logoImagePath, InputStream xsltFileStream,
									  String softwareCertificationId) {

        this(BillyMathContext.get(), logoImagePath, xsltFileStream, softwareCertificationId, new Config());
    }

    public PTInvoicePDFFOPTransformer(PTInvoiceTemplateBundle bundle) {
        super(PTInvoiceData.class, BillyMathContext.get(), bundle, new Config());
    }

    @Override
    protected ParamsTree<String, String> mapDocumentToParamsTree(PTInvoiceData entity) {

        ParamsTree<String, String> params = super.mapDocumentToParamsTree(entity);

        params.getRoot().addChild(PTParamKeys.INVOICE_HASH,
                this.getVerificationHashString(entity.getHash().getBytes()));
        entity.getQrCodeString().ifPresent(s -> params.getRoot().addChild(PTParamKeys.QRCODE, s));
        params.getRoot().addChild(PTParamKeys.SOFTWARE_CERTIFICATE_NUMBER, this.getSoftwareCertificationId());

        return params;
    }

    @Override
    protected void setHeader(ParamsTree<String, String> params, PTInvoiceData entity) {
        if (null != entity.getSettlementDescription()) {
            params.getRoot().addChild(PTParamKeys.INVOICE_PAYSETTLEMENT, entity.getSettlementDescription());

        }
        super.setHeader(params, entity);
    }

}
