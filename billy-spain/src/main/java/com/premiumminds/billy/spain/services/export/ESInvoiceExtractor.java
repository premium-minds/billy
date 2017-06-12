/**
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
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;

public class ESInvoiceExtractor extends AbstractBillyDataExtractor implements BillyDataExtractor<ESInvoiceData> {
	
	private final DAOESInvoice daoESInvoice;
	
	@Inject
	public ESInvoiceExtractor(DAOESInvoice daoESInvoice) {
		this.daoESInvoice = daoESInvoice;
	}

	@Override
	public ESInvoiceData extract(UID uid) throws ExportServiceException {
		ESInvoiceEntity entity = (ESInvoiceEntity) daoESInvoice.get(uid); //FIXME: Fix the DAOs to remove this cast
		if (entity == null) {
			throw new ExportServiceException("Unable to find entity with uid " + uid.toString() + " to be extracted");
		}
		
		List<PaymentData> payments = extractPayments(entity.getPayments());
		CostumerData costumer = extractCostumer(entity.getCustomer());
		BusinessData business = extractBusiness(entity.getBusiness());
		List<InvoiceEntryData> entries = extractEntries(entity.getEntries());
		
		return new ESInvoiceData(entity.getNumber(), entity.getDate(), entity.getSettlementDate(), 
				payments, costumer, business, entries, 
				entity.getTaxAmount(), entity.getAmountWithTax(), entity.getAmountWithoutTax(), 
				entity.getSettlementDescription());
	}
	
}
