/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.entities.ESReceipt;
import com.premiumminds.billy.spain.services.entities.ESReceiptEntry;

public class ESReceiptTestUtil {
  protected static final Boolean BILLED = false;
  protected static final Boolean CANCELLED = false;
  protected static final Boolean SELFBILL = false;
  protected static final String SOURCE_ID = "SOURCE";
  protected static final String SERIES = "A";
  protected static final Integer SERIES_NUMBER = 1;
  protected static final int MAX_PRODUCTS = 5;

  protected final Injector injector;
  protected final ESReceiptEntryTestUtil receiptEntry;
  protected final ESBusinessTestUtil businesses;
  protected final ESPaymentTestUtil payments;

  public ESReceiptTestUtil(Injector injector) {
    this.injector = injector;
    receiptEntry = new ESReceiptEntryTestUtil(injector);
    businesses = new ESBusinessTestUtil(injector);
    payments = new ESPaymentTestUtil(injector);
  }

  public ESReceiptEntity getReceiptEntity() {
    return (ESReceiptEntity) getReceiptBuilder(businesses.getBusinessEntity()).build();
  }

  public ESReceipt.Builder getReceiptBuilder(ESBusinessEntity business) {
    ESReceipt.Builder receiptBuilder = injector.getInstance(ESReceipt.Builder.class);
    ESReceiptEntry.Builder entryBuilder = receiptEntry.getReceiptEntryBuilder()
        .setUnitAmount(AmountType.WITH_TAX, new BigDecimal("0.45"));

    return receiptBuilder.setBilled(BILLED).setCancelled(CANCELLED).setSelfBilled(SELFBILL)
        .setSourceId(SOURCE_ID).setDate(new Date()).setBusinessUID(business.getUID())
        .addPayment(payments.getPaymentBuilder()).addEntry(entryBuilder);
  }
}
