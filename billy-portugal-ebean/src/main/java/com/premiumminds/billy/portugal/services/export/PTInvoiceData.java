/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export;

import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.CostumerData;
import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.PaymentData;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PTInvoiceData extends PTGenericInvoiceData {

    public PTInvoiceData(
        final String number,
        final Date date,
        final Date settlementDate,
        final List<PaymentData> payments,
        final CostumerData customer,
        final BusinessData business,
        final List<InvoiceEntryData> entries,
        final BigDecimal taxAmount,
        final BigDecimal amountWithTax,
        final BigDecimal amountWithoutTax,
        final String settlementDescription,
        final String hash,
        final String qrCodeString) {

        super(number, date, settlementDate, payments, customer, business, entries, taxAmount, amountWithTax,
                amountWithoutTax, settlementDescription, hash, qrCodeString);
    }

}
