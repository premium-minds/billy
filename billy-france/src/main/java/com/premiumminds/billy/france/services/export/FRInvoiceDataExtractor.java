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
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;

public class FRInvoiceDataExtractor extends AbstractBillyDataExtractor implements BillyDataExtractor<FRInvoiceData> {

    private final DAOFRInvoice daoFRInvoice;

    @Inject
    public FRInvoiceDataExtractor(DAOFRInvoice daoFRInvoice) {
        this.daoFRInvoice = daoFRInvoice;
    }

    @Override
    public FRInvoiceData extract(UID uid) throws ExportServiceException {
        FRInvoiceEntity entity = this.daoFRInvoice.get(uid); // FIXME: Fix the DAOs to remove this cast
        if (entity == null) {
            throw new ExportServiceException("Unable to find entity with uid " + uid.toString() + " to be extracted");
        }

        List<PaymentData> payments = this.extractPayments(entity.getPayments());
        CostumerData costumer = this.extractCostumer(entity.getCustomer());
        BusinessData business = this.extractBusiness(entity.getBusiness());
        List<InvoiceEntryData> entries = this.extractEntries(entity.getEntries());

        return new FRInvoiceData(entity.getNumber(), entity.getDate(), entity.getSettlementDate(), payments, costumer,
                business, entries, entity.getTaxAmount(), entity.getAmountWithTax(), entity.getAmountWithoutTax(),
                entity.getSettlementDescription());
    }

}
