/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core Ebean.
 *
 * billy core Ebean is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core Ebean is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core Ebean. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.dao.ebean;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPACustomerEntity;
import com.premiumminds.billy.core.services.UID;

import io.ebean.Ebean;

public class DAOCustomerImplTest extends BaseH2Test {

    private static final UID existingObjUid1 = new UID("1796dc4d-462c-468c-9f0f-170b65944341");

    private static final UID existingObjUid2 = new UID("a413c9e9-f2de-4f4b-a937-a63d88504796");

    private static final UID inactiveObjUid = new UID("f01970a9-c004-4f29-a3e1-bf2183248d76");

    private static DAOCustomerImpl daoCustomerImpl;

    @BeforeEach
    public void prepare() {
        daoCustomerImpl = new DAOCustomerImpl();
    }

    @Test
    public void getAllActiveCustomers_noCustomers() {
        List<CustomerEntity> customers = daoCustomerImpl.getAllActiveCustomers();

        Assertions.assertEquals(customers.size(), 0);
    }

    @Test
    public void getAllActiveCustomers_noActiveCustomers() {
        Ebean.beginTransaction();
        JPACustomerEntity customer = new JPACustomerEntity();
        customer.setUID(inactiveObjUid);
        customer.setName("Test Customer 0");
        Ebean.commitTransaction();

        List<CustomerEntity> customers = daoCustomerImpl.getAllActiveCustomers();

        Assertions.assertEquals(customers.size(), 0);
    }

    @Test
    public void getAllActiveCustomers() {
        Ebean.beginTransaction();
        JPACustomerEntity customer = new JPACustomerEntity();
        customer.setUID(existingObjUid1);
        customer.setName("Test Customer 1");
        daoCustomerImpl.create(customer);

        customer = new JPACustomerEntity();
        customer.setUID(existingObjUid2);
        customer.setName("Test Customer 2");
        daoCustomerImpl.create(customer);
        Ebean.commitTransaction();

        List<CustomerEntity> customers = daoCustomerImpl.getAllActiveCustomers();

        Assertions.assertEquals(customers.size(), 2);

    }
}
