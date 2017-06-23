/**
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

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTCustomerTestUtil;

public class TestJPAPTCustomer extends PTJPAAbstractTest {

  private TransactionWrapper<Void> transaction;

  @Before
  public void setUp() {
    this.transaction = new TransactionWrapper<Void>(
        PTAbstractTest.injector.getInstance(DAOPTInvoice.class)) {

      @Override
      public Void runTransaction() throws Exception {
        final PTCustomerTestUtil customer = new PTCustomerTestUtil(PTAbstractTest.injector);
        DAOPTCustomer daoPTCustomer = PTAbstractTest.injector.getInstance(DAOPTCustomer.class);

        PTCustomerEntity newCustomer = customer.getCustomerEntity();

        daoPTCustomer.create(newCustomer);

        return null;
      }

    };
  }

  @Test
  public void testSimpleCustomerCreate() throws Exception {
    PTJPAAbstractTest.execute(PTAbstractTest.injector, this.transaction);
  }

}
