/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.test.services.jpa;

import com.google.inject.Injector;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.andorra.test.util.ADBusinessTestUtil;
import java.util.concurrent.Callable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;

public class TestJPAESBusiness extends ESJPAAbstractTest {

    private TransactionWrapper<Void> transaction;
    private static final StringID<Business> BUSINESS_UID = StringID.fromValue("Biz");

    class TestRunner implements Callable<Void> {

        private Injector injector;

        public TestRunner(Injector inject) {
            this.injector = inject;

        }

        @Override
        public Void call() throws Exception {
            ESJPAAbstractTest.execute(this.injector, TestJPAESBusiness.this.transaction);
            return null;
        }
    }

    @BeforeEach
    public void setUp() {
        this.transaction = new TransactionWrapper<Void>(ADAbstractTest.injector.getInstance(DAOADInvoice.class)) {

            @Override
            public Void runTransaction() throws Exception {
                new ADBusinessTestUtil(ADAbstractTest.injector).getBusinessEntity(TestJPAESBusiness.BUSINESS_UID);
                return null;
            }
        };
    }

    @Test
    public void doTest() throws Exception {
        ESJPAAbstractTest.execute(ADAbstractTest.injector, this.transaction);
    }

    @Test
    public void testConcurrentCreate() throws Exception {
        ConcurrentTestUtil test = new ConcurrentTestUtil(10);

        test.runThreads(new TestRunner(ADAbstractTest.injector));

        DAOADBusiness biz = ADAbstractTest.injector.getInstance(DAOADBusiness.class);
        Assertions.assertTrue(biz.exists(TestJPAESBusiness.BUSINESS_UID));
    }
}
