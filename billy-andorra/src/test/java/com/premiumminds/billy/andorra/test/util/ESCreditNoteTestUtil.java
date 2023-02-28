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
import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditNote;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry;

public class ESCreditNoteTestUtil {

    private static final Boolean BILLED = false;
    private static final Boolean CANCELLED = false;
    private static final Boolean SELFBILL = false;
    private static final String SOURCEID = "SOURCE";

    private Injector injector;
    private ESCreditNoteEntryTestUtil creditNoteEntry;
    protected ESPaymentTestUtil payment;

    public ESCreditNoteTestUtil(Injector injector) {
        this.injector = injector;
        this.creditNoteEntry = new ESCreditNoteEntryTestUtil(injector);
        this.payment = new ESPaymentTestUtil(injector);
    }

    public ADCreditNoteEntity getCreditNoteEntity(ADInvoiceEntity reference) {

        ADCreditNoteEntity creditNote = (ADCreditNoteEntity) this.getCreditNoteBuilder(reference).build();

        ADCreditNoteEntryEntity creditNoteEntry = (ADCreditNoteEntryEntity) creditNote.getEntries().get(0);
        creditNoteEntry.getDocumentReferences().add(creditNote);

        return creditNote;
    }

    public ADCreditNote.Builder getCreditNoteBuilder(ADInvoiceEntity reference) {

        ADCreditNote.Builder creditNoteBuilder = this.injector.getInstance(ADCreditNote.Builder.class);

        ADCreditNoteEntry.Builder creditNoteEntryBuilder = this.creditNoteEntry.getCreditNoteEntryBuilder(reference);

        return creditNoteBuilder.setBilled(ESCreditNoteTestUtil.BILLED).setCancelled(ESCreditNoteTestUtil.CANCELLED)
                .setSelfBilled(ESCreditNoteTestUtil.SELFBILL).setDate(new Date())
                .setSourceId(ESCreditNoteTestUtil.SOURCEID).addEntry(creditNoteEntryBuilder)
                .setBusinessUID(reference.getBusiness().getUID()).setCustomerUID(reference.getCustomer().getUID())
                .addPayment(this.payment.getPaymentBuilder());
    }
}
