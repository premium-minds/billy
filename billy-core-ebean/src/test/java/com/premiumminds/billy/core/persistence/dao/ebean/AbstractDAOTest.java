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

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPABusinessEntity;
import com.premiumminds.billy.core.services.UID;

import io.ebean.Ebean;

public class AbstractDAOTest extends BaseH2Test {

    private static final UID existingObjUid = new UID("1796dc4d-462c-468c-9f0f-170b65944341");

    private static final UID nonExistingObjUid = new UID("a413c9e9-f2de-4f4b-a937-a63d88504796");

    private static final UID inactiveObjUid = new UID("f01970a9-c004-4f29-a3e1-bf2183248d76");
    

    private static AbstractDAO<BusinessEntity, JPABusinessEntity> abstractDAO;

    @BeforeEach
    public void prepare() {
        Ebean.beginTransaction();
        abstractDAO = new DAOBusinessImpl();

        JPABusinessEntity activeBusiness = new JPABusinessEntity();
        activeBusiness.setUID(existingObjUid);
        activeBusiness.setName("Test Business");

        JPABusinessEntity inactiveBusiness = new JPABusinessEntity();
        inactiveBusiness.setUID(inactiveObjUid);
        inactiveBusiness.setName("Inactive Test Business");

        abstractDAO.create(activeBusiness);
        Ebean.commitTransaction();
    }

    @Test
    public void get() {
        BaseEntity entity = abstractDAO.get(existingObjUid);

        Assertions.assertEquals(entity.getClass(), JPABusinessEntity.class);
        JPABusinessEntity business = (JPABusinessEntity) entity;
        Assertions.assertEquals(business.getUID(), existingObjUid);
        Assertions.assertEquals(business.getName(), "Test Business");
    }

    @Test
    public void get_noSuchUid() {
        Assertions.assertThrows(NoResultException.class, () -> abstractDAO.get(nonExistingObjUid));
    }

    @Test
    public void get_inactive() {
        Assertions.assertThrows(NoResultException.class, () -> abstractDAO.get(inactiveObjUid));
    }

    @Test
    public void exists() {
        boolean exists = abstractDAO.exists(existingObjUid);

        Assertions.assertTrue(exists);
    }

    @Test
    public void exists_noSuchUid() {
        boolean exists = abstractDAO.exists(nonExistingObjUid);

        Assertions.assertFalse(exists);
    }

    @Test
    public void exists_inactive() {
        boolean exists = abstractDAO.exists(inactiveObjUid);

        Assertions.assertFalse(exists);
    }
}
