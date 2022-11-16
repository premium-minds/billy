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

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.persistence.dao.jpa.DAOApplicationImpl;
import com.premiumminds.billy.spain.persistence.dao.DAOESApplication;
import com.premiumminds.billy.spain.persistence.entities.ESApplicationEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESApplicationEntity;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class DAOESApplicationImpl extends DAOApplicationImpl implements DAOESApplication {

    @Inject
    public DAOESApplicationImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ESApplicationEntity getEntityInstance() {
        return new JPAESApplicationEntity();
    }

    @Override
    protected Class<JPAESApplicationEntity> getEntityClass() {
        return JPAESApplicationEntity.class;
    }

    @Override
    public ESApplicationEntity get(StringID<Application> uid) throws NoResultException {
        return (ESApplicationEntity) super.get(uid);
    }

}
