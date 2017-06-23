/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.CostumerData;
import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.PaymentData;

public class PTSimpleInvoiceData extends PTGenericInvoiceData {

  public PTSimpleInvoiceData(String number, Date date, Date settlementDate,
      List<PaymentData> payments, CostumerData customer, BusinessData business,
      List<InvoiceEntryData> entries, BigDecimal taxAmount, BigDecimal amountWithTax,
      BigDecimal amountWithoutTax, String settlementDescription, String hash) {

    super(number, date, settlementDate, payments, customer, business, entries, taxAmount,
        amountWithTax, amountWithoutTax, settlementDescription, hash);
  }

}
