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
package com.premiumminds.billy.portugal.test.services.jpa;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.PlatypusBootstrap;
import com.premiumminds.billy.portugal.PlatypusDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PlatypusTestPersistenceDependencyModule;
import com.premiumminds.billy.portugal.util.Contexts;
import com.premiumminds.billy.portugal.util.Taxes;

public class TestJPAPTInvoice extends PTAbstractTest {

	@Test
	public void doTest() {
		Injector injector = Guice.createInjector(
				new PlatypusDependencyModule(),
				new PlatypusTestPersistenceDependencyModule());
		injector.getInstance(PlatypusDependencyModule.Initializer.class);
		injector.getInstance(PlatypusTestPersistenceDependencyModule.Initializer.class);
		execute(injector);
		// assert
	}

	public static void execute(final Injector injector) {
		PlatypusBootstrap.execute(injector);
		DAO<?> dao = injector.getInstance(DAOPTInvoice.class);
		final Config configuration = new Config();
		final Taxes taxes = new Taxes(injector);
		final Contexts contexts = new Contexts(injector);

		try {
			new TransactionWrapper<Void>(dao) {

				@SuppressWarnings("unused")
				@Override
				public Void runTransaction() throws Exception {
					DAOPTInvoiceEntry daoPTInvoiceEntry = injector
							.getInstance(DAOPTInvoiceEntry.class);
					DAOPTInvoice daoPTInvoice = injector
							.getInstance(DAOPTInvoice.class);
					DAOPTProduct daoPTProduct = injector
							.getInstance(DAOPTProduct.class);

					PTInvoiceEntry.Builder invoiceEntryBuilder = injector
							.getInstance(PTInvoiceEntry.Builder.class);
					PTInvoice.Builder invoiceBuilder = injector
							.getInstance(PTInvoice.Builder.class);
					PTProduct.Builder productBuilder = injector
							.getInstance(PTProduct.Builder.class);

					final PTRegionContextEntity CONTEXT_PORTUGAL = (PTRegionContextEntity) contexts
							.portugal().portugal();

					final PTRegionContextEntity CONTEXT_CONTINENTAL_PORTUGAL = (PTRegionContextEntity) contexts
							.continent().continent();

					final PTTaxEntity VAT_NORMAL_CONTINENTAL_PORTUGAL = (PTTaxEntity) taxes
							.continent().normal();

					final PTProductEntity PRODUCT_PORTUGAL = this.buildProduct(
							daoPTProduct, productBuilder,
							VAT_NORMAL_CONTINENTAL_PORTUGAL.getUID());

					final PTInvoiceEntity INVOICE_ENTITY = this.buildInvoice(
							daoPTInvoice, invoiceBuilder, invoiceEntryBuilder,
							new UID("POTATOES"), daoPTInvoiceEntry,
							CONTEXT_PORTUGAL.getUID());

					return null;
				}

				private PTProductEntity buildProduct(DAOPTProduct daoPTProduct,
						PTProduct.Builder productBuilder, UID taxUID) {
					productBuilder.clear();
					productBuilder.addTaxUID(taxUID).setNumberCode("123")
							.setUnitOfMeasure("Kg").setProductCode("123")
							.setDescription("description")
							.setType(ProductType.GOODS);

					PTProductEntity product = (PTProductEntity) productBuilder
							.build();

					product.setUID(new UID("POTATOES"));

					daoPTProduct.create(product);

					return product;
				}

				private PTInvoiceEntity buildInvoice(DAOPTInvoice daoPTInvoice,
						PTInvoice.Builder invoiceBuilder,
						PTInvoiceEntry.Builder invoiceEntryBuilder,
						UID productUID, DAOPTInvoiceEntry daoPTInvoiceEntry,
						UID contextUID) {

					buidInvoiceEntry(invoiceEntryBuilder, productUID,
							contextUID);

					invoiceBuilder.clear();

					invoiceBuilder.setBilled(false).setCancelled(false)
							.setSelfBilled(false).setHash("HASH")
							.setDate(new Date()).setSourceId("EU")
							.addEntry(invoiceEntryBuilder);

					PTInvoiceEntity invoice = (PTInvoiceEntity) invoiceBuilder
							.build();

					invoice.setUID(new UID("INVOICE"));
					invoice.setSeries("A");
					invoice.setSeriesNumber(1);
					invoice.setNumber("FS A/1");

					PTInvoiceEntryEntity invoiceEntry = (PTInvoiceEntryEntity) invoice
							.getEntries().get(0);
					invoiceEntry.setUID(new UID("INVOICE_ENTRY"));
					invoiceEntry.getDocumentReferences().add(invoice);
					daoPTInvoice.create(invoice);

					return invoice;
				}

				private void buidInvoiceEntry(
						PTInvoiceEntry.Builder invoiceEntryBuilder,
						UID productUID, UID contextUID) {
					invoiceEntryBuilder.clear();

					invoiceEntryBuilder
							.setUnitAmount(AmountType.WITH_TAX,
									new BigDecimal(20),
									Currency.getInstance("EUR"))
							.setTaxPointDate(new Date())
							.setCreditOrDebit(CreditOrDebit.DEBIT)
							.setDescription("Description")
							.setQuantity(new BigDecimal(1))
							.setUnitOfMeasure("Kg").setProductUID(productUID)
							.setContextUID(contextUID);
				}

			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
