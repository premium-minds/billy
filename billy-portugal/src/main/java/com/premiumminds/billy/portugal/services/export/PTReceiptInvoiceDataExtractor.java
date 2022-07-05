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
package com.premiumminds.billy.portugal.services.export;

import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeStringGenerator;
import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyDataExtractor;
import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.CostumerData;
import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.export.impl.AbstractBillyDataExtractor;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.services.entities.PTReceiptInvoice;

public class PTReceiptInvoiceDataExtractor extends AbstractBillyDataExtractor
        implements BillyDataExtractor<PTReceiptInvoiceData> {

    private final DAOPTReceiptInvoice daoPTReceiptInvoice;
    private final QRCodeStringGenerator qrCodeStringGenerator;

    @Inject
    public PTReceiptInvoiceDataExtractor(
        final DAOPTReceiptInvoice daoPTReceiptInvoice, final QRCodeStringGenerator qrCodeStringGenerator) {

        this.daoPTReceiptInvoice = daoPTReceiptInvoice;
        this.qrCodeStringGenerator = qrCodeStringGenerator;
    }

    @Override
    public PTReceiptInvoiceData extract(UID uid) throws ExportServiceException {
        PTReceiptInvoice entity = this.daoPTReceiptInvoice.get(uid);
        if (entity == null) {
            throw new ExportServiceException("Unable to find entity with uid " + uid.toString() + " to be extracted");
        }

        List<PaymentData> payments = this.extractPayments(entity.getPayments());
        CostumerData costumer = this.extractCostumer(entity.getCustomer());
        BusinessData business = this.extractBusiness(entity.getBusiness());
        List<InvoiceEntryData> entries = this.extractEntries(entity.getEntries());

        final String qrCodeString;
        try {
            qrCodeString = qrCodeStringGenerator.generateQRCodeData(entity);
        } catch (RequiredFieldNotFoundException e) {
            throw new ExportServiceException(e);
        }
        return new PTReceiptInvoiceData(entity.getNumber(), entity.getDate(), entity.getSettlementDate(), payments,
                                        costumer, business, entries, entity.getTaxAmount(), entity.getAmountWithTax(),
                                        entity.getAmountWithoutTax(), entity.getSettlementDescription(), entity.getHash(),
                                        qrCodeString, entity.getATCUD());
    }

}
