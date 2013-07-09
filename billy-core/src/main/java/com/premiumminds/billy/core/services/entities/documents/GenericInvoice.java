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
package com.premiumminds.billy.core.services.entities.documents;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;

import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.Entity;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Supplier;

public interface GenericInvoice extends Entity {

	public static enum CreditOrDebit {
		CREDIT,
		DEBIT
	}
	
	public String getNumber();
	
	public <T extends Business> T getBusiness();
	
	public <T extends Customer> T getCustomer();
	
	public <T extends Supplier> T getSupplier();
	
	public String getOfficeNumber();
	
	public Date getDate();
	
	public BigDecimal getNetAmount();
	
	public BigDecimal getTaxAmount();
	
	public BigDecimal getGrossAmount();
	
	public <T extends ShippingPoint> T getShippingOrigin();
	
	public <T extends ShippingPoint> T getShippingDestination();
	
	public String getPaymentTerms();
	
	public boolean isSelfBilled();
	
	public String getSourceId();
	
	public Date getGeneralLedgerDate();
	
	public String getBatchId();
	
	public String getTransactionId();
	
	public Collection<String> getReceiptNumbers();
	
	public <T extends GenericInvoiceEntry> Collection<T> getEntries();

	public Currency getCurrency();
	
	public String getSettlementDescription();
	
	public BigDecimal getSettlementDiscount();
	
	public Date getSettlementDate();
	
	public <T extends Enum<T>> T getPaymentMechanism();
	
	public CreditOrDebit getCreditOrDebit();
	
}
