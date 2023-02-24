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
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTProduct;

public class PTProductPersistenceService implements PersistenceService<Product, PTProduct> {

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
    public PTProduct get(final StringID<Product> uid) {
        try {
            return new TransactionWrapper<PTProduct>(this.daoProduct) {

                @Override
                public PTProduct runTransaction() {
                    return (PTProduct) PTProductPersistenceService.this.daoProduct.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public boolean exists(final StringID<Product> uid) {
        return this.daoProduct.exists(uid);
    }

}
