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
package com.premiumminds.billy.portugal.persistence.entities;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;

public interface IPTFinancialDocumentEntity extends GenericInvoiceEntity {

	public static enum DocumentState {
		NORMAL,
		SELF_BILLING,
		VOID,
		BILLED
	}

	public static enum PaymentMechanism {
		CASH,
		CHECK,
		DEBIT_CARD,
		CREDIT_CARD,
		BANK_TRANSFER,
		RESTAURANT_TICKET
	}

	@SuppressWarnings("unchecked")
	@NotNull
	@Override
	public IPTBusinessEntity getBusiness();
	
	@NotNull
	@Override
	public List<? extends IPTFinancialDocumentEntryEntity> getDocumentEntries();
	
	@NotNull
	@Override
	public List<? extends IPTTaxEntity> getDocumentTaxes();
	
	@NotNull
	public String getSequenceID();

	@NotNull
	public long getSequencialNumber();

	@NotNull
	public DocumentState getDocumentState();

	public byte[] getHash();

	@NotNull
	public PaymentMechanism getPaymentMechanism();

	public String getDeliveryOriginId();
	
	public AddressEntity getDeliveryOriginAddress();

	public Date getDeliveryShippingDate();

	public String getDeliveryId();
	
	public AddressEntity getDeliveryDestinationAddress();

	public Date getDeliveryDate();
	
	public String getDocumentNumberSAFTPT();
	
	public IPTRegionContextEntity getPTRegionContext();

	
	//Setters
	
	void setSequencialNumber(long sequencialNumber);
	
	public void setDocumentState(DocumentState state);
	
	public void setHash(@NotNull byte[] hash);

	public void setPTRegionContext(IPTRegionContextEntity regionContext);
	
}
