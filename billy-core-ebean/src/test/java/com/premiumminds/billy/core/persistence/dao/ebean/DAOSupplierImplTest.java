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
package com.premiumminds.billy.core.persistence.dao.ebean;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.persistence.entities.SupplierEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPASupplierEntity;
import com.premiumminds.billy.core.services.UID;

import io.ebean.Ebean;

public class DAOSupplierImplTest extends BaseH2Test {

    private static UID existingObjUid1 = new UID("1796dc4d-462c-468c-9f0f-170b65944341");

    private static UID existingObjUid2 = new UID("a413c9e9-f2de-4f4b-a937-a63d88504796");

    private static UID inactiveObjUid = new UID("f01970a9-c004-4f29-a3e1-bf2183248d76");

    private static DAOSupplierImpl daoSupplierImpl;

    @Before
    public void prepare() {
        DAOSupplierImplTest.daoSupplierImpl = new DAOSupplierImpl();
    }

    @Test
    public void getAllActiveSuppliers_noSuppliers() {
        List<SupplierEntity> suppliers = DAOSupplierImplTest.daoSupplierImpl.getAllActiveSuppliers();

        Assert.assertEquals(suppliers.size(), 0);
    }

    @Test
    public void getAllActiveSuppliers_noActiveSuppliers() {
        Ebean.beginTransaction();
        JPASupplierEntity supplier = new JPASupplierEntity();
        supplier.setUID(DAOSupplierImplTest.inactiveObjUid);
        supplier.setName("Test Supplier 0");
        Ebean.commitTransaction();

        List<SupplierEntity> suppliers = DAOSupplierImplTest.daoSupplierImpl.getAllActiveSuppliers();

        Assert.assertEquals(suppliers.size(), 0);
    }

    @Test
    public void getAllActiveSuppliers() {
        Ebean.beginTransaction();
        JPASupplierEntity supplier = new JPASupplierEntity();
        supplier.setUID(DAOSupplierImplTest.existingObjUid1);
        supplier.setName("Test Supplier 1");
        DAOSupplierImplTest.daoSupplierImpl.create(supplier);

        supplier = new JPASupplierEntity();
        supplier.setUID(DAOSupplierImplTest.existingObjUid2);
        supplier.setName("Test Supplier 2");
        DAOSupplierImplTest.daoSupplierImpl.create(supplier);
        Ebean.commitTransaction();

        List<SupplierEntity> suppliers = DAOSupplierImplTest.daoSupplierImpl.getAllActiveSuppliers();

        Assert.assertEquals(suppliers.size(), 2);
    }
}
