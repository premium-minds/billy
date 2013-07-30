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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.util.Contexts;

public class PTInvoiceEntryTestUtil {

	private final BigDecimal amount = new BigDecimal(20);
	private final Currency currency = Currency.getInstance("EUR");
	private final BigDecimal quantity = new BigDecimal(1);

	private Injector injector;
	private PTProductTestUtil product;
	private Contexts contexts;
	private PTRegionContext context;

	public PTInvoiceEntryTestUtil(Injector injector) {
		this.injector = injector;
		product = new PTProductTestUtil(injector);
		contexts = new Contexts(injector);
	}

	public PTInvoiceEntry.Builder getInvoiceEntryBuilder() {
		PTInvoiceEntry.Builder invoiceEntryBuilder = injector
				.getInstance(PTInvoiceEntry.Builder.class);
		DAOPTProduct daoPTProduct = injector.getInstance(DAOPTProduct.class);
		context = contexts.portugal().portugal();

		PTProductEntity newProduct = product.buildProduct();
		daoPTProduct.create(newProduct);

		invoiceEntryBuilder.clear();

		invoiceEntryBuilder
				.setUnitAmount(AmountType.WITH_TAX, amount, currency)
				.setTaxPointDate(new Date())
				.setCreditOrDebit(CreditOrDebit.DEBIT)
				.setDescription(newProduct.getDescription())
				.setQuantity(quantity)
				.setUnitOfMeasure(newProduct.getUnitOfMeasure())
				.setProductUID(newProduct.getUID())
				.setContextUID(context.getUID());

		return invoiceEntryBuilder;
	}
}
