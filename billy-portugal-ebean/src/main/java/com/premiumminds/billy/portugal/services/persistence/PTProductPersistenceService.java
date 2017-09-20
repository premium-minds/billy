/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.persistence;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTProduct;

public class PTProductPersistenceService implements PersistenceService<PTProduct> {

    protected final DAOPTProduct daoProduct;

    @Inject
    public PTProductPersistenceService(DAOPTProduct daoProduct) {
        this.daoProduct = daoProduct;
    }

    @Override
    public PTProduct create(final Builder<PTProduct> builder) {
        try {
            return new TransactionWrapper<PTProduct>(this.daoProduct) {

                @Override
                public PTProduct runTransaction() throws Exception {
                    PTProductEntity entity = (PTProductEntity) builder.build();
                    return (PTProduct) PTProductPersistenceService.this.daoProduct.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTProduct update(final Builder<PTProduct> builder) {
        try {
            return new TransactionWrapper<PTProduct>(this.daoProduct) {

                @Override
                public PTProduct runTransaction() throws Exception {
                    PTProductEntity entity = (PTProductEntity) builder.build();
                    return (PTProduct) PTProductPersistenceService.this.daoProduct.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTProduct get(final UID uid) {
        try {
            return new TransactionWrapper<PTProduct>(this.daoProduct) {

                @Override
                public PTProduct runTransaction() throws Exception {
                    return (PTProduct) PTProductPersistenceService.this.daoProduct.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public boolean exists(final UID uid) {
        return this.daoProduct.exists(uid);
    }

}
