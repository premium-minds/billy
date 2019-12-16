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
package com.premiumminds.billy.spain.services.export;

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
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditReceiptEntry;

public class ESCreditReceiptDataExtractor extends AbstractBillyDataExtractor
        implements BillyDataExtractor<ESCreditReceiptData> {

    private final DAOESCreditReceipt daoESReceipt;
    private final ESReceiptDataExtractor receiptExtractor;

    @Inject
    public ESCreditReceiptDataExtractor(DAOESCreditReceipt daoESReceipt, ESReceiptDataExtractor receiptExtractor) {
        this.daoESReceipt = daoESReceipt;
        this.receiptExtractor = receiptExtractor;
    }

    @Override
    public ESCreditReceiptData extract(UID uid) throws ExportServiceException {
        ESCreditReceiptEntity entity = this.daoESReceipt.get(uid); // FIXME: Fix the DAOs to remove
                                                                   // this cast
        if (entity == null) {
            throw new ExportServiceException("Unable to find entity with uid " + uid.toString() + " to be extracted");
        }

        List<PaymentData> payments = this.extractPayments(entity.getPayments());
        BusinessData business = this.extractBusiness(entity.getBusiness());
        List<ESCreditReceiptEntryData> entries = this.extractCreditEntries(entity.getEntries());

        return new ESCreditReceiptData(entity.getNumber(), entity.getDate(), entity.getSettlementDate(), payments,
                business, entries, entity.getTaxAmount(), entity.getAmountWithTax(), entity.getAmountWithoutTax(),
                entity.getSettlementDescription());
    }

    private List<ESCreditReceiptEntryData> extractCreditEntries(List<ESCreditReceiptEntry> entryEntities)
            throws ExportServiceException {
        List<ESCreditReceiptEntryData> entries = new ArrayList<>(entryEntities.size());
        for (ESCreditReceiptEntry entry : entryEntities) {
            ProductData product =
                    new ProductData(entry.getProduct().getProductCode(), entry.getProduct().getDescription());

            List<TaxData> taxes = this.extractTaxes(entry.getTaxes());
            ESReceiptData reference = this.receiptExtractor.extract(entry.getReference().getUID());

            entries.add(new ESCreditReceiptEntryData(product, entry.getDescription(), entry.getQuantity(),
                    entry.getTaxAmount(), entry.getUnitAmountWithTax(), entry.getAmountWithTax(),
                    entry.getAmountWithoutTax(), taxes, reference, entry.getUnitOfMeasure()));
        }

        return entries;
    }

}
