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

import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;

public class TestJPAPTBusiness extends PTJPAAbstractTest {

	private TransactionWrapper<Void> transaction;

	class TestRunner implements Callable<Void> {

		private Injector injector;

		public TestRunner(Injector inject) {
			this.injector = inject;

		}

		@Override
		public Void call() throws Exception {
			TestJPAPTBusiness.execute(injector, transaction);
			return null;
		}
	}

	@Before
	public void setUp() {
		this.transaction = new TransactionWrapper<Void>(
				injector.getInstance(DAOPTInvoice.class)) {

			@Override
			public Void runTransaction() throws Exception {
				PTBusinessTestUtil business = new PTBusinessTestUtil(injector);
				DAOPTBusiness daoPTBusiness = injector
						.getInstance(DAOPTBusiness.class);

				PTBusinessEntity newBusiness = business.getBusinessEntity();
				newBusiness.setUID(new UID("Biz"));

				daoPTBusiness.create(newBusiness);

				return null;
			}
		};
	}

	@Test
	public void doTest() throws Exception {
		TestJPAPTBusiness.execute(PTAbstractTest.injector, transaction);
	}

	@Test
	public void testConcurrentCreate() throws Exception {
		ConcurrentTestUtil test = new ConcurrentTestUtil(10);

		test.runThreads(new TestRunner(PTAbstractTest.injector));

		DAOPTBusiness biz = injector.getInstance(DAOPTBusiness.class);
		assertTrue(biz.exists(new UID("Biz")));
	}
}
