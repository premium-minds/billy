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
package com.premiumminds.billy.andorra.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.andorra.persistence.entities.ADBusinessEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.services.entities.ADReceipt;
import com.premiumminds.billy.andorra.services.entities.ADReceiptEntry;

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
        this.receiptEntry = new ESReceiptEntryTestUtil(injector);
        this.businesses = new ESBusinessTestUtil(injector);
        this.payments = new ESPaymentTestUtil(injector);
    }

    public ADReceiptEntity getReceiptEntity() {
        return (ADReceiptEntity) this.getReceiptBuilder(this.businesses.getBusinessEntity()).build();
    }

    public ADReceipt.Builder getReceiptBuilder(ADBusinessEntity business) {
        ADReceipt.Builder receiptBuilder = this.injector.getInstance(ADReceipt.Builder.class);
        ADReceiptEntry.Builder entryBuilder =
                this.receiptEntry.getReceiptEntryBuilder().setUnitAmount(AmountType.WITH_TAX, new BigDecimal("0.45"));

        return receiptBuilder.setBilled(ESReceiptTestUtil.BILLED).setCancelled(ESReceiptTestUtil.CANCELLED)
                .setSelfBilled(ESReceiptTestUtil.SELFBILL).setSourceId(ESReceiptTestUtil.SOURCE_ID).setDate(new Date())
                .setBusinessUID(business.getUID()).addPayment(this.payments.getPaymentBuilder()).addEntry(entryBuilder)
                .setCreditOrDebit(GenericInvoice.CreditOrDebit.CREDIT);
    }
}
