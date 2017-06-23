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
package com.premiumminds.billy.core.services.entities.documents;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.services.entities.Entity;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;

public interface GenericInvoiceEntry extends Entity {

  public static class Builder extends GenericInvoiceEntryBuilderImpl<Builder, GenericInvoiceEntry> {

    @Inject
    public Builder(DAOGenericInvoiceEntry daoEntry, DAOGenericInvoice daoGenericInvoice,
        DAOTax daoTax, DAOProduct daoProduct, DAOContext daoContext) {
      super(daoEntry, daoGenericInvoice, daoTax, daoProduct, daoContext);
    }
  }

  public Integer getEntryNumber();

  public <T extends ShippingPoint> T getShippingOrigin();

  public <T extends ShippingPoint> T getShippingDestination();

  public <T extends Product> T getProduct();

  public BigDecimal getQuantity();

  public String getUnitOfMeasure();

  public BigDecimal getUnitAmountWithTax();

  public BigDecimal getUnitAmountWithoutTax();

  public BigDecimal getUnitTaxAmount();

  public BigDecimal getUnitDiscountAmount();

  public BigDecimal getAmountWithTax();

  public BigDecimal getAmountWithoutTax();

  public BigDecimal getTaxAmount();

  public BigDecimal getDiscountAmount();

  public Date getTaxPointDate();

  public <T extends GenericInvoice> Collection<T> getDocumentReferences();

  public String getDescription();

  public CreditOrDebit getCreditOrDebit();

  public BigDecimal getShippingCostsAmount();

  public Currency getCurrency();

  public BigDecimal getExchangeRateToDocumentCurrency();

  public <T extends Tax> Collection<T> getTaxes();

  public String getTaxExemptionReason();

  public AmountType getAmountType();

}
