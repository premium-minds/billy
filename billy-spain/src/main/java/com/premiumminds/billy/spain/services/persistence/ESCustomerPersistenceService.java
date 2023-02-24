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
package com.premiumminds.billy.spain.services.persistence;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.entities.ESCustomerEntity;
import com.premiumminds.billy.spain.services.entities.ESCustomer;
import javax.inject.Inject;

public class ESCustomerPersistenceService implements PersistenceService<Customer, ESCustomer> {

    protected final DAOESCustomer daoCustomer;

    @Inject
    public ESCustomerPersistenceService(DAOESCustomer daoCustomer) {
        this.daoCustomer = daoCustomer;
    }

    @Override
    public ESCustomer create(final Builder<ESCustomer> builder) {
        try {
            return new TransactionWrapper<ESCustomer>(this.daoCustomer) {

                @Override
                public ESCustomer runTransaction() throws Exception {
                    ESCustomerEntity entity = (ESCustomerEntity) builder.build();
                    return (ESCustomer) ESCustomerPersistenceService.this.daoCustomer.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESCustomer update(final Builder<ESCustomer> builder) {
        try {
            return new TransactionWrapper<ESCustomer>(this.daoCustomer) {

                @Override
                public ESCustomer runTransaction() throws Exception {
                    ESCustomerEntity entity = (ESCustomerEntity) builder.build();
                    return (ESCustomer) ESCustomerPersistenceService.this.daoCustomer.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESCustomer get(final StringID<Customer> uid) {
        try {
            return new TransactionWrapper<ESCustomer>(this.daoCustomer) {

                @Override
                public ESCustomer runTransaction() throws Exception {
                    return (ESCustomer) ESCustomerPersistenceService.this.daoCustomer.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
