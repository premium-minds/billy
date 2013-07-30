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

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.portugal.PlatypusBootstrap;
import com.premiumminds.billy.portugal.PlatypusDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PlatypusTestPersistenceDependencyModule;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

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
		final PTInvoiceTestUtil invoice = new PTInvoiceTestUtil(injector);

		try {
			new TransactionWrapper<Void>(dao) {

				@Override
				public Void runTransaction() throws Exception {
					DAOPTInvoice daoPTInvoice = injector
							.getInstance(DAOPTInvoice.class);

					PTInvoiceEntity newInvoice = invoice.getInvoiceEntity();

					daoPTInvoice.create(newInvoice);

					return null;
				}

			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
