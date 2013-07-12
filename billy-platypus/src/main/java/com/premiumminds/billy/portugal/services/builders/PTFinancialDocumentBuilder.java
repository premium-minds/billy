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
package com.premiumminds.billy.portugal.services.builders;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.FinancialDocumentBuilder;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.PaymentMechanism;
import com.premiumminds.billy.portugal.services.entities.PTFinancialDocument;
import com.premiumminds.billy.portugal.services.entities.PTFinancialDocumentEntry;

public interface PTFinancialDocumentBuilder<TBuilder extends PTFinancialDocumentBuilder<TBuilder, TEntry, TDocument>, TEntry extends PTFinancialDocumentEntry, TDocument extends PTFinancialDocument> extends FinancialDocumentBuilder<TBuilder, TEntry, TDocument> {

	public TBuilder setSequenceId(@NotNull String sequenceId);

	public TBuilder setSequencialNumber(long sequencialNumber);

	public TBuilder setPaymentMechanism(@NotNull PaymentMechanism paymentMechanism);

	public TBuilder setDeliveryOriginId(String deliveryOriginId);

	public TBuilder setDeliveryOriginAddress(Address deliveryOriginAddress);

	public TBuilder setDeliveryShippingDate(Date deliveryShippingDate);

	public TBuilder setDeliveryId(String deliveryId);

	public TBuilder setDeliveryDestinationAddress(Address deliveryDestinationAddress);

	public TBuilder setDeliveryDate(Date deliveryDate);
	
	public TBuilder setRegionContextUID(UID uid);
	
}
