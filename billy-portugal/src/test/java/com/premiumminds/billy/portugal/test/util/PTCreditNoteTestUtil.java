/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.portugal.test.util;

import java.util.Currency;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;

public class PTCreditNoteTestUtil {

	private static final Boolean		BILLED		= false;
	private static final Boolean		CANCELLED	= false;
	private static final Boolean		SELFBILL	= false;
	private static final String			SOURCEID	= "SOURCE";

	private Injector					injector;
	private PTCreditNoteEntryTestUtil	creditNoteEntry;

	public PTCreditNoteTestUtil(Injector injector) {
		this.injector = injector;
		this.creditNoteEntry = new PTCreditNoteEntryTestUtil(injector);
	}

	public PTCreditNoteEntity getCreditNoteEntity(TYPE type,
			PTInvoiceEntity reference) {

		PTCreditNoteEntity creditNote = (PTCreditNoteEntity) this
				.getCreditNoteBuilder(reference).build();
		creditNote.setType(type);

		PTCreditNoteEntryEntity creditNoteEntry = (PTCreditNoteEntryEntity) creditNote
				.getEntries().get(0);
		creditNoteEntry.getDocumentReferences().add(creditNote);

		return creditNote;
	}

	public PTCreditNote.Builder getCreditNoteBuilder(PTInvoiceEntity reference) {

		PTCreditNote.Builder creditNoteBuilder = this.injector
				.getInstance(PTCreditNote.Builder.class);

		PTCreditNoteEntry.Builder creditNoteEntryBuilder = this.creditNoteEntry
				.getCreditNoteEntryBuilder(reference);

		return creditNoteBuilder
				.setBilled(PTCreditNoteTestUtil.BILLED)
				.setCancelled(PTCreditNoteTestUtil.CANCELLED)
				.setSelfBilled(PTCreditNoteTestUtil.SELFBILL)
				.setDate(new Date()).setSourceId(PTCreditNoteTestUtil.SOURCEID)
				.addEntry(creditNoteEntryBuilder)
				.setBusinessUID(reference.getBusiness().getUID())
				.setSourceBilling(SourceBilling.P)
				.setCustomerUID(reference.getCustomer().getUID());
	}

	public PTCreditNoteEntity getCreditNoteEntity(PTInvoiceEntity reference) {
		return this.getCreditNoteEntity(TYPE.NC, reference);
	}
}
