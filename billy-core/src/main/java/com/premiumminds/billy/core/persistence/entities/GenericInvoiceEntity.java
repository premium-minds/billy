/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.entities;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;

public interface GenericInvoiceEntity extends GenericInvoice, BaseEntity {

	public void setSeriesNumber(Integer seriesNumber);

	public void setNumber(String number);

	public void setSeries(String series);

	public <T extends Business> void setBusiness(T business);

	public <T extends CustomerEntity> void setCustomer(T customer);

	public <T extends SupplierEntity> void setSupplier(T supplier);

	public void setOfficeNumber(String number);

	public void setDate(Date date);

	public void setAmountWithTax(BigDecimal amount);

	public void setAmountWithoutTax(BigDecimal amount);

	public void setTaxAmount(BigDecimal amount);

	public void setDiscountsAmount(BigDecimal amount);

	public <T extends ShippingPointEntity> void setShippingOrigin(T origin);

	public <T extends ShippingPointEntity> void setShippingDestination(
			T destination);

	public void setPaymentTerms(String terms);

	public void setSelfBilled(Boolean selfBilled);

	public void setSourceId(String source);

	public void setGeneralLedgerDate(Date date);

	public void setBatchId(String id);

	public void setTransactionId(String id);

	@Override
	public List<String> getReceiptNumbers();

	@Override
	public <T extends GenericInvoiceEntry> List<T> getEntries();

	public void setCurrency(Currency currency);

	public void setSettlementDescription(String description);

	public void setSettlementDiscount(BigDecimal discount);

	public void setSettlementDate(Date date);
	
	public <T extends Payment> List<T> getPayments();

	public void setCreditOrDebit(CreditOrDebit creditOrDebit);
	
	public void setScale(Integer scale);

}
