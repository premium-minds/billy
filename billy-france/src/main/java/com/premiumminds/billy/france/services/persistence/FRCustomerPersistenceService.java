/**
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
package com.premiumminds.billy.france.services.persistence;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.entities.FRCustomerEntity;
import com.premiumminds.billy.france.services.entities.FRCustomer;

public class FRCustomerPersistenceService implements PersistenceService<FRCustomer> {

    protected final DAOFRCustomer daoCustomer;

    @Inject
    public FRCustomerPersistenceService(DAOFRCustomer daoCustomer) {
        this.daoCustomer = daoCustomer;
    }

    @Override
    public FRCustomer create(final Builder<FRCustomer> builder) {
        try {
            return new TransactionWrapper<FRCustomer>(this.daoCustomer) {

                @Override
                public FRCustomer runTransaction() throws Exception {
                    FRCustomerEntity entity = (FRCustomerEntity) builder.build();
                    return (FRCustomer) FRCustomerPersistenceService.this.daoCustomer.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRCustomer update(final Builder<FRCustomer> builder) {
        try {
            return new TransactionWrapper<FRCustomer>(this.daoCustomer) {

                @Override
                public FRCustomer runTransaction() throws Exception {
                    FRCustomerEntity entity = (FRCustomerEntity) builder.build();
                    return (FRCustomer) FRCustomerPersistenceService.this.daoCustomer.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRCustomer get(final UID uid) {
        try {
            return new TransactionWrapper<FRCustomer>(this.daoCustomer) {

                @Override
                public FRCustomer runTransaction() throws Exception {
                    return (FRCustomer) FRCustomerPersistenceService.this.daoCustomer.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
