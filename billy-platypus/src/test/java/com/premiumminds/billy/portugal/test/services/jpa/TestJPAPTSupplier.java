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

import org.junit.Test;

import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTSupplierTestUtil;

public class TestJPAPTSupplier extends PTPersistencyAbstractTest {

	@Test
	public void doTest() {
		execute(injector);
		// assert
	}

	public static void execute(final Injector injector) {
		DAO<?> dao = injector.getInstance(DAOPTInvoice.class);
		final PTSupplierTestUtil supplier = new PTSupplierTestUtil(injector);

		try {
			new TransactionWrapper<Void>(dao) {

				@Override
				public Void runTransaction() throws Exception {
					DAOPTSupplier daoPTSupplier = injector
							.getInstance(DAOPTSupplier.class);

					PTSupplierEntity newSupplier = supplier.getSupplierEntity();

					daoPTSupplier.create(newSupplier);

					return null;
				}

			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
