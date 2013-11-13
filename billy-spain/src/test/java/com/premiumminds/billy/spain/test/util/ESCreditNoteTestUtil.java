/**
 * Copyright (C) 2013 Premium Minds.
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
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice.SourceBilling;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice.TYPE;

public class ESCreditNoteTestUtil {

	private static final Boolean		BILLED		= false;
	private static final Boolean		CANCELLED	= false;
	private static final Boolean		SELFBILL	= false;
	private static final String			SOURCEID	= "SOURCE";

	private Injector					injector;
	private ESCreditNoteEntryTestUtil	creditNoteEntry;
	protected ESPaymentTestUtil			payment;

	public ESCreditNoteTestUtil(Injector injector) {
		this.injector = injector;
		this.creditNoteEntry = new ESCreditNoteEntryTestUtil(injector);
		this.payment = new ESPaymentTestUtil(injector);
	}

	public ESCreditNoteEntity getCreditNoteEntity(TYPE type,
			ESInvoiceEntity reference) {

		ESCreditNoteEntity creditNote = (ESCreditNoteEntity) this
				.getCreditNoteBuilder(reference).build();
		creditNote.setType(type);

		ESCreditNoteEntryEntity creditNoteEntry = (ESCreditNoteEntryEntity) creditNote
				.getEntries().get(0);
		creditNoteEntry.getDocumentReferences().add(creditNote);

		return creditNote;
	}

	public ESCreditNote.Builder getCreditNoteBuilder(ESInvoiceEntity reference) {

		ESCreditNote.Builder creditNoteBuilder = this.injector
				.getInstance(ESCreditNote.Builder.class);

		ESCreditNoteEntry.Builder creditNoteEntryBuilder = this.creditNoteEntry
				.getCreditNoteEntryBuilder(reference);

		return creditNoteBuilder
				.setBilled(ESCreditNoteTestUtil.BILLED)
				.setCancelled(ESCreditNoteTestUtil.CANCELLED)
				.setSelfBilled(ESCreditNoteTestUtil.SELFBILL)
				.setDate(new Date()).setSourceId(ESCreditNoteTestUtil.SOURCEID)
				.addEntry(creditNoteEntryBuilder)
				.setBusinessUID(reference.getBusiness().getUID())
				.setSourceBilling(SourceBilling.P)
				.setCustomerUID(reference.getCustomer().getUID())
				.addPayment(payment.getPaymentBuilder());
	}

	public ESCreditNoteEntity getCreditNoteEntity(ESInvoiceEntity reference) {
		return this.getCreditNoteEntity(TYPE.NC, reference);
	}
}
