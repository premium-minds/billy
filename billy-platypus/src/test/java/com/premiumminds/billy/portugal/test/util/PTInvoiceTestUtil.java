/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.util;

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntryEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;

public class PTInvoiceTestUtil {

	private final Boolean billed = false;
	private final Boolean cancelled = false;
	private final Boolean selfBill = false;
	private final String hash = "HASH";
	private final String sourceID = "SOURCE";
	private final String uid = "INVOICE";
	private final String serie = "A";
	private final String number = "FS A/1";
	private final Integer seriesNumber = 1;
	private final String invoiceEntryUID = "INVOICE_ENTRY";

	private Injector injector;
	private PTInvoiceEntryTestUtil invoiceEntry;

	public PTInvoiceTestUtil(Injector injector) {
		this.injector = injector;
		invoiceEntry = new PTInvoiceEntryTestUtil(injector);

	}

	public PTInvoiceEntity getInvoiceEntity() {
		PTInvoice.Builder invoiceBuilder = injector
				.getInstance(PTInvoice.Builder.class);

		PTInvoiceEntry.Builder invoiceEntryBuilder = invoiceEntry
				.getInvoiceEntryBuilder();

		invoiceBuilder.clear();

		invoiceBuilder.setBilled(billed).setCancelled(cancelled)
				.setSelfBilled(selfBill).setHash(hash).setDate(new Date())
				.setSourceId(sourceID).addEntry(invoiceEntryBuilder);

		PTInvoiceEntity invoice = (PTInvoiceEntity) invoiceBuilder.build();

		invoice.setUID(new UID(uid));
		invoice.setSeries(serie);
		invoice.setSeriesNumber(seriesNumber);
		invoice.setNumber(number);

		PTInvoiceEntryEntity invoiceEntry = (PTInvoiceEntryEntity) invoice
				.getEntries().get(0);
		invoiceEntry.setUID(new UID(invoiceEntryUID));
		invoiceEntry.getDocumentReferences().add(invoice);

		return invoice;
	}
}
