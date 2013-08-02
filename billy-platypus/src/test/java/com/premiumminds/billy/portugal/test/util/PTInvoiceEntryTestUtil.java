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

import javax.persistence.NoResultException;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTShippingPoint;
import com.premiumminds.billy.portugal.util.Contexts;

public class PTInvoiceEntryTestUtil {

	private static final BigDecimal AMOUNT = new BigDecimal(20);
	private static final Currency CURRENCY = Currency.getInstance("EUR");
	private static final BigDecimal QUANTITY = new BigDecimal(1);

	private Injector injector;
	private PTProductTestUtil product;
	private Contexts contexts;
	private PTRegionContext context;
	private PTShippingPointTestUtil shippingPoint;

	public PTInvoiceEntryTestUtil(Injector injector) {
		this.injector = injector;
		product = new PTProductTestUtil(injector);
		contexts = new Contexts(injector);
		shippingPoint = new PTShippingPointTestUtil(injector);
	}

	public PTInvoiceEntry.Builder getInvoiceEntryBuilder(PTProductEntity product) {
		PTInvoiceEntry.Builder invoiceEntryBuilder = injector
				.getInstance(PTInvoiceEntry.Builder.class);
		PTShippingPoint.Builder originBuilder = shippingPoint
				.getShippingPointBuilder();
		context = contexts.portugal().portugal();

		invoiceEntryBuilder.clear();

		invoiceEntryBuilder
				.setUnitAmount(AmountType.WITH_TAX, AMOUNT, CURRENCY)
				.setTaxPointDate(new Date())
				.setCreditOrDebit(CreditOrDebit.DEBIT)
				.setDescription(product.getDescription()).setQuantity(QUANTITY)
				.setUnitOfMeasure(product.getUnitOfMeasure())
				.setProductUID(product.getUID())
				.setContextUID(context.getUID())
				.setShippingOrigin(originBuilder);

		return invoiceEntryBuilder;

	}

	public PTInvoiceEntry.Builder getInvoiceEntryBuilder() {

		PTProductEntity newProduct = product.getProductEntity();
		return getInvoiceEntryBuilder(newProduct);
	}

	public PTInvoiceEntry.Builder getInvoiceEntryBuilder(String productUID) {
		DAOPTProduct daoPTProduct = injector.getInstance(DAOPTProduct.class);
		PTProductEntity newProduct = null;
		try {
			newProduct = (PTProductEntity) daoPTProduct
					.get(new UID(productUID));
		} catch (NoResultException e) {
		}

		if (newProduct == null) {
			newProduct = product.getProductEntity(productUID);
			daoPTProduct.create(newProduct);
		}
		return getInvoiceEntryBuilder(newProduct);
	}
}
