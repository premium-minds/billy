/*
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
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.services.entities.Entity;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;

public interface GenericInvoiceEntry extends Entity<GenericInvoiceEntry> {

    class Builder extends
            GenericInvoiceEntryBuilderImpl<Builder, GenericInvoiceEntry, GenericInvoiceEntity, DAOGenericInvoiceEntry
                    , DAOGenericInvoice> {

        @Inject
        public Builder(DAOGenericInvoiceEntry daoEntry, DAOGenericInvoice daoGenericInvoice, DAOTax daoTax,
                DAOProduct daoProduct, DAOContext daoContext) {
            super(daoEntry, daoGenericInvoice, daoTax, daoProduct, daoContext);
        }
    }

    Integer getEntryNumber();

    <T extends ShippingPoint> T getShippingOrigin();

    <T extends ShippingPoint> T getShippingDestination();

    <T extends Product> T getProduct();

    BigDecimal getQuantity();

    String getUnitOfMeasure();

    BigDecimal getUnitAmountWithTax();

    BigDecimal getUnitAmountWithoutTax();

    BigDecimal getUnitTaxAmount();

    BigDecimal getUnitDiscountAmount();

    BigDecimal getAmountWithTax();

    BigDecimal getAmountWithoutTax();

    BigDecimal getTaxAmount();

    BigDecimal getDiscountAmount();

    Date getTaxPointDate();

    <T extends GenericInvoice> Collection<T> getDocumentReferences();

    String getDescription();

    CreditOrDebit getCreditOrDebit();

    BigDecimal getShippingCostsAmount();

    Currency getCurrency();

    BigDecimal getExchangeRateToDocumentCurrency();

    <T extends Tax> Collection<T> getTaxes();

    String getTaxExemptionReason();

    String getTaxExemptionCode();

    AmountType getAmountType();

}
