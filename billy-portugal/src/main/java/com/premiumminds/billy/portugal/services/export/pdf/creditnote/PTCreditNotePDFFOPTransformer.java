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

import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.TaxData;
import com.premiumminds.billy.gin.services.export.TaxExemption;
import com.premiumminds.billy.portugal.services.export.PTCreditNoteEntryData;
import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.services.export.PTCreditNoteData;
import com.premiumminds.billy.portugal.services.export.pdf.PTAbstractFOPPDFTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.PTCreditNotePDFTransformer;
import java.util.Collection;
import java.util.List;

public class PTCreditNotePDFFOPTransformer extends PTAbstractFOPPDFTransformer<PTCreditNoteData>
        implements PTCreditNotePDFTransformer {

    public static final String PARAM_KEYS_ROOT = "creditnote";
    public static final String PARAM_KEYS_INVOICE = "invoice";

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
    protected void setEntries(TaxTotals taxTotals, ParamsTree<String, String> params, PTCreditNoteData document) {

        Node<String, String> entries = params.getRoot().addChild(ParamKeys.ENTRIES);

        List<PTCreditNoteEntryData> creditNoteList = document.getEntries();
        for (PTCreditNoteEntryData entry : creditNoteList) {

            Node<String, String> entryNode = entries.addChild(ParamKeys.ENTRY);
            entryNode.addChild(ParamKeys.ENTRY_ID, entry.getProduct().getProductCode());
            entryNode.addChild(ParamKeys.ENTRY_DESCRIPTION, entry.getDescription());
            entryNode.addChild(ParamKeys.ENTRY_QUANTITY, entry.getQuantityWithUnitOfMeasure(this.mc.getRoundingMode()));
            entryNode.addChild(ParamKeys.ENTRY_UNIT_OF_MEASURE, entry.getUnitOfMeasure());
            entryNode.addChild(ParamKeys.ENTRY_UNIT_PRICE,
                               entry.getUnitAmountWithTax().setScale(2, this.mc.getRoundingMode()).toPlainString());
            entryNode.addChild(ParamKeys.ENTRY_TOTAL,
                               entry.getAmountWithTax().setScale(2, this.mc.getRoundingMode()).toPlainString());

            Collection<TaxData> list = entry.getTaxes();
            for (TaxData tax : list) {
                entryNode.addChild(ParamKeys.ENTRY_TAX, tax.getValue().setScale(2, this.mc.getRoundingMode()) +
                        (shouldPrintPercent(tax.getTaxRateType()) ? "%" : "&#8364;"));
                taxTotals.add(shouldPrintPercent(tax.getTaxRateType()), tax.getValue(),
                              entry.getAmountWithoutTax(), entry.getTaxAmount(), tax.getUID().toString(),
                              tax.getDesignation(), tax.getDescription());
            }

            if(entry.getExemption().isPresent()) {
                final TaxExemption exemption = entry.getExemption().get();

                entryNode.addChild(ParamKeys.ENTRY_TAX_EXEMPTION_CODE, exemption.getExemptionCode());
                entryNode.addChild(ParamKeys.ENTRY_TAX_EXEMPTION_REASON, exemption.getExemptionReason());
            }

            entryNode.addChild(PTCreditNotePDFFOPTransformer.PARAM_KEYS_INVOICE)
                     .addChild(ParamKeys.ID, entry.getReference().getNumber());
        }
    }

    @Override
    protected ParamsTree<String, String> mapDocumentToParamsTree(PTCreditNoteData entity) {

        ParamsTree<String, String> params = super.mapDocumentToParamsTree(entity);

        params.getRoot().addChild(PTParamKeys.INVOICE_HASH,
                this.getVerificationHashString(entity.getHash()));

        entity.getQrCodeString().ifPresent(s -> params.getRoot().addChild(PTParamKeys.QRCODE, s));
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
