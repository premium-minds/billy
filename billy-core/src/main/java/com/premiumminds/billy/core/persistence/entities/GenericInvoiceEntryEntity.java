/**
 * Copyright (C) 2013 Premium Minds.
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

import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;

public interface GenericInvoiceEntryEntity extends GenericInvoiceEntry,
	BaseEntity {

	public void setEntryNumber(Integer number);

	public <T extends ShippingPoint> void setShippingOrigin(T origin);

	public <T extends ShippingPoint> void setShippingDestination(T destination);

	public <T extends Product> void setProduct(T product);

	public void setQuantity(BigDecimal quantity);

	public void setUnitOfMeasure(String unit);

	public void setUnitAmountWithTax(BigDecimal amount);

	public void setUnitAmountWithoutTax(BigDecimal amount);

	public void setUnitTaxAmount(BigDecimal amount);

	public void setUnitDiscountAmount(BigDecimal amount);

	public void setAmountWithTax(BigDecimal amount);

	public void setAmountWithoutTax(BigDecimal amount);

	public void setTaxAmount(BigDecimal amount);

	public void setDiscountAmount(BigDecimal amount);

	public void setTaxPointDate(Date date);

	@Override
	public <T extends GenericInvoice> List<T> getDocumentReferences();

	public void setDescription(String description);

	public void setCreditOrDebit(CreditOrDebit creditOrDebit);

	public void setShippingCostsAmount(BigDecimal amount);

	public void setCurrency(Currency currency);

	public void setExchangeRateToDocumentCurrency(BigDecimal rate);

	@Override
	public <T extends Tax> List<T> getTaxes();

	public void setTaxExemptionReason(String exemptionReason);

	public void setAmountType(AmountType type);

}
