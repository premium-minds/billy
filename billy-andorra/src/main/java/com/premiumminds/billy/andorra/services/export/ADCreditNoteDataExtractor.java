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
package com.premiumminds.billy.andorra.services.export;

import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNote;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.gin.services.export.TaxExemption;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyDataExtractor;
import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.CostumerData;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.export.ProductData;
import com.premiumminds.billy.gin.services.export.TaxData;
import com.premiumminds.billy.gin.services.export.impl.AbstractBillyDataExtractor;

public class ADCreditNoteDataExtractor extends AbstractBillyDataExtractor
        implements BillyDataExtractor<ADCreditNoteData> {

    private final DAOADCreditNote daoADCreditNote;
    private final ADInvoiceDataExtractor invoiceExtractor;

    @Inject
    public ADCreditNoteDataExtractor(DAOADCreditNote daoADCreditNote, ADInvoiceDataExtractor invoiceExtractor) {
        this.daoADCreditNote = daoADCreditNote;
        this.invoiceExtractor = invoiceExtractor;
    }

    @Override
    public ADCreditNoteData extract(StringID<GenericInvoice> uid) throws ExportServiceException {
        ADCreditNoteEntity entity = this.daoADCreditNote.get(uid);
        if (entity == null) {
            throw new ExportServiceException("Unable to find entity with uid " + uid.toString() + " to be extracted");
        }

        List<PaymentData> payments = this.extractPayments(entity.getPayments());
        CostumerData costumer = this.extractCostumer(entity.getCustomer());
        BusinessData business = this.extractBusiness(entity.getBusiness());
        List<ADCreditNoteEntryData> entries = this.extractCreditEntries(entity.getEntries());

        return new ADCreditNoteData(entity.getNumber(), entity.getDate(), entity.getSettlementDate(), payments,
									costumer, business, entries, entity.getTaxAmount(), entity.getAmountWithTax(),
									entity.getAmountWithoutTax(), entity.getSettlementDescription());
    }

    private List<ADCreditNoteEntryData> extractCreditEntries(List<ADCreditNoteEntry> entryEntities)
            throws ExportServiceException {
        List<ADCreditNoteEntryData> entries = new ArrayList<>(entryEntities.size());
        for (ADCreditNoteEntry entry : entryEntities) {
            ProductData product =
                    new ProductData(entry.getProduct().getProductCode(), entry.getProduct().getDescription());

            List<TaxData> taxes = this.extractTaxes(entry.getTaxes());
            ADInvoiceData reference = this.invoiceExtractor.extract(entry.getReference().getUID());

            entries.add(new ADCreditNoteEntryData(
                product, entry.getDescription(), entry.getQuantity(), entry.getTaxAmount(), entry.getUnitAmountWithTax(),
                entry.getAmountWithTax(), entry.getAmountWithoutTax(), taxes, reference, entry.getUnitOfMeasure(),
                TaxExemption.setExemption(entry.getTaxExemptionCode(), entry.getTaxExemptionReason())));
        }

        return entries;
    }

}
