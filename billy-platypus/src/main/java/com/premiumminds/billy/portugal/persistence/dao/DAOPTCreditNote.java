/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-platypus.
 * 
 * billy-platypus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-platypus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-platypus.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.portugal.persistence.dao;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.BusinessOfficeEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.DocumentState;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.PaymentMechanism;

public interface DAOPTCreditNote extends DAO<IPTCreditNoteEntity> {

	List<IPTCreditNoteEntity> getBusinessCreditNotesForSAFTPT(UUID uuid,
			Date fromDate, Date toDate);

	IPTCreditNoteEntity getPTCreditNoteInstance(IPTBusinessEntity business,
			BusinessOfficeEntity office, CustomerEntity customer,
			List<IPTFinancialDocumentEntryEntity> documentEntries,
			List<IPTTaxEntity> documentTaxes, Date issueDate,
			String settlementDescription, Date settlementDate,
			BigDecimal netTotal, BigDecimal taxTotal,
			BigDecimal settlementTotal, BigDecimal grossTotal,
			Currency currency, boolean selfBilling, byte[] hash,
			String sequenceId, long sequentialNumber, DocumentState state,
			PaymentMechanism paymentMechanism, String deliveryOriginId,
			AddressEntity deliveryOriginAddress, Date deliveryShippingDate,
			String deliveryId, AddressEntity deliveryDestinationAddress,
			Date deliveryDate, String reasonForCredit,
			IPTRegionContextEntity ptRegionContext);

	IPTCreditNoteEntity getLastIssuedCreditNote(UUID uuid, String sequenceID);

}
