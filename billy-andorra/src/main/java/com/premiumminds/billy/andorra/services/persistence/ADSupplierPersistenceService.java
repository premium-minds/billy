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

import com.premiumminds.billy.andorra.persistence.entities.ADSupplierEntity;
import com.premiumminds.billy.andorra.services.entities.ADSupplier;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import jakarta.inject.Inject;

public class ADSupplierPersistenceService implements PersistenceService<Supplier, ADSupplier> {

    protected final DAOADSupplier daoSupplier;

    @Inject
    public ADSupplierPersistenceService(DAOADSupplier daoSupplier) {
        this.daoSupplier = daoSupplier;
    }

    @Override
    public ADSupplier create(final Builder<ADSupplier> builder) {
        try {
            return new TransactionWrapper<ADSupplier>(this.daoSupplier) {

                @Override
                public ADSupplier runTransaction() throws Exception {
                    ADSupplierEntity entity = (ADSupplierEntity) builder.build();
                    return (ADSupplier) ADSupplierPersistenceService.this.daoSupplier.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADSupplier update(final Builder<ADSupplier> builder) {
        try {
            return new TransactionWrapper<ADSupplier>(this.daoSupplier) {

                @Override
                public ADSupplier runTransaction() throws Exception {
                    ADSupplierEntity entity = (ADSupplierEntity) builder.build();
                    return (ADSupplier) ADSupplierPersistenceService.this.daoSupplier.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADSupplier get(final StringID<Supplier> uid) {
        try {
            return new TransactionWrapper<ADSupplier>(this.daoSupplier) {

                @Override
                public ADSupplier runTransaction() throws Exception {
                    return (ADSupplier) ADSupplierPersistenceService.this.daoSupplier.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
