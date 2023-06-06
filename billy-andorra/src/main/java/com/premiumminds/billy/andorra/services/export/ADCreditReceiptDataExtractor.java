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

import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceipt;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditReceiptEntry;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.gin.services.export.TaxExemption;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyDataExtractor;
import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.export.ProductData;
import com.premiumminds.billy.gin.services.export.TaxData;
import com.premiumminds.billy.gin.services.export.impl.AbstractBillyDataExtractor;

public class ADCreditReceiptDataExtractor extends AbstractBillyDataExtractor
        implements BillyDataExtractor<ADCreditReceiptData> {

    private final DAOADCreditReceipt daoADReceipt;
    private final ADReceiptDataExtractor receiptExtractor;

    @Inject
    public ADCreditReceiptDataExtractor(DAOADCreditReceipt daoADReceipt, ADReceiptDataExtractor receiptExtractor) {
        this.daoADReceipt = daoADReceipt;
        this.receiptExtractor = receiptExtractor;
    }

    @Override
    public ADCreditReceiptData extract(StringID<GenericInvoice> uid) throws ExportServiceException {
        ADCreditReceiptEntity entity = this.daoADReceipt.get(uid);

        if (entity == null) {
            throw new ExportServiceException("Unable to find entity with uid " + uid.toString() + " to be extracted");
        }

        List<PaymentData> payments = this.extractPayments(entity.getPayments());
        BusinessData business = this.extractBusiness(entity.getBusiness());
        List<ADCreditReceiptEntryData> entries = this.extractCreditEntries(entity.getEntries());

        return new ADCreditReceiptData(entity.getNumber(), entity.getDate(), entity.getLocalDate(),
                                       entity.getSettlementDate(), payments, business, entries, entity.getTaxAmount(),
                                       entity.getAmountWithTax(), entity.getAmountWithoutTax(),
                                       entity.getSettlementDescription());
    }

    private List<ADCreditReceiptEntryData> extractCreditEntries(List<ADCreditReceiptEntry> entryEntities)
            throws ExportServiceException {
        List<ADCreditReceiptEntryData> entries = new ArrayList<>(entryEntities.size());
        for (ADCreditReceiptEntry entry : entryEntities) {
            ProductData product =
                    new ProductData(entry.getProduct().getProductCode(), entry.getProduct().getDescription());

            List<TaxData> taxes = this.extractTaxes(entry.getTaxes());
            ADReceiptData reference = this.receiptExtractor.extract(entry.getReference().getUID());

            entries.add(new ADCreditReceiptEntryData(
                product, entry.getDescription(), entry.getQuantity(), entry.getTaxAmount(), entry.getUnitAmountWithTax(),
                entry.getAmountWithTax(), entry.getAmountWithoutTax(), taxes, reference, entry.getUnitOfMeasure(),
                TaxExemption.setExemption(entry.getTaxExemptionCode(), entry.getTaxExemptionReason())));
        }

        return entries;
    }

}
