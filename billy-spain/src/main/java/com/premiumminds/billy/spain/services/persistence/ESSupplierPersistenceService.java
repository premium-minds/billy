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
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESSupplierEntity;
import com.premiumminds.billy.spain.services.entities.ESSupplier;
import javax.inject.Inject;

public class ESSupplierPersistenceService implements PersistenceService<Supplier, ESSupplier> {

    protected final DAOESSupplier daoSupplier;

    @Inject
    public ESSupplierPersistenceService(DAOESSupplier daoSupplier) {
        this.daoSupplier = daoSupplier;
    }

    @Override
    public ESSupplier create(final Builder<ESSupplier> builder) {
        try {
            return new TransactionWrapper<ESSupplier>(this.daoSupplier) {

                @Override
                public ESSupplier runTransaction() throws Exception {
                    ESSupplierEntity entity = (ESSupplierEntity) builder.build();
                    return (ESSupplier) ESSupplierPersistenceService.this.daoSupplier.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESSupplier update(final Builder<ESSupplier> builder) {
        try {
            return new TransactionWrapper<ESSupplier>(this.daoSupplier) {

                @Override
                public ESSupplier runTransaction() throws Exception {
                    ESSupplierEntity entity = (ESSupplierEntity) builder.build();
                    return (ESSupplier) ESSupplierPersistenceService.this.daoSupplier.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESSupplier get(final StringID<Supplier> uid) {
        try {
            return new TransactionWrapper<ESSupplier>(this.daoSupplier) {

                @Override
                public ESSupplier runTransaction() throws Exception {
                    return (ESSupplier) ESSupplierPersistenceService.this.daoSupplier.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
