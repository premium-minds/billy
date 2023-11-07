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

import com.premiumminds.billy.andorra.persistence.entities.ADShippingPointEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADShippingPointEntity;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;

import com.premiumminds.billy.persistence.dao.jpa.DAOShippingPointImpl;
import com.premiumminds.billy.andorra.persistence.dao.DAOADShippingPoint;

public class DAOADShippingPointImpl extends DAOShippingPointImpl implements DAOADShippingPoint {

    @Inject
    public DAOADShippingPointImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ADShippingPointEntity getEntityInstance() {
        return new JPAADShippingPointEntity();
    }

    @Override
    protected Class<JPAADShippingPointEntity> getEntityClass() {
        return JPAADShippingPointEntity.class;
    }
}
