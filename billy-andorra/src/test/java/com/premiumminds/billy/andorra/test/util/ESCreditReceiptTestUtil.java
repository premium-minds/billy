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

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntryEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditReceipt;
import com.premiumminds.billy.andorra.services.entities.ADCreditReceiptEntry;

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

    public ADCreditReceiptEntity getCreditReceiptEntity(ADReceiptEntity reference) {

        ADCreditReceiptEntity creditReceipt = (ADCreditReceiptEntity) this.getCreditReceiptBuilder(reference).build();

        ADCreditReceiptEntryEntity creditReceiptEntry = (ADCreditReceiptEntryEntity) creditReceipt.getEntries().get(0);
        creditReceiptEntry.getDocumentReferences().add(creditReceipt);

        return creditReceipt;
    }

    public ADCreditReceipt.Builder getCreditReceiptBuilder(ADReceiptEntity reference) {

        ADCreditReceipt.Builder creditReceiptBuilder = this.injector.getInstance(ADCreditReceipt.Builder.class);

        ADCreditReceiptEntry.Builder creditReceiptEntryBuilder =
                this.creditReceiptEntry.getCreditReceiptEntryBuilder(reference);

        return creditReceiptBuilder.setBilled(ESCreditReceiptTestUtil.BILLED)
                .setCancelled(ESCreditReceiptTestUtil.CANCELLED).setSelfBilled(ESCreditReceiptTestUtil.SELFBILL)
                .setDate(new Date()).setSourceId(ESCreditReceiptTestUtil.SOURCEID).addEntry(creditReceiptEntryBuilder)
                .setBusinessUID(reference.getBusiness().getUID()).addPayment(this.payment.getPaymentBuilder());
    }
}
