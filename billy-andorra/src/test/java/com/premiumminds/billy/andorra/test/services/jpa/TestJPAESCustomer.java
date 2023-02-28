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

import com.premiumminds.billy.andorra.persistence.entities.ADCustomerEntity;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.util.ESCustomerTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;

public class TestJPAESCustomer extends ESJPAAbstractTest {

    private TransactionWrapper<Void> transaction;

    @BeforeEach
    public void setUp() {
        this.transaction = new TransactionWrapper<Void>(ADAbstractTest.injector.getInstance(DAOADInvoice.class)) {

            @Override
            public Void runTransaction() throws Exception {
                final ESCustomerTestUtil customer = new ESCustomerTestUtil(ADAbstractTest.injector);
                DAOADCustomer daoESCustomer = ADAbstractTest.injector.getInstance(DAOADCustomer.class);

                ADCustomerEntity newCustomer = customer.getCustomerEntity();

                daoESCustomer.create(newCustomer);

                return null;
            }

        };
    }

    @Test
    public void testSimpleCustomerCreate() throws Exception {
        ESJPAAbstractTest.execute(ADAbstractTest.injector, this.transaction);
    }

}
