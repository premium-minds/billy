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
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.persistence.entities.FRSupplierEntity;
import com.premiumminds.billy.france.services.entities.FRSupplier;

public class FRSupplierPersistenceService implements PersistenceService<FRSupplier> {

    protected final DAOFRSupplier daoSupplier;

    @Inject
    public FRSupplierPersistenceService(DAOFRSupplier daoSupplier) {
        this.daoSupplier = daoSupplier;
    }

    @Override
    public FRSupplier create(final Builder<FRSupplier> builder) {
        try {
            return new TransactionWrapper<FRSupplier>(this.daoSupplier) {

                @Override
                public FRSupplier runTransaction() throws Exception {
                    FRSupplierEntity entity = (FRSupplierEntity) builder.build();
                    return (FRSupplier) FRSupplierPersistenceService.this.daoSupplier.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRSupplier update(final Builder<FRSupplier> builder) {
        try {
            return new TransactionWrapper<FRSupplier>(this.daoSupplier) {

                @Override
                public FRSupplier runTransaction() throws Exception {
                    FRSupplierEntity entity = (FRSupplierEntity) builder.build();
                    return (FRSupplier) FRSupplierPersistenceService.this.daoSupplier.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRSupplier get(final UID uid) {
        try {
            return new TransactionWrapper<FRSupplier>(this.daoSupplier) {

                @Override
                public FRSupplier runTransaction() throws Exception {
                    return (FRSupplier) FRSupplierPersistenceService.this.daoSupplier.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
