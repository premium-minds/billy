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
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntity;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntryEntity;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRCreditNote;
import com.premiumminds.billy.france.services.entities.FRCreditNoteEntry;

public class FRCreditNoteTestUtil {

    private static final Boolean BILLED = false;
    private static final Boolean CANCELLED = false;
    private static final Boolean SELFBILL = false;
    private static final String SOURCEID = "SOURCE";

    private Injector injector;
    private FRCreditNoteEntryTestUtil creditNoteEntry;
    protected FRPaymentTestUtil payment;

    public FRCreditNoteTestUtil(Injector injector) {
        this.injector = injector;
        this.creditNoteEntry = new FRCreditNoteEntryTestUtil(injector);
        this.payment = new FRPaymentTestUtil(injector);
    }

    public FRCreditNoteEntity getCreditNoteEntity(FRInvoiceEntity reference) {

        FRCreditNoteEntity creditNote = (FRCreditNoteEntity) this.getCreditNoteBuilder(reference).build();

        FRCreditNoteEntryEntity creditNoteEntry = (FRCreditNoteEntryEntity) creditNote.getEntries().get(0);
        creditNoteEntry.getDocumentReferences().add(creditNote);

        return creditNote;
    }

    public FRCreditNote.Builder getCreditNoteBuilder(FRInvoiceEntity reference) {

        FRCreditNote.Builder creditNoteBuilder = this.injector.getInstance(FRCreditNote.Builder.class);

        FRCreditNoteEntry.Builder creditNoteEntryBuilder = this.creditNoteEntry.getCreditNoteEntryBuilder(reference);

        return creditNoteBuilder.setBilled(FRCreditNoteTestUtil.BILLED).setCancelled(FRCreditNoteTestUtil.CANCELLED)
                .setSelfBilled(FRCreditNoteTestUtil.SELFBILL).setDate(new Date())
                .setSourceId(FRCreditNoteTestUtil.SOURCEID).addEntry(creditNoteEntryBuilder)
                .setBusinessUID(reference.getBusiness().getUID()).setCustomerUID(reference.getCustomer().getUID())
                .addPayment(this.payment.getPaymentBuilder());
    }
}
