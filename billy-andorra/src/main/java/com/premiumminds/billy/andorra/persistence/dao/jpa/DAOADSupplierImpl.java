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

import com.premiumminds.billy.andorra.persistence.entities.ADSupplierEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADSupplierEntity;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;

import com.premiumminds.billy.persistence.dao.jpa.DAOSupplierImpl;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;

public class DAOADSupplierImpl extends DAOSupplierImpl implements DAOADSupplier {

    @Inject
    public DAOADSupplierImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ADSupplierEntity getEntityInstance() {
        return new JPAADSupplierEntity();
    }

    @Override
    protected Class<JPAADSupplierEntity> getEntityClass() {
        return JPAADSupplierEntity.class;
    }
}
