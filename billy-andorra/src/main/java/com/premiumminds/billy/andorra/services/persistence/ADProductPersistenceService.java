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

import com.premiumminds.billy.andorra.persistence.entities.ADProductEntity;
import com.premiumminds.billy.andorra.services.entities.ADProduct;
import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;

public class ADProductPersistenceService implements PersistenceService<Product, ADProduct> {

    protected final DAOADProduct daoProduct;

    @Inject
    public ADProductPersistenceService(DAOADProduct daoProduct) {
        this.daoProduct = daoProduct;
    }

    @Override
    public ADProduct create(final Builder<ADProduct> builder) {
        try {
            return new TransactionWrapper<ADProduct>(this.daoProduct) {

                @Override
                public ADProduct runTransaction() throws Exception {
                    ADProductEntity entity = (ADProductEntity) builder.build();
                    return (ADProduct) ADProductPersistenceService.this.daoProduct.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADProduct update(final Builder<ADProduct> builder) {
        try {
            return new TransactionWrapper<ADProduct>(this.daoProduct) {

                @Override
                public ADProduct runTransaction() throws Exception {
                    ADProductEntity entity = (ADProductEntity) builder.build();
                    return (ADProduct) ADProductPersistenceService.this.daoProduct.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADProduct get(final StringID<Product> uid) {
        try {
            return new TransactionWrapper<ADProduct>(this.daoProduct) {

                @Override
                public ADProduct runTransaction() throws Exception {
                    return (ADProduct) ADProductPersistenceService.this.daoProduct.get(uid);
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
