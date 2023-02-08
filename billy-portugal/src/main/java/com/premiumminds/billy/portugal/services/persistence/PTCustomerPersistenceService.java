/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.persistence;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;

public class PTCustomerPersistenceService implements PersistenceService<Customer, PTCustomer> {

    protected final DAOPTCustomer daoCustomer;

    @Inject
    public PTCustomerPersistenceService(DAOPTCustomer daoCustomer) {
        this.daoCustomer = daoCustomer;
    }

    @Override
    public PTCustomer create(final Builder<PTCustomer> builder) {
        try {
            return new TransactionWrapper<PTCustomer>(this.daoCustomer) {

                @Override
                public PTCustomer runTransaction() throws Exception {
                    PTCustomerEntity entity = (PTCustomerEntity) builder.build();
                    return (PTCustomer) PTCustomerPersistenceService.this.daoCustomer.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTCustomer update(final Builder<PTCustomer> builder) {
        try {
            return new TransactionWrapper<PTCustomer>(this.daoCustomer) {

                @Override
                public PTCustomer runTransaction() throws Exception {
                    PTCustomerEntity entity = (PTCustomerEntity) builder.build();
                    return (PTCustomer) PTCustomerPersistenceService.this.daoCustomer.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTCustomer get(final StringID<Customer> uid) {
        try {
            return new TransactionWrapper<PTCustomer>(this.daoCustomer) {

                @Override
                public PTCustomer runTransaction() throws Exception {
                    return (PTCustomer) PTCustomerPersistenceService.this.daoCustomer.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
