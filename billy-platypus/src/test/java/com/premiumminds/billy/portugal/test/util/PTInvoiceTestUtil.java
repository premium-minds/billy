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
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;

public class PTInvoiceTestUtil {

	private static final Date DATE = new Date();
	private static final Boolean BILLED = false;
	private static final Boolean CANCELLED = false;
	private static final Boolean SELFBILL = false;
	private static final String HASH = "HASH";
	private static final String SOURCE_ID = "SOURCE";
	private static final String UID = "INVOICE";
	private static final String SERIE = "A";
	private static final Integer SERIE_NUMBER = 1;
	private static final String INVOICE_ENTRY_UID = "INVOICE_ENTRY";
	private static final String PRODUCT_UID = "PRODUCT_UID";
	private static final TYPE INVOICE_TYPE = TYPE.FT;

	private Injector injector;
	private PTInvoiceEntryTestUtil invoiceEntry;

	public PTInvoiceTestUtil(Injector injector) {
		this.injector = injector;
		invoiceEntry = new PTInvoiceEntryTestUtil(injector);

	}

	public PTInvoiceEntity getInvoiceEntity() {
		return getInvoiceEntity(INVOICE_TYPE, SERIE, UID, SERIE_NUMBER,
				INVOICE_ENTRY_UID, PRODUCT_UID);
	}

	public PTInvoiceEntity getInvoiceEntity(String productUID) {
		return getInvoiceEntity(INVOICE_TYPE, SERIE, UID, SERIE_NUMBER,
				INVOICE_ENTRY_UID, productUID);
	}

	public PTInvoiceEntity getInvoiceEntity(TYPE invoiceType, String serie,
			String uid, Integer seriesNumber, String entryUID, String productUID) {
		PTInvoiceEntity invoice = getSimpleInvoiceEntity(invoiceType,
				productUID, entryUID, uid);

		String formatedNumber = invoiceType.toString() + " " + serie + "/"
				+ seriesNumber;

		invoice.setSeries(serie);
		invoice.setSeriesNumber(seriesNumber);
		invoice.setNumber(formatedNumber);

		return invoice;
	}

	public PTInvoiceEntity getSimpleInvoiceEntity(TYPE invoiceType,
			String productUID, String entryUID, String uid) {
		PTInvoice.Builder invoiceBuilder = injector
				.getInstance(PTInvoice.Builder.class);

		PTInvoiceEntry.Builder invoiceEntryBuilder = invoiceEntry
				.getInvoiceEntryBuilder(productUID);

		invoiceBuilder.clear();

		invoiceBuilder.setBilled(BILLED).setCancelled(CANCELLED)
				.setSelfBilled(SELFBILL).setHash(HASH).setDate(DATE)
				.setSourceId(SOURCE_ID).addEntry(invoiceEntryBuilder);

		PTInvoiceEntity invoice = (PTInvoiceEntity) invoiceBuilder.build();
		invoice.setUID(new UID(uid));
		invoice.setType(invoiceType);

		PTInvoiceEntryEntity invoiceEntry = (PTInvoiceEntryEntity) invoice
				.getEntries().get(0);
		invoiceEntry.setUID(new UID(entryUID));
		invoiceEntry.getDocumentReferences().add(invoice);

		return invoice;
	}
}
