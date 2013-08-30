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

import javassist.expr.Instanceof;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.PortugalBootstrap;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTShippingPoint;
import com.premiumminds.billy.portugal.util.Contexts;

public class PTInvoiceEntryTestUtil {

	private static final BigDecimal	AMOUNT		= new BigDecimal(20);
	private static final Currency	CURRENCY	= Currency.getInstance("EUR");
	private static final BigDecimal	QUANTITY	= new BigDecimal("1");

	private Injector				injector;
	private PTProductTestUtil		product;
	private Contexts				contexts;
	private PTRegionContext			context;
	private PTShippingPointTestUtil	shippingPoint;

	public PTInvoiceEntryTestUtil(Injector injector) {
		this.injector = injector;
		this.product = new PTProductTestUtil(injector);
		this.contexts = new Contexts(injector);
		this.shippingPoint = new PTShippingPointTestUtil(injector);
	}

	public PTInvoiceEntry.Builder getInvoiceEntryBuilder(PTProductEntity product) {
		PTInvoiceEntry.Builder invoiceEntryBuilder = this.injector
				.getInstance(PTInvoiceEntry.Builder.class);
		PTShippingPoint.Builder originBuilder = this.shippingPoint
				.getShippingPointBuilder();
		this.context = this.contexts.portugal().allRegions();

		invoiceEntryBuilder.clear();

		invoiceEntryBuilder
				.setUnitAmount(AmountType.WITH_TAX,
						PTInvoiceEntryTestUtil.AMOUNT)
				.setTaxPointDate(new Date())
				.setCreditOrDebit(CreditOrDebit.DEBIT)
				.setDescription(product.getDescription())
				.setQuantity(PTInvoiceEntryTestUtil.QUANTITY)
				.setUnitOfMeasure(product.getUnitOfMeasure())
				.setProductUID(product.getUID())
				.setContextUID(this.context.getUID())
				.setShippingOrigin(originBuilder)
				.setCurrency(CURRENCY);

		return invoiceEntryBuilder;

	}

	public PTInvoiceEntry.Builder getInvoiceEntryBuilder() {
		PTProductEntity newProduct = this.product.getProductEntity();
		return this.getInvoiceEntryBuilder((PTProductEntity) this.injector
				.getInstance(DAOPTProduct.class).create(newProduct));
	}
	
	public PTInvoiceEntry.Builder getInvoiceOtherRegionsEntryBuilder(String region) {
		PTProductEntity product;
		
		if(region.equals("PT-20")){
			product = (PTProductEntity) this.injector
					.getInstance(DAOPTProduct.class).create(this.product.getOtherRegionProductEntity(region));
			this.context = this.contexts.azores().azores().getParentContext();
		}
		else{
			product = (PTProductEntity) this.injector
					.getInstance(DAOPTProduct.class).create(this.product.getOtherRegionProductEntity(region));
			this.context = this.contexts.madeira().madeira().getParentContext();
		}

		PTInvoiceEntry.Builder invoiceEntryBuilder = this.injector
				.getInstance(PTInvoiceEntry.Builder.class);
		PTShippingPoint.Builder originBuilder = this.shippingPoint
				.getShippingPointBuilder();

		invoiceEntryBuilder.clear();

		invoiceEntryBuilder
				.setUnitAmount(AmountType.WITHOUT_TAX,
						PTInvoiceEntryTestUtil.AMOUNT)
				.setTaxPointDate(new Date())
				.setCreditOrDebit(CreditOrDebit.DEBIT)
				.setDescription(product.getDescription())
				.setQuantity(PTInvoiceEntryTestUtil.QUANTITY)
				.setUnitOfMeasure(product.getUnitOfMeasure())
				.setProductUID(product.getUID())
				.setContextUID(this.context.getUID())
				.setShippingOrigin(originBuilder)
				.setCurrency(Currency.getInstance("EUR"));

		return invoiceEntryBuilder;

	}
}
