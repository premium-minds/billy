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
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.PlatypusDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PlatypusTestPersistenceDependencyModule;
import com.premiumminds.billy.portugal.util.Contexts;
import com.premiumminds.billy.portugal.util.Taxes;

public class TestJPAPTCreditNote extends PTAbstractTest {

	@Test(expected = com.premiumminds.billy.core.exceptions.BillyValidationException.class)
	public void doTest() throws Exception {
		Injector injector = Guice.createInjector(
				new PlatypusDependencyModule(),
				new PlatypusTestPersistenceDependencyModule());
		injector.getInstance(PlatypusDependencyModule.Initializer.class);
		injector.getInstance(PlatypusTestPersistenceDependencyModule.Initializer.class);
		TestJPAPTInvoice.execute(injector);
		execute(injector);
		execute(injector);
		// assert
	}

	public static void execute(final Injector injector) throws Exception {
		DAO<?> dao = injector.getInstance(DAOPTInvoice.class);
		final Taxes taxes = new Taxes(injector);
		final Contexts contexts = new Contexts(injector);

		try {
			new TransactionWrapper<Void>(dao) {

				@SuppressWarnings("unused")
				@Override
				public Void runTransaction() throws Exception {
					DAOPTCreditNote daoPTCreditNote = injector
							.getInstance(DAOPTCreditNote.class);
					DAOPTCreditNoteEntry daoPTCreditNoteEntry = injector
							.getInstance(DAOPTCreditNoteEntry.class);

					PTCreditNote.Builder creditNoteBuilder = injector
							.getInstance(PTCreditNote.Builder.class);
					PTCreditNoteEntry.Builder creditNoteEntryBuilder = injector
							.getInstance(PTCreditNoteEntry.Builder.class);

					final PTRegionContextEntity CONTEXT_PORTUGAL = (PTRegionContextEntity) contexts
							.portugal().portugal();

					final PTTaxEntity VAT_NORMAL_CONTINENTAL_PORTUGAL = (PTTaxEntity) taxes
							.continent().normal();

					final PTProductEntity PRODUCT_PORTUGAL = (PTProductEntity) injector
							.getInstance(DAOPTProduct.class).get(
									new UID("POTATOES"));

					final PTInvoiceEntity INVOICE_ENTITY = (PTInvoiceEntity) injector
							.getInstance(DAOPTInvoice.class).get(
									new UID("INVOICE"));

					final PTCreditNoteEntity CREDIT_NOTE_ENTITY = this
							.buildCreditNote(daoPTCreditNote,
									creditNoteBuilder, creditNoteEntryBuilder,
									PRODUCT_PORTUGAL.getUID(),
									daoPTCreditNoteEntry,
									CONTEXT_PORTUGAL.getUID(), INVOICE_ENTITY);

					return null;
				}

				private PTCreditNoteEntity buildCreditNote(
						DAOPTCreditNote daoPTCreditNote,
						PTCreditNote.Builder creditNoteBuilder,
						PTCreditNoteEntry.Builder creditNoteEntryBuilder,
						UID productUID,
						DAOPTCreditNoteEntry daoPTCreditNoteEntry,
						UID contextUID, PTInvoice reference) {

					buidCreditNoteEntry(creditNoteEntryBuilder, productUID,
							contextUID, reference);

					creditNoteBuilder.clear();

					creditNoteBuilder.setBilled(false).setCancelled(false)
							.setSelfBilled(false).setHash("HASH")
							.setDate(new Date()).setSourceId("EU")
							.addEntry(creditNoteEntryBuilder);

					PTCreditNoteEntity creditNote = (PTCreditNoteEntity) creditNoteBuilder
							.build();

					creditNote.setUID(new UID("CREDIT_NOTE"));

					PTCreditNoteEntryEntity creditNoteEntry = (PTCreditNoteEntryEntity) creditNote
							.getEntries().get(0);
					creditNoteEntry.setUID(new UID("CREDIT_NOTE_ENTRY"));
					creditNoteEntry.getDocumentReferences().add(creditNote);
					daoPTCreditNote.create(creditNote);

					return creditNote;
				}

				private void buidCreditNoteEntry(
						PTCreditNoteEntry.Builder creditNoteEntryBuilder,
						UID productUID, UID contextUID, PTInvoice reference) {
					creditNoteEntryBuilder.clear();

					creditNoteEntryBuilder
							.setUnitAmount(AmountType.WITH_TAX,
									new BigDecimal(20),
									Currency.getInstance("EUR"))
							.setTaxPointDate(new Date())
							.setCreditOrDebit(CreditOrDebit.DEBIT)
							.setDescription("Description")
							.setQuantity(new BigDecimal(1))
							.setUnitOfMeasure("Kg").setProductUID(productUID)
							.setContextUID(contextUID)
							.setReason("Rotten potatoes")
							.setReference(reference);
				}

			}.execute();
		} catch (Exception e) {
			throw e;
		}
	}
}
