/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy GIN.
 *
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.export;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class GenericInvoiceData {

    private final String number;
    private final LocalDate localDate;
    private final Date settlementDate;
    private final List<PaymentData> payments;
    private final CostumerData customer;
    private final BusinessData business;
    private final List<InvoiceEntryData> entries;
    private final BigDecimal taxAmount;
    private final BigDecimal amountWithTax;
    private final BigDecimal amountWithoutTax;
    private final String settlementDescription;

    public GenericInvoiceData(
        final String number,
        final LocalDate localDate,
        final Date settlementDate,
        final List<PaymentData> payments,
        final CostumerData customer,
        final BusinessData business,
        final List<InvoiceEntryData> entries,
        final BigDecimal taxAmount,
        final BigDecimal amountWithTax,
        final BigDecimal amountWithoutTax,
        final String settlementDescription) {

        this.number = number;
        this.localDate = localDate;
        this.settlementDate = settlementDate;
        this.payments = payments;
        this.customer = customer;
        this.business = business;
        this.entries = entries;
        this.taxAmount = taxAmount;
        this.amountWithTax = amountWithTax;
        this.amountWithoutTax = amountWithoutTax;
        this.settlementDescription = settlementDescription;
    }

    public String getNumber() {
        return this.number;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public Date getSettlementDate() {
        return this.settlementDate;
    }

    public List<PaymentData> getPayments() {
        return this.payments;
    }

    public CostumerData getCustomer() {
        return this.customer;
    }

    public BusinessData getBusiness() {
        return this.business;
    }

    public List<? extends InvoiceEntryData> getEntries() {
        return this.entries;
    }

    public BigDecimal getTaxAmount() {
        return this.taxAmount;
    }

    public BigDecimal getAmountWithTax() {
        return this.amountWithTax;
    }

    public BigDecimal getAmountWithoutTax() {
        return this.amountWithoutTax;
    }

    public String getSettlementDescription() {
        return this.settlementDescription;
    }

}
