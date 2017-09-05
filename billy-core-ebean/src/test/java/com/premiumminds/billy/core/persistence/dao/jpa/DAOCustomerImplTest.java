/**
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
package com.premiumminds.billy.core.persistence.dao.jpa;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPACustomerEntity;
import com.premiumminds.billy.core.services.UID;

public class DAOCustomerImplTest extends BaseH2Test {

    private static UID existingObjUid1 = new UID("1796dc4d-462c-468c-9f0f-170b65944341");

    private static UID existingObjUid2 = new UID("a413c9e9-f2de-4f4b-a937-a63d88504796");

    private static UID inactiveObjUid = new UID("f01970a9-c004-4f29-a3e1-bf2183248d76");

    private static DAOCustomerImpl daoCustomerImpl;

    @Before
    public void prepare() {
        DAOCustomerImplTest.daoCustomerImpl = new DAOCustomerImpl();
    }

    @Test
    public void getAllActiveCustomers_noCustomers() {
        List<CustomerEntity> customers = DAOCustomerImplTest.daoCustomerImpl.getAllActiveCustomers();

        Assert.assertEquals(customers.size(), 0);
    }

    @Test
    public void getAllActiveCustomers_noActiveCustomers() {
        DAOCustomerImplTest.daoCustomerImpl.beginTransaction();
        JPACustomerEntity customer = new JPACustomerEntity();
        customer.setUID(DAOCustomerImplTest.inactiveObjUid);
        customer.setName("Test Customer 0");
        DAOCustomerImplTest.daoCustomerImpl.commit();

        List<CustomerEntity> customers = DAOCustomerImplTest.daoCustomerImpl.getAllActiveCustomers();

        Assert.assertEquals(customers.size(), 0);
    }

    @Test
    public void getAllActiveCustomers() {
        DAOCustomerImplTest.daoCustomerImpl.beginTransaction();
        JPACustomerEntity customer = new JPACustomerEntity();
        customer.setUID(DAOCustomerImplTest.existingObjUid1);
        customer.setName("Test Customer 1");
        DAOCustomerImplTest.daoCustomerImpl.create(customer);

        customer = new JPACustomerEntity();
        customer.setUID(DAOCustomerImplTest.existingObjUid2);
        customer.setName("Test Customer 2");
        DAOCustomerImplTest.daoCustomerImpl.create(customer);
        DAOCustomerImplTest.daoCustomerImpl.commit();

        List<CustomerEntity> customers = DAOCustomerImplTest.daoCustomerImpl.getAllActiveCustomers();

        Assert.assertEquals(customers.size(), 2);

    }
}
