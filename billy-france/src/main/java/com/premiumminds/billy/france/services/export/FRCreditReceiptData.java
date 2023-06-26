/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.services.export;

import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.export.PaymentData;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FRCreditReceiptData extends GenericInvoiceData {

    private final List<FRCreditReceiptEntryData> entries;

    public FRCreditReceiptData(
        final String number,
        final LocalDate localDate,
        final Date settlementDate,
        final List<PaymentData> payments,
        final BusinessData business,
        final List<FRCreditReceiptEntryData> entries,
        final BigDecimal taxAmount,
        final BigDecimal amountWithTax,
        final BigDecimal amountWithoutTax,
        final String settlementDescription) {

        super(number, localDate, settlementDate, payments, null, business, new ArrayList<>(0),
              taxAmount, amountWithTax, amountWithoutTax, settlementDescription);

        this.entries = entries;
    }

    @Override
    public List<FRCreditReceiptEntryData> getEntries() {
        return this.entries;
    }

}
