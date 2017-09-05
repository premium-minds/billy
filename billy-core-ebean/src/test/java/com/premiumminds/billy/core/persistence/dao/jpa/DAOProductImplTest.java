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

import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAProductEntity;
import com.premiumminds.billy.core.services.UID;

public class DAOProductImplTest extends BaseH2Test {

    private static UID existingObjUid1 = new UID("1796dc4d-462c-468c-9f0f-170b65944341");

    private static UID existingObjUid2 = new UID("a413c9e9-f2de-4f4b-a937-a63d88504796");

    private static UID inactiveObjUid = new UID("f01970a9-c004-4f29-a3e1-bf2183248d76");

    private static DAOProductImpl daoProductImpl;

    @Before
    public void prepare() {
        DAOProductImplTest.daoProductImpl = new DAOProductImpl();
    }

    @Test
    public void getAllActiveProducts_noProducts() {
        List<ProductEntity> products = DAOProductImplTest.daoProductImpl.getAllActiveProducts();

        Assert.assertEquals(products.size(), 0);
    }

    @Test
    public void getAllActiveProducts_noActiveProducts() {
        DAOProductImplTest.daoProductImpl.beginTransaction();
        JPAProductEntity product = new JPAProductEntity();
        product.setUID(DAOProductImplTest.inactiveObjUid);
        product.setDescription("Test Product 0");
        DAOProductImplTest.daoProductImpl.commit();

        List<ProductEntity> products = DAOProductImplTest.daoProductImpl.getAllActiveProducts();

        Assert.assertEquals(products.size(), 0);
    }

    @Test
    public void getAllActiveProducts() {
        DAOProductImplTest.daoProductImpl.beginTransaction();
        JPAProductEntity product = new JPAProductEntity();
        product.setUID(DAOProductImplTest.existingObjUid1);
        product.setDescription("Test Product 1");
        DAOProductImplTest.daoProductImpl.create(product);

        product = new JPAProductEntity();
        product.setUID(DAOProductImplTest.existingObjUid2);
        product.setDescription("Test Product 2");
        DAOProductImplTest.daoProductImpl.create(product);
        DAOProductImplTest.daoProductImpl.commit();

        List<ProductEntity> products = DAOProductImplTest.daoProductImpl.getAllActiveProducts();

        Assert.assertEquals(products.size(), 2);
    }
}
