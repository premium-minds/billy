/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core JPA.
 *
 * billy core JPA is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core JPA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.persistence.dao.jpa;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.persistence.entities.jpa.JPACustomerEntity;

public class DAOCustomerImpl extends AbstractDAO<Customer, CustomerEntity, JPACustomerEntity> implements DAOCustomer {

    @Inject
    public DAOCustomerImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerEntity> getAllActiveCustomers() {
        List<JPACustomerEntity> result = (List<JPACustomerEntity>) this.getEntityManager()
                .createQuery(
                        "select c from " + this.getEntityClass().getCanonicalName() + " c " + "where c.active=true",
                        this.getEntityClass())
                .getResultList();
        return this.checkEntityList(result, CustomerEntity.class);
    }

    @Override
    protected Class<? extends JPACustomerEntity> getEntityClass() {
        return JPACustomerEntity.class;
    }

    @Override
    public CustomerEntity getEntityInstance() {
        return new JPACustomerEntity();
    }

}
