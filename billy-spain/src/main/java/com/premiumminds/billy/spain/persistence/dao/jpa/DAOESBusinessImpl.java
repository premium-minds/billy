/*
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
package com.premiumminds.billy.spain.persistence.dao.jpa;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.persistence.dao.jpa.DAOBusinessImpl;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESBusinessEntity;

public class DAOESBusinessImpl extends DAOBusinessImpl implements DAOESBusiness {

    @Inject
    public DAOESBusinessImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ESBusinessEntity getEntityInstance() {
        return new JPAESBusinessEntity();
    }

    @Override
    protected Class<JPAESBusinessEntity> getEntityClass() {
        return JPAESBusinessEntity.class;
    }

    @Override
    public ESBusinessEntity get(UID uid) throws NoResultException {
        return (ESBusinessEntity) super.get(uid);
    }

}
