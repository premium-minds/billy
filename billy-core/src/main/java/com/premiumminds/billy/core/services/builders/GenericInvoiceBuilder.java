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
package com.premiumminds.billy.core.services.builders;

import com.premiumminds.billy.core.persistence.entities.ShippingPointEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.DiscountType;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public interface GenericInvoiceBuilder<TBuilder extends GenericInvoiceBuilder<TBuilder, TEntry, TDocument>, TEntry extends GenericInvoiceEntry, TDocument extends GenericInvoice>
        extends Builder<TDocument> {

    TBuilder setBusinessUID(StringID<Business> businessUID);

    TBuilder setCustomerUID(StringID<Customer> customerUID);

    TBuilder setSupplierUID(StringID<Supplier> supplier);

    TBuilder setOfficeNumber(String number);

    TBuilder setDate(Date date);

    <T extends ShippingPointEntity> TBuilder setShippingOrigin(Builder<T> originBuilder);

    <T extends ShippingPointEntity> TBuilder setShippingDestination(Builder<T> destinationBuilder);

    TBuilder setPaymentTerms(String terms);

    TBuilder setSelfBilled(boolean selfBilled);

    TBuilder setSourceId(String source);

    TBuilder setGeneralLedgerDate(Date date);

    TBuilder setBatchId(String id);

    TBuilder setTransactionId(String id);

    TBuilder addReceiptNumber(String number);

    <T extends GenericInvoiceEntry> TBuilder addEntry(Builder<T> entryBuilder);

    TBuilder setSettlementDescription(String description);

    TBuilder setSettlementDiscount(BigDecimal discount);

    TBuilder setSettlementDate(Date date);

    <T extends Payment> TBuilder addPayment(Builder<T> paymentBuilder);

    TBuilder setCreditOrDebit(GenericInvoice.CreditOrDebit creditOrDebit);

    TBuilder setDiscounts(DiscountType type, BigDecimal... discounts);

    TBuilder setCurrency(Currency currency);

    TBuilder setScale(int scale);

}
