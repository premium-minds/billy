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
package com.premiumminds.billy.andorra.services.export.pdf.creditnote;

import com.premiumminds.billy.andorra.services.export.ADCreditNoteEntryData;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.TaxData;
import com.premiumminds.billy.gin.services.export.TaxExemption;
import com.premiumminds.billy.andorra.services.export.ADCreditNoteData;
import com.premiumminds.billy.andorra.services.export.pdf.ADAbstractFOPPDFTransformer;
import com.premiumminds.billy.andorra.services.export.pdf.ADCreditNotePDFTransformer;
import java.io.InputStream;
import java.math.MathContext;
import java.util.Collection;
import java.util.List;

public class ADCreditNotePDFFOPTransformer extends ADAbstractFOPPDFTransformer<ADCreditNoteData>
        implements ADCreditNotePDFTransformer
{

    public static final String PARAM_KEYS_ROOT = "creditnote";
    public static final String PARAM_KEYS_INVOICE = "invoice";

    public ADCreditNotePDFFOPTransformer(MathContext mathContext, String logoImagePath, InputStream xsltFileStream) {

        super(ADCreditNoteData.class, mathContext, logoImagePath, xsltFileStream);
    }

    public ADCreditNotePDFFOPTransformer(String logoImagePath, InputStream xsltFileStream) {

        this(BillyMathContext.get(), logoImagePath, xsltFileStream);
    }

    public ADCreditNotePDFFOPTransformer(ADCreditNoteTemplateBundle bundle) {
        super(ADCreditNoteData.class, BillyMathContext.get(), bundle);
    }

    @Override
    protected ParamsTree<String, String> getNewParamsTree() {
        return new ParamsTree<>(ADCreditNotePDFFOPTransformer.PARAM_KEYS_ROOT);
    }

    @Override
    protected void setEntries(TaxTotals taxTotals, ParamsTree<String, String> params, ADCreditNoteData document) {

        Node<String, String> entries = params.getRoot().addChild(ParamKeys.ENTRIES);

        List<ADCreditNoteEntryData> creditNoteList = document.getEntries();
        for (ADCreditNoteEntryData entry : creditNoteList) {

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

            entryNode.addChild(ADCreditNotePDFFOPTransformer.PARAM_KEYS_INVOICE).addChild(ParamKeys.ID,
                                                                                          entry.getReference().getNumber());
        }
    }

    @Override
    public String getCustomerFinancialId(ADCreditNoteData invoice) {
        return invoice.getCustomer().getTaxRegistrationNumber();
    }
}
