/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.services.export;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.PaymentData;
import java.util.Optional;

public class ADReceiptData extends GenericInvoiceData {

    public ADReceiptData(
        final String number,
        final Date date,
        final Optional<LocalDate> localDate,
        final Date settlementDate,
        final List<PaymentData> payments,
        final BusinessData business,
        final List<InvoiceEntryData> entries,
        final BigDecimal taxAmount,
        final BigDecimal amountWithTax,
        final BigDecimal amountWithoutTax,
        final String settlementDescription) {

        super(number, date, localDate, settlementDate, payments, null, business, entries, taxAmount, amountWithTax,
              amountWithoutTax, settlementDescription);
    }

}
