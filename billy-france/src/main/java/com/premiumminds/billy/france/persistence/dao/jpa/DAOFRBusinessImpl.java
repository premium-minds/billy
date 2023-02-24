/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.persistence.dao.jpa;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRBusinessEntity;
import com.premiumminds.billy.persistence.dao.jpa.DAOBusinessImpl;

public class DAOFRBusinessImpl extends DAOBusinessImpl implements DAOFRBusiness {

    @Inject
    public DAOFRBusinessImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public FRBusinessEntity getEntityInstance() {
        return new JPAFRBusinessEntity();
    }

    @Override
    protected Class<JPAFRBusinessEntity> getEntityClass() {
        return JPAFRBusinessEntity.class;
    }

    @Override
    public FRBusinessEntity get(StringID<Business> uid) throws NoResultException {
        return (FRBusinessEntity) super.get(uid);
    }

}
