/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy portugal (PT Pack).
 * 
 * billy portugal (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy portugal (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.jpa;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.junit.Test;

import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;

public class TestJPAPTBusiness extends PTPersistencyAbstractTest {

	@Test
	public void doTest() throws Exception {
		TestJPAPTBusiness.execute(PTAbstractTest.injector);
	}

	public static void execute(final Injector injector) throws Exception {
		DAO<?> dao = injector.getInstance(DAOPTInvoice.class);
		final PTBusinessTestUtil business = new PTBusinessTestUtil(injector);

		try {
			new TransactionWrapper<Void>(dao) {

				@Override
				public Void runTransaction() throws Exception {
					DAOPTBusiness daoPTBusiness = injector
							.getInstance(DAOPTBusiness.class);

					PTBusinessEntity newBusiness = business.getBusinessEntity();

					daoPTBusiness.create(newBusiness);

					return null;
				}

			}.execute();
		} catch (Exception e) {
			throw e;
		}
	}

	class TestRunner implements Callable<Void> {

		private Injector injector;

		public TestRunner(Injector inject) {
			this.injector = inject;

		}

		@Override
		public Void call() throws Exception {
			TestJPAPTBusiness.execute(injector);
			return null;
		}

	}

	@Test
	public void testConcurrentIssuing() throws Exception {
		ConcurrentTestUtil test = new ConcurrentTestUtil(10);

		List<Future<?>> results = test.runThreads(new TestRunner(
				PTAbstractTest.injector));

		int i = 1;
		for (Future<?> future : results) {
			System.out.println("Thread " + i);
			try {
				test.testFuture(future);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			i++;
		}
	}
}
