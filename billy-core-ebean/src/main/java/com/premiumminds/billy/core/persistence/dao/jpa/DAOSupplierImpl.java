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

import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.persistence.entities.SupplierEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPASupplierEntity;

public class DAOSupplierImpl extends AbstractDAO<SupplierEntity, JPASupplierEntity> implements DAOSupplier {

    @Override
    protected Class<? extends JPASupplierEntity> getEntityClass() {
        return JPASupplierEntity.class;
    }

    @Override
    public SupplierEntity getEntityInstance() {
        return new JPASupplierEntity();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SupplierEntity> getAllActiveSuppliers() {
        List<JPASupplierEntity> result = (List<JPASupplierEntity>) this.getEntityManager()
                .createQuery(
                        "select c from " + this.getEntityClass().getCanonicalName() + " c " + "where c.active=true",
                        this.getEntityClass())
                .getResultList();
        return this.checkEntityList(result, SupplierEntity.class);
    }

}
