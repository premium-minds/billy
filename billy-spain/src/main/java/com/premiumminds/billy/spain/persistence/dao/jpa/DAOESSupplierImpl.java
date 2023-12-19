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

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;

import com.premiumminds.billy.persistence.dao.jpa.DAOSupplierImpl;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESSupplierEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESSupplierEntity;

public class DAOESSupplierImpl extends DAOSupplierImpl implements DAOESSupplier {

    @Inject
    public DAOESSupplierImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ESSupplierEntity getEntityInstance() {
        return new JPAESSupplierEntity();
    }

    @Override
    protected Class<JPAESSupplierEntity> getEntityClass() {
        return JPAESSupplierEntity.class;
    }
}
