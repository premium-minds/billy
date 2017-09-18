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

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntryEntity;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.entities.FRCreditReceipt;
import com.premiumminds.billy.france.services.entities.FRCreditReceiptEntry;

public class FRCreditReceiptTestUtil {

    private static final Boolean BILLED = false;
    private static final Boolean CANCELLED = false;
    private static final Boolean SELFBILL = false;
    private static final String SOURCEID = "SOURCE";

    private Injector injector;
    private FRCreditReceiptEntryTestUtil creditReceiptEntry;
    protected FRPaymentTestUtil payment;

    public FRCreditReceiptTestUtil(Injector injector) {
        this.injector = injector;
        this.creditReceiptEntry = new FRCreditReceiptEntryTestUtil(injector);
        this.payment = new FRPaymentTestUtil(injector);
    }

    public FRCreditReceiptEntity getCreditReceiptEntity(FRReceiptEntity reference) {

        FRCreditReceiptEntity creditReceipt = (FRCreditReceiptEntity) this.getCreditReceiptBuilder(reference).build();

        FRCreditReceiptEntryEntity creditReceiptEntry = (FRCreditReceiptEntryEntity) creditReceipt.getEntries().get(0);
        creditReceiptEntry.getDocumentReferences().add(creditReceipt);

        return creditReceipt;
    }

    public FRCreditReceipt.Builder getCreditReceiptBuilder(FRReceiptEntity reference) {

        FRCreditReceipt.Builder creditReceiptBuilder = this.injector.getInstance(FRCreditReceipt.Builder.class);

        FRCreditReceiptEntry.Builder creditReceiptEntryBuilder =
                this.creditReceiptEntry.getCreditReceiptEntryBuilder(reference);

        return creditReceiptBuilder.setBilled(FRCreditReceiptTestUtil.BILLED)
                .setCancelled(FRCreditReceiptTestUtil.CANCELLED).setSelfBilled(FRCreditReceiptTestUtil.SELFBILL)
                .setDate(new Date()).setSourceId(FRCreditReceiptTestUtil.SOURCEID).addEntry(creditReceiptEntryBuilder)
                .setBusinessUID(reference.getBusiness().getUID()).addPayment(this.payment.getPaymentBuilder());
    }
}
