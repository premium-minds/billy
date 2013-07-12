/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.entities.jpa;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.premiumminds.billy.core.util.DocumentType;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTInvoiceEntity;

@Entity
@Table(name = Config.TABLE_PREFIX + "INVOICE")
public class PTInvoiceEntity extends PTFinancialDocumentEntity
implements IPTInvoiceEntity {
	private static final long serialVersionUID = 1L;

	protected PTInvoiceEntity(){}
	
	public PTInvoiceEntity(
			PTBusinessEntity business, 
			BusinessOfficeEntity businessOffice,
			CustomerEntity customer,
			List<PTFinancialDocumentEntryEntity> documentEntries,
			List<PTTaxEntity> documentTaxes, 
			Date issueDate,
			String settlementDescription,
			Date settlementDate,
			BigDecimal netTotal,
			BigDecimal taxTotal,
			BigDecimal settlementTotal,
			BigDecimal grossTotal,
			Currency currency,
			boolean selfBilling,
			byte[] hash,			
			String sequenceId,
			long sequencialNumber,
			DocumentState state,
			PaymentMechanism paymentMechanism,
			String deliveryOriginId,
			AddressEntityImpl deliveryOriginAddress,
			Date deliveryShippingDate,
			String deliveryId,
			AddressEntityImpl deliveryDestinationAddress,
			Date deliveryDate,
			PTRegionContextEntity ptRegionContextEntity) {
		
		
		
		super(business, businessOffice, customer, DocumentType.INVOICE, documentEntries, 
				documentTaxes, issueDate, settlementDescription, settlementDate, netTotal, taxTotal, settlementTotal, 
				grossTotal, currency, selfBilling, hash, sequenceId, 
				sequencialNumber, state, paymentMechanism, deliveryOriginId, 
				deliveryOriginAddress, deliveryShippingDate, deliveryId, 
				deliveryDestinationAddress, deliveryDate, ptRegionContextEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IPTBusinessEntity getBusiness() {
		return super.getBusiness();
	}
	
}
