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
package com.premiumminds.billy.portugal.test.services.jpa;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTCreditNoteTestUtil;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

public class TestJPAPTCreditNote extends PTPersistencyAbstractTest {

	@Before
	public void setUp() {
		final PTInvoiceTestUtil invoice = new PTInvoiceTestUtil(injector);
		DAOPTInvoice daoPTInvoice = injector.getInstance(DAOPTInvoice.class);

		PTInvoiceEntity newInvoice = invoice.getInvoiceEntity();

		daoPTInvoice.create(newInvoice);
	}

	// Expect exception caused by two credit notes for the same invoice
	@Test(expected = com.premiumminds.billy.core.exceptions.BillyValidationException.class)
	public void doTest() throws Exception {
		execute(injector);
		execute(injector);
	}

	// Expect no exception
	@Test
	public void doTest2() throws Exception {
		execute(injector);
	}

	public static void execute(final Injector injector) throws Exception {
		DAO<?> dao = injector.getInstance(DAOPTInvoice.class);
		final PTCreditNoteTestUtil creditNote = new PTCreditNoteTestUtil(
				injector);

		try {
			new TransactionWrapper<Void>(dao) {

				@Override
				public Void runTransaction() throws Exception {
					DAOPTCreditNote daoPTCreditNote = injector
							.getInstance(DAOPTCreditNote.class);

					PTCreditNoteEntity newCreditNote = creditNote
							.getCreditNoteEntity();

					daoPTCreditNote.create(newCreditNote);

					return null;
				}
			}.execute();
		} catch (Exception e) {
			throw e;
		}
	}
}
