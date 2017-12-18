/**
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
package com.premiumminds.billy.france.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.entities.FRReceipt;
import com.premiumminds.billy.france.services.entities.FRReceiptEntry;

public class FRReceiptTestUtil {

    protected static final Boolean BILLED = false;
    protected static final Boolean CANCELLED = false;
    protected static final Boolean SELFBILL = false;
    protected static final String SOURCE_ID = "SOURCE";
    protected static final String SERIES = "A";
    protected static final Integer SERIES_NUMBER = 1;
    protected static final int MAX_PRODUCTS = 5;

    protected final Injector injector;
    protected final FRReceiptEntryTestUtil receiptEntry;
    protected final FRBusinessTestUtil businesses;
    protected final FRPaymentTestUtil payments;

    public FRReceiptTestUtil(Injector injector) {
        this.injector = injector;
        this.receiptEntry = new FRReceiptEntryTestUtil(injector);
        this.businesses = new FRBusinessTestUtil(injector);
        this.payments = new FRPaymentTestUtil(injector);
    }

    public FRReceiptEntity getReceiptEntity() {
        return (FRReceiptEntity) this.getReceiptBuilder(this.businesses.getBusinessEntity()).build();
    }

    public FRReceipt.Builder getReceiptBuilder(FRBusinessEntity business) {
        FRReceipt.Builder receiptBuilder = this.injector.getInstance(FRReceipt.Builder.class);
        FRReceiptEntry.Builder entryBuilder =
                this.receiptEntry.getReceiptEntryBuilder().setUnitAmount(AmountType.WITH_TAX, new BigDecimal("0.45"));

        return receiptBuilder.setBilled(FRReceiptTestUtil.BILLED).setCancelled(FRReceiptTestUtil.CANCELLED)
                .setSelfBilled(FRReceiptTestUtil.SELFBILL).setSourceId(FRReceiptTestUtil.SOURCE_ID).setDate(new Date())
                .setBusinessUID(business.getUID()).addPayment(this.payments.getPaymentBuilder()).addEntry(entryBuilder);
    }
}
