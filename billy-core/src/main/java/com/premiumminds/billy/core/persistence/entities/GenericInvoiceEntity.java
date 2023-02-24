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
package com.premiumminds.billy.core.persistence.entities;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;

public interface GenericInvoiceEntity extends GenericInvoice {

    void setSeriesNumber(Integer seriesNumber);

    void setNumber(String number);

    void setSeries(String series);

    <T extends Business> void setBusiness(T business);

    <T extends CustomerEntity> void setCustomer(T customer);

    <T extends SupplierEntity> void setSupplier(T supplier);

    void setOfficeNumber(String number);

    void setDate(Date date);

    void setAmountWithTax(BigDecimal amount);

    void setAmountWithoutTax(BigDecimal amount);

    void setTaxAmount(BigDecimal amount);

    void setDiscountsAmount(BigDecimal amount);

    <T extends ShippingPointEntity> void setShippingOrigin(T origin);

    <T extends ShippingPointEntity> void setShippingDestination(T destination);

    void setPaymentTerms(String terms);

    void setSelfBilled(Boolean selfBilled);

    void setSourceId(String source);

    void setGeneralLedgerDate(Date date);

    void setBatchId(String id);

    void setTransactionId(String id);

    @Override List<String> getReceiptNumbers();

    @Override <T extends GenericInvoiceEntry> List<T> getEntries();

    void setCurrency(Currency currency);

    void setSettlementDescription(String description);

    void setSettlementDiscount(BigDecimal discount);

    void setSettlementDate(Date date);

    @Override <T extends Payment> List<T> getPayments();

    void setCreditOrDebit(CreditOrDebit creditOrDebit);

    void setScale(Integer scale);

}
