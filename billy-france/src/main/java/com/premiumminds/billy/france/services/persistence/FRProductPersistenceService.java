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
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.entities.FRProductEntity;
import com.premiumminds.billy.france.services.entities.FRProduct;

public class FRProductPersistenceService implements PersistenceService<FRProduct> {

    protected final DAOFRProduct daoProduct;

    @Inject
    public FRProductPersistenceService(DAOFRProduct daoProduct) {
        this.daoProduct = daoProduct;
    }

    @Override
    public FRProduct create(final Builder<FRProduct> builder) {
        try {
            return new TransactionWrapper<FRProduct>(this.daoProduct) {

                @Override
                public FRProduct runTransaction() throws Exception {
                    FRProductEntity entity = (FRProductEntity) builder.build();
                    return (FRProduct) FRProductPersistenceService.this.daoProduct.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRProduct update(final Builder<FRProduct> builder) {
        try {
            return new TransactionWrapper<FRProduct>(this.daoProduct) {

                @Override
                public FRProduct runTransaction() throws Exception {
                    FRProductEntity entity = (FRProductEntity) builder.build();
                    return (FRProduct) FRProductPersistenceService.this.daoProduct.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRProduct get(final UID uid) {
        try {
            return new TransactionWrapper<FRProduct>(this.daoProduct) {

                @Override
                public FRProduct runTransaction() throws Exception {
                    return (FRProduct) FRProductPersistenceService.this.daoProduct.get(uid);
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
