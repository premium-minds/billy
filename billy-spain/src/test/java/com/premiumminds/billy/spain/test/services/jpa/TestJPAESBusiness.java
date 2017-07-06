/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.test.services.jpa;

import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.spain.test.util.ESBusinessTestUtil;

public class TestJPAESBusiness extends ESJPAAbstractTest {

  private TransactionWrapper<Void> transaction;
  private static final String BUSINESS_UID = "Biz";

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

  @Before
  public void setUp() {
    this.transaction = new TransactionWrapper<Void>(
        ESAbstractTest.injector.getInstance(DAOESInvoice.class)) {

      @Override
      public Void runTransaction() throws Exception {
        new ESBusinessTestUtil(ESAbstractTest.injector).getBusinessEntity(BUSINESS_UID);
        return null;
      }
    };
  }

  @Test
  public void doTest() throws Exception {
    ESJPAAbstractTest.execute(ESAbstractTest.injector, this.transaction);
  }

  @Test
  public void testConcurrentCreate() throws Exception {
    ConcurrentTestUtil test = new ConcurrentTestUtil(10);

    test.runThreads(new TestRunner(ESAbstractTest.injector));

    DAOESBusiness biz = ESAbstractTest.injector.getInstance(DAOESBusiness.class);
    Assert.assertTrue(biz.exists(new UID(BUSINESS_UID)));
  }
}
