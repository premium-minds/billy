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
package com.premiumminds.billy.france.services.export;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyDataExtractor;
import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.CostumerData;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.export.ProductData;
import com.premiumminds.billy.gin.services.export.TaxData;
import com.premiumminds.billy.gin.services.export.impl.AbstractBillyDataExtractor;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNote;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntity;
import com.premiumminds.billy.france.services.entities.FRCreditNoteEntry;

public class FRCreditNoteDataExtractor extends AbstractBillyDataExtractor
        implements BillyDataExtractor<FRCreditNoteData> {

    private final DAOFRCreditNote daoFRCreditNote;
    private final FRInvoiceDataExtractor invoiceExtractor;

    @Inject
    public FRCreditNoteDataExtractor(DAOFRCreditNote daoFRCreditNote, FRInvoiceDataExtractor invoiceExtractor) {
        this.daoFRCreditNote = daoFRCreditNote;
        this.invoiceExtractor = invoiceExtractor;
    }

    @Override
    public FRCreditNoteData extract(UID uid) throws ExportServiceException {
        FRCreditNoteEntity entity = this.daoFRCreditNote.get(uid); // FIXME: Fix the DAOs to remove this
                                                                   // cast
        if (entity == null) {
            throw new ExportServiceException("Unable to find entity with uid " + uid.toString() + " to be extracted");
        }

        List<PaymentData> payments = this.extractPayments(entity.getPayments());
        CostumerData costumer = this.extractCostumer(entity.getCustomer());
        BusinessData business = this.extractBusiness(entity.getBusiness());
        List<FRCreditNoteEntryData> entries = this.extractCreditEntries(entity.getEntries());

        return new FRCreditNoteData(entity.getNumber(), entity.getDate(), entity.getSettlementDate(), payments,
                costumer, business, entries, entity.getTaxAmount(), entity.getAmountWithTax(),
                entity.getAmountWithoutTax(), entity.getSettlementDescription());
    }

    private List<FRCreditNoteEntryData> extractCreditEntries(List<FRCreditNoteEntry> entryEntities)
            throws ExportServiceException {
        List<FRCreditNoteEntryData> entries = new ArrayList<>(entryEntities.size());
        for (FRCreditNoteEntry entry : entryEntities) {
            ProductData product =
                    new ProductData(entry.getProduct().getProductCode(), entry.getProduct().getDescription());

            List<TaxData> taxes = this.extractTaxes(entry.getTaxes());
            FRInvoiceData reference = this.invoiceExtractor.extract(entry.getReference().getUID());

            entries.add(new FRCreditNoteEntryData(product, entry.getDescription(), entry.getQuantity(),
                    entry.getTaxAmount(), entry.getUnitAmountWithTax(), entry.getAmountWithTax(),
                    entry.getAmountWithoutTax(), taxes, reference));
        }

        return entries;
    }

}
