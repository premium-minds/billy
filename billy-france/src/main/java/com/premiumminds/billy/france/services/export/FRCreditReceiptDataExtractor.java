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
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.export.ProductData;
import com.premiumminds.billy.gin.services.export.TaxData;
import com.premiumminds.billy.gin.services.export.impl.AbstractBillyDataExtractor;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceipt;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntity;
import com.premiumminds.billy.france.services.entities.FRCreditReceiptEntry;

public class FRCreditReceiptDataExtractor extends AbstractBillyDataExtractor
        implements BillyDataExtractor<FRCreditReceiptData> {

    private final DAOFRCreditReceipt daoFRReceipt;
    private final FRReceiptDataExtractor receiptExtractor;

    @Inject
    public FRCreditReceiptDataExtractor(DAOFRCreditReceipt daoFRReceipt, FRReceiptDataExtractor receiptExtractor) {
        this.daoFRReceipt = daoFRReceipt;
        this.receiptExtractor = receiptExtractor;
    }

    @Override
    public FRCreditReceiptData extract(UID uid) throws ExportServiceException {
        FRCreditReceiptEntity entity = this.daoFRReceipt.get(uid); // FIXME: Fix the DAOs to remove
                                                                   // this cast
        if (entity == null) {
            throw new ExportServiceException("Unable to find entity with uid " + uid.toString() + " to be extracted");
        }

        List<PaymentData> payments = this.extractPayments(entity.getPayments());
        BusinessData business = this.extractBusiness(entity.getBusiness());
        List<FRCreditReceiptEntryData> entries = this.extractCreditEntries(entity.getEntries());

        return new FRCreditReceiptData(entity.getNumber(), entity.getDate(), entity.getSettlementDate(), payments,
                business, entries, entity.getTaxAmount(), entity.getAmountWithTax(), entity.getAmountWithoutTax(),
                entity.getSettlementDescription());
    }

    private List<FRCreditReceiptEntryData> extractCreditEntries(List<FRCreditReceiptEntry> entryEntities)
            throws ExportServiceException {
        List<FRCreditReceiptEntryData> entries = new ArrayList<>(entryEntities.size());
        for (FRCreditReceiptEntry entry : entryEntities) {
            ProductData product =
                    new ProductData(entry.getProduct().getProductCode(), entry.getProduct().getDescription());

            List<TaxData> taxes = this.extractTaxes(entry.getTaxes());
            FRReceiptData reference = this.receiptExtractor.extract(entry.getReference().getUID());

            entries.add(new FRCreditReceiptEntryData(product, entry.getDescription(), entry.getQuantity(),
                    entry.getTaxAmount(), entry.getUnitAmountWithTax(), entry.getAmountWithTax(),
                    entry.getAmountWithoutTax(), taxes, reference));
        }

        return entries;
    }

}
