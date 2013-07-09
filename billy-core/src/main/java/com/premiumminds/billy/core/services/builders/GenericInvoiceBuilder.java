/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.services.builders;

import java.math.BigDecimal;
import java.util.Date;

import com.premiumminds.billy.core.persistence.entities.ShippingPointEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.DiscountType;

public interface GenericInvoiceBuilder<TBuilder extends GenericInvoiceBuilder<TBuilder, TEntry, TDocument>, TEntry extends GenericInvoiceEntry, TDocument extends GenericInvoice> extends Builder<TDocument> {

	public TBuilder setBusinessUID(UID businessUID);
	
	public TBuilder setCustomerUID(UID customerUID);

	public TBuilder setSupplierUID(UID supplier);

	public TBuilder setOfficeNumber(String number);

	public TBuilder setDate(Date date);

	public <T extends ShippingPointEntity> TBuilder setShippingOrigin(Builder<T> originBuilder);

	public <T extends ShippingPointEntity> TBuilder setShippingDestination(Builder<T> destinationBuilder);

	public TBuilder setPaymentTerms(String terms);

	public TBuilder setSelfBilled(boolean selfBilled);

	public TBuilder setSourceId(String source);

	public TBuilder setGeneralLedgerDate(Date date);

	public TBuilder setBatchId(String id);

	public TBuilder setTransactionId(String id);

	public TBuilder addReceiptNumber(String number);

	public <T extends GenericInvoiceEntry> TBuilder addEntry(Builder<T> entryBuilder);

	public TBuilder setSettlementDescription(String description);

	public TBuilder setSettlementDiscount(BigDecimal discount);

	public TBuilder setSettlementDate(Date date);

	public <T extends Enum<T>> TBuilder setPaymentMechanism(T mechanism);

	public TBuilder setCreditOrDebit(CreditOrDebit creditOrDebit);
	
	public TBuilder setDiscounts(DiscountType type, BigDecimal... discounts);
	
}
