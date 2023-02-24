/*
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

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRSimpleInvoice;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyDataExtractor;
import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.CostumerData;
import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.export.impl.AbstractBillyDataExtractor;

public class FRSimpleInvoiceDataExtractor extends AbstractBillyDataExtractor
        implements BillyDataExtractor<FRSimpleInvoiceData> {

    private final DAOFRSimpleInvoice daoFRSimpleInvoice;

    @Inject
    public FRSimpleInvoiceDataExtractor(DAOFRSimpleInvoice daoFRSimpleInvoice) {
        this.daoFRSimpleInvoice = daoFRSimpleInvoice;
    }

    @Override
    public FRSimpleInvoiceData extract(StringID<GenericInvoice> uid) throws ExportServiceException {
        FRSimpleInvoiceEntity entity = this.daoFRSimpleInvoice.get(uid);
        if (entity == null) {
            throw new ExportServiceException("Unable to find entity with uid " + uid.toString() + " to be extracted");
        }

        List<PaymentData> payments = this.extractPayments(entity.getPayments());
        CostumerData costumer = this.extractCostumer(entity.getCustomer());
        BusinessData business = this.extractBusiness(entity.getBusiness());
        List<InvoiceEntryData> entries = this.extractEntries(entity.getEntries());

        return new FRSimpleInvoiceData(entity.getNumber(), entity.getDate(), entity.getSettlementDate(), payments,
                costumer, business, entries, entity.getTaxAmount(), entity.getAmountWithTax(),
                entity.getAmountWithoutTax(), entity.getSettlementDescription());
    }

}
