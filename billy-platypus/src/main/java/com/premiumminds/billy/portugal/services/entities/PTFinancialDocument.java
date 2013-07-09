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
package com.premiumminds.billy.portugal.services.entities;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.FinancialDocument;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.DocumentState;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.PaymentMechanism;
import com.premiumminds.billy.portugal.services.builders.impl.PTFinancialDocumentBuilderImpl;

public interface PTFinancialDocument extends FinancialDocument {

	public static class Builder extends PTFinancialDocumentBuilderImpl<Builder, PTFinancialDocumentEntry, PTFinancialDocument> {
		public static Builder create() {
			return new Builder();
		}
	}
	
	public UID getRegionContextUID();
	
	public String getSequenceId();

	public long getSequentialNumber();

	public DocumentState getDocumentState();

	public byte[] getHash();

	@NotNull
	public PaymentMechanism getPaymentMechanism();

	public String getDeliveryOriginId();

	public Address getDeliveryOriginAddress();

	public Date getDeliveryShippingDate();

	public String getDeliveryId();

	public Address getDeliveryDestinationAddress();

	public Date getDeliveryDate();
	
	@SuppressWarnings("unchecked")
	@NotNull
	@Override
	public List<PTFinancialDocumentEntry> getDocumentEntries();

}
