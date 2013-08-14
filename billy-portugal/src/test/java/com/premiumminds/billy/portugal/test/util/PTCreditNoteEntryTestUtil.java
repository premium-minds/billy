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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.util.Contexts;

public class PTCreditNoteEntryTestUtil {

	private static final String PRODUCT = "PRODUCT_UID";
	private static final String INVOICEREFERENCE = "INVOICE";
	private static final BigDecimal AMOUNT = new BigDecimal(20);
	private static final Currency CURRENCY = Currency.getInstance("EUR");
	private static final BigDecimal QUANTITY = new BigDecimal(1);
	private static final String REASON = "Rotten potatoes";

	private Injector injector;
	private Contexts contexts;
	private PTRegionContext context;

	public PTCreditNoteEntryTestUtil(Injector injector) {
		this.injector = injector;
		this.contexts = new Contexts(injector);
	}

	public PTCreditNoteEntry.Builder getCreditNoteEntryBuilder(
			String productUID, String invoiceReference) {
		PTCreditNoteEntry.Builder creditNoteEntryBuilder = this.injector
				.getInstance(PTCreditNoteEntry.Builder.class);

		final PTProductEntity newProduct = (PTProductEntity) this.injector
				.getInstance(DAOPTProduct.class).get(new UID(productUID));
		final PTInvoiceEntity reference = (PTInvoiceEntity) this.injector
				.getInstance(DAOPTInvoice.class).get(new UID(invoiceReference));
		this.context = this.contexts.portugal().portugal();

		creditNoteEntryBuilder.clear();

		creditNoteEntryBuilder
				.setUnitAmount(AmountType.WITH_TAX,
						PTCreditNoteEntryTestUtil.AMOUNT,
						PTCreditNoteEntryTestUtil.CURRENCY)
				.setTaxPointDate(new Date())
				.setCreditOrDebit(CreditOrDebit.DEBIT)
				.setDescription(newProduct.getDescription())
				.setQuantity(PTCreditNoteEntryTestUtil.QUANTITY)
				.setUnitOfMeasure(newProduct.getUnitOfMeasure())
				.setProductUID(newProduct.getUID())
				.setContextUID(this.context.getUID())
				.setReason(PTCreditNoteEntryTestUtil.REASON)
				.setReferenceUID(reference.getUID());

		return creditNoteEntryBuilder;
	}

	public PTCreditNoteEntry.Builder getCreditNoteEntryBuilder() {
		return this.getCreditNoteEntryBuilder(
				PTCreditNoteEntryTestUtil.PRODUCT,
				PTCreditNoteEntryTestUtil.INVOICEREFERENCE);
	}
}
