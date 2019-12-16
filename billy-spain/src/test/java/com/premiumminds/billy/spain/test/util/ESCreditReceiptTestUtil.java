/*
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

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditReceipt;
import com.premiumminds.billy.spain.services.entities.ESCreditReceiptEntry;

public class ESCreditReceiptTestUtil {

    private static final Boolean BILLED = false;
    private static final Boolean CANCELLED = false;
    private static final Boolean SELFBILL = false;
    private static final String SOURCEID = "SOURCE";

    private Injector injector;
    private ESCreditReceiptEntryTestUtil creditReceiptEntry;
    protected ESPaymentTestUtil payment;

    public ESCreditReceiptTestUtil(Injector injector) {
        this.injector = injector;
        this.creditReceiptEntry = new ESCreditReceiptEntryTestUtil(injector);
        this.payment = new ESPaymentTestUtil(injector);
    }

    public ESCreditReceiptEntity getCreditReceiptEntity(ESReceiptEntity reference) {

        ESCreditReceiptEntity creditReceipt = (ESCreditReceiptEntity) this.getCreditReceiptBuilder(reference).build();

        ESCreditReceiptEntryEntity creditReceiptEntry = (ESCreditReceiptEntryEntity) creditReceipt.getEntries().get(0);
        creditReceiptEntry.getDocumentReferences().add(creditReceipt);

        return creditReceipt;
    }

    public ESCreditReceipt.Builder getCreditReceiptBuilder(ESReceiptEntity reference) {

        ESCreditReceipt.Builder creditReceiptBuilder = this.injector.getInstance(ESCreditReceipt.Builder.class);

        ESCreditReceiptEntry.Builder creditReceiptEntryBuilder =
                this.creditReceiptEntry.getCreditReceiptEntryBuilder(reference);

        return creditReceiptBuilder.setBilled(ESCreditReceiptTestUtil.BILLED)
                .setCancelled(ESCreditReceiptTestUtil.CANCELLED).setSelfBilled(ESCreditReceiptTestUtil.SELFBILL)
                .setDate(new Date()).setSourceId(ESCreditReceiptTestUtil.SOURCEID).addEntry(creditReceiptEntryBuilder)
                .setBusinessUID(reference.getBusiness().getUID()).addPayment(this.payment.getPaymentBuilder());
    }
}
