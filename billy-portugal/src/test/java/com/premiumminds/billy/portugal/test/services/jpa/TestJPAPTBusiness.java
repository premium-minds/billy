/*
 * Copyright (C) 2017 Premium Minds.
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

import com.google.inject.Injector;
import java.util.concurrent.Callable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;

public class TestJPAPTBusiness extends PTJPAAbstractTest {

    private TransactionWrapper<Void> transaction;
    private static final StringID<Business> BUSINESS_UID = StringID.fromValue("Biz");

    class TestRunner implements Callable<Void> {

        private Injector injector;

        public TestRunner(Injector inject) {
            this.injector = inject;

        }

        @Override
        public Void call() throws Exception {
            PTJPAAbstractTest.execute(this.injector, TestJPAPTBusiness.this.transaction);
            return null;
        }
    }

    @BeforeEach
    public void setUp() {
        this.transaction = new TransactionWrapper<Void>(PTAbstractTest.injector.getInstance(DAOPTInvoice.class)) {

            @Override
            public Void runTransaction() throws Exception {
                new PTBusinessTestUtil(PTAbstractTest.injector).getBusinessEntity(TestJPAPTBusiness.BUSINESS_UID);
                return null;
            }
        };
    }

    @Test
    public void doTest() throws Exception {
        PTJPAAbstractTest.execute(PTAbstractTest.injector, this.transaction);
    }

    @Test
    public void testConcurrentCreate() throws Exception {
        ConcurrentTestUtil test = new ConcurrentTestUtil(10);

        test.runThreads(new TestRunner(PTAbstractTest.injector));

        DAOPTBusiness biz = PTAbstractTest.injector.getInstance(DAOPTBusiness.class);
        Assertions.assertTrue(biz.exists(TestJPAPTBusiness.BUSINESS_UID));
    }
}
