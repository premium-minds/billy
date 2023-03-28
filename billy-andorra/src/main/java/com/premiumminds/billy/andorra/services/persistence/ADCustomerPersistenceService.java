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
package com.premiumminds.billy.andorra.services.persistence;

import com.premiumminds.billy.andorra.persistence.entities.ADCustomerEntity;
import com.premiumminds.billy.andorra.services.entities.ADCustomer;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import javax.inject.Inject;

public class ADCustomerPersistenceService implements PersistenceService<Customer, ADCustomer> {

    protected final DAOADCustomer daoCustomer;

    @Inject
    public ADCustomerPersistenceService(DAOADCustomer daoCustomer) {
        this.daoCustomer = daoCustomer;
    }

    @Override
    public ADCustomer create(final Builder<ADCustomer> builder) {
        try {
            return new TransactionWrapper<ADCustomer>(this.daoCustomer) {

                @Override
                public ADCustomer runTransaction() throws Exception {
                    ADCustomerEntity entity = (ADCustomerEntity) builder.build();
                    return (ADCustomer) ADCustomerPersistenceService.this.daoCustomer.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADCustomer update(final Builder<ADCustomer> builder) {
        try {
            return new TransactionWrapper<ADCustomer>(this.daoCustomer) {

                @Override
                public ADCustomer runTransaction() throws Exception {
                    ADCustomerEntity entity = (ADCustomerEntity) builder.build();
                    return (ADCustomer) ADCustomerPersistenceService.this.daoCustomer.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADCustomer get(final StringID<Customer> uid) {
        try {
            return new TransactionWrapper<ADCustomer>(this.daoCustomer) {

                @Override
                public ADCustomer runTransaction() throws Exception {
                    return (ADCustomer) ADCustomerPersistenceService.this.daoCustomer.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
