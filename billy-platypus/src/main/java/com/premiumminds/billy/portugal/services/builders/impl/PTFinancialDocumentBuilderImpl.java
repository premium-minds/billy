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
package com.premiumminds.billy.portugal.services.builders.impl;

import java.util.Date;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.impl.FinancialDocumentBuilderImpl;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.PaymentMechanism;
import com.premiumminds.billy.portugal.services.builders.PTFinancialDocumentBuilder;
import com.premiumminds.billy.portugal.services.entities.PTFinancialDocument;
import com.premiumminds.billy.portugal.services.entities.PTFinancialDocumentEntry;
import com.premiumminds.billy.portugal.services.entities.impl.PTFinancialDocumentImpl;

public class PTFinancialDocumentBuilderImpl<TBuilder extends PTFinancialDocumentBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTFinancialDocumentEntry, TDocument extends PTFinancialDocument>
extends FinancialDocumentBuilderImpl<TBuilder, TEntry, TDocument>
implements PTFinancialDocumentBuilder<TBuilder, TEntry, TDocument> {

	public PTFinancialDocumentBuilderImpl() {
		this.document = new PTFinancialDocumentImpl();
	}
	
	protected PTFinancialDocumentImpl getDocumentImpl() {
		return (PTFinancialDocumentImpl) this.document;
	}

	@Override
	public TBuilder setSequenceId(String sequenceId) {
		getDocumentImpl().setSequenceId(sequenceId);
		return getBuilder();
	}

	@Override
	public TBuilder setSequencialNumber(long sequencialNumber) {
		getDocumentImpl().setSequencialNumber(sequencialNumber);
		return getBuilder();
	}

	@Override
	public TBuilder setPaymentMechanism(PaymentMechanism paymentMechanism) {
		getDocumentImpl().setPaymentMechanism(paymentMechanism);
		return getBuilder();
	}

	@Override
	public TBuilder setDeliveryOriginId(String deliveryOriginId) {
		getDocumentImpl().setDeliveryOriginId(deliveryOriginId);
		return getBuilder();
	}

	@Override
	public TBuilder setDeliveryOriginAddress(Address deliveryOriginAddress) {
		getDocumentImpl().setDeliveryOriginAddress(deliveryOriginAddress);
		return getBuilder();
	}

	@Override
	public TBuilder setDeliveryShippingDate(Date deliveryShippingDate) {
		getDocumentImpl().setDeliveryShippingDate(deliveryShippingDate);
		return getBuilder();
	}

	@Override
	public TBuilder setDeliveryId(String deliveryId) {
		getDocumentImpl().setDeliveryId(deliveryId);
		return getBuilder();
	}

	@Override
	public TBuilder setDeliveryDestinationAddress(
			Address deliveryDestinationAddress) {
		getDocumentImpl().setDeliveryDestinationAddress(deliveryDestinationAddress);
		return getBuilder();
	}

	@Override
	public TBuilder setDeliveryDate(Date deliveryDate) {
		getDocumentImpl().setDeliveryDate(deliveryDate);
		return getBuilder();
	}
	
	@Override
	public TBuilder setRegionContextUID(UID uid) {
		getDocumentImpl().setRegionContextUID(uid);
		return getBuilder();
	};
	
}
