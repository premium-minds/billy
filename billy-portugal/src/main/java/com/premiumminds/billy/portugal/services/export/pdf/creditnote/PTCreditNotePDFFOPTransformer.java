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
package com.premiumminds.billy.portugal.services.export.pdf.creditnote;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.services.export.PTCreditNoteData;
import com.premiumminds.billy.portugal.services.export.pdf.PTAbstractFOPPDFTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.PTCreditNotePDFTransformer;

public class PTCreditNotePDFFOPTransformer extends PTAbstractFOPPDFTransformer<PTCreditNoteData>
        implements PTCreditNotePDFTransformer {

    public static final String PARAM_KEYS_ROOT = "creditnote";

    public PTCreditNotePDFFOPTransformer(MathContext mathContext, String logoImagePath, InputStream xsltFileStream,
            String softwareCertificationId, Config config) {

        super(PTCreditNoteData.class, mathContext, logoImagePath, xsltFileStream, softwareCertificationId, config);

    }

    public PTCreditNotePDFFOPTransformer(String logoImagePath, InputStream xsltFileStream,
            String softwareCertificationId) {

        this(BillyMathContext.get(), logoImagePath, xsltFileStream, softwareCertificationId, new Config());
    }

    public PTCreditNotePDFFOPTransformer(PTCreditNoteTemplateBundle bundle) {
        super(PTCreditNoteData.class, BillyMathContext.get(), bundle, new Config());
    }

    @Override
    protected ParamsTree<String, String> getNewParamsTree() {
        return new ParamsTree<>(PTCreditNotePDFFOPTransformer.PARAM_KEYS_ROOT);
    }

    @Override
    protected ParamsTree<String, String> mapDocumentToParamsTree(PTCreditNoteData entity) {

        ParamsTree<String, String> params = super.mapDocumentToParamsTree(entity);

        params.getRoot().addChild(PTParamKeys.INVOICE_HASH,
                this.getVerificationHashString(entity.getHash().getBytes()));
        params.getRoot().addChild(PTParamKeys.SOFTWARE_CERTIFICATE_NUMBER, this.getSoftwareCertificationId());

        return params;
    }

    @Override
    protected void setHeader(ParamsTree<String, String> params, PTCreditNoteData entity) {
        if (null != entity.getSettlementDescription()) {
            params.getRoot().addChild(PTParamKeys.INVOICE_PAYSETTLEMENT, entity.getSettlementDescription());

        }
        super.setHeader(params, entity);
    }

}
