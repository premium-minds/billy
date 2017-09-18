/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.services.jpa;

import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.france.test.util.FRBusinessTestUtil;

public class TestJPAFRBusiness extends FRJPAAbstractTest {

    private TransactionWrapper<Void> transaction;
    private static final String BUSINESS_UID = "Biz";

    class TestRunner implements Callable<Void> {

        private Injector injector;

        public TestRunner(Injector inject) {
            this.injector = inject;

        }

        @Override
        public Void call() throws Exception {
            FRJPAAbstractTest.execute(this.injector, TestJPAFRBusiness.this.transaction);
            return null;
        }
    }

    @Before
    public void setUp() {
        this.transaction = new TransactionWrapper<Void>(FRAbstractTest.injector.getInstance(DAOFRInvoice.class)) {

            @Override
            public Void runTransaction() throws Exception {
                new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(TestJPAFRBusiness.BUSINESS_UID);
                return null;
            }
        };
    }

    @Test
    public void doTest() throws Exception {
        FRJPAAbstractTest.execute(FRAbstractTest.injector, this.transaction);
    }

    @Test
    public void testConcurrentCreate() throws Exception {
        ConcurrentTestUtil test = new ConcurrentTestUtil(10);

        test.runThreads(new TestRunner(FRAbstractTest.injector));

        DAOFRBusiness biz = FRAbstractTest.injector.getInstance(DAOFRBusiness.class);
        Assert.assertTrue(biz.exists(new UID(TestJPAFRBusiness.BUSINESS_UID)));
    }
}
