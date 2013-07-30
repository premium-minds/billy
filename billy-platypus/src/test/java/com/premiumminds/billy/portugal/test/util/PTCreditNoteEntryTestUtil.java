/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
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

	private final String product = "PRODUCT_UID";
	private final String invoiceReference = "INVOICE";
	private final BigDecimal amount = new BigDecimal(20);
	private final Currency currency = Currency.getInstance("EUR");
	private final BigDecimal quantity = new BigDecimal(1);
	private final String reason = "Rotten potatoes";

	private Injector injector;
	private Contexts contexts;
	private PTRegionContext context;

	public PTCreditNoteEntryTestUtil(Injector injector) {
		this.injector = injector;
		contexts = new Contexts(injector);
	}

	public PTCreditNoteEntry.Builder getCreditNoteEntryBuilder() {
		PTCreditNoteEntry.Builder creditNoteEntryBuilder = injector
				.getInstance(PTCreditNoteEntry.Builder.class);

		final PTProductEntity newProduct = (PTProductEntity) injector
				.getInstance(DAOPTProduct.class).get(new UID(product));
		final PTInvoiceEntity reference = (PTInvoiceEntity) injector
				.getInstance(DAOPTInvoice.class).get(new UID(invoiceReference));
		context = contexts.portugal().portugal();

		creditNoteEntryBuilder.clear();

		creditNoteEntryBuilder
				.setUnitAmount(AmountType.WITH_TAX, amount, currency)
				.setTaxPointDate(new Date())
				.setCreditOrDebit(CreditOrDebit.DEBIT)
				.setDescription(newProduct.getDescription())
				.setQuantity(quantity)
				.setUnitOfMeasure(newProduct.getUnitOfMeasure())
				.setProductUID(newProduct.getUID())
				.setContextUID(context.getUID()).setReason(reason)
				.setReference(reference);

		return creditNoteEntryBuilder;
	}
}
