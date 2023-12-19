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
package com.premiumminds.billy.andorra.persistence.dao.jpa;

import com.premiumminds.billy.andorra.persistence.entities.ADApplicationEntity;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.persistence.dao.jpa.DAOApplicationImpl;
import com.premiumminds.billy.andorra.persistence.dao.DAOADApplication;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADApplicationEntity;

public class DAOADApplicationImpl extends DAOApplicationImpl implements DAOADApplication {

    @Inject
    public DAOADApplicationImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ADApplicationEntity getEntityInstance() {
        return new JPAADApplicationEntity();
    }

    @Override
    protected Class<JPAADApplicationEntity> getEntityClass() {
        return JPAADApplicationEntity.class;
    }

    @Override
    public ADApplicationEntity get(StringID<Application> uid) throws NoResultException {
        return (ADApplicationEntity) super.get(uid);
    }

}
