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

import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyDataExtractor;
import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.CostumerData;
import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.export.impl.AbstractBillyDataExtractor;

public class ADInvoiceDataExtractor extends AbstractBillyDataExtractor implements BillyDataExtractor<ADInvoiceData> {

    private final DAOADInvoice daoADInvoice;

    @Inject
    public ADInvoiceDataExtractor(DAOADInvoice daoADInvoice) {
        this.daoADInvoice = daoADInvoice;
    }

    @Override
    public ADInvoiceData extract(StringID<GenericInvoice> uid) throws ExportServiceException {
        ADInvoiceEntity entity = this.daoADInvoice.get(uid); // FIXME: Fix the DAOs to remove this cast
        if (entity == null) {
            throw new ExportServiceException("Unable to find entity with uid " + uid.toString() + " to be extracted");
        }

        List<PaymentData> payments = this.extractPayments(entity.getPayments());
        CostumerData costumer = this.extractCostumer(entity.getCustomer());
        BusinessData business = this.extractBusiness(entity.getBusiness());
        List<InvoiceEntryData> entries = this.extractEntries(entity.getEntries());

        return new ADInvoiceData(entity.getNumber(), entity.getDate(), entity.getLocalDate(),
                                 entity.getSettlementDate(), payments, costumer, business, entries,
                                 entity.getTaxAmount(), entity.getAmountWithTax(), entity.getAmountWithoutTax(),
                                 entity.getSettlementDescription());
    }

}
