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
import com.premiumminds.billy.france.persistence.dao.DAOFRApplication;
import com.premiumminds.billy.france.persistence.entities.FRApplicationEntity;
import com.premiumminds.billy.france.services.entities.FRApplication;

public class FRApplicationPersistenceService implements PersistenceService<FRApplication> {

    protected final DAOFRApplication daoApplication;

    @Inject
    public FRApplicationPersistenceService(DAOFRApplication daoApplication) {
        this.daoApplication = daoApplication;
    }

    @Override
    public FRApplication create(final Builder<FRApplication> builder) {
        try {
            return new TransactionWrapper<FRApplication>(this.daoApplication) {

                @Override
                public FRApplication runTransaction() throws Exception {
                    FRApplicationEntity entity = (FRApplicationEntity) builder.build();
                    return (FRApplication) FRApplicationPersistenceService.this.daoApplication.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRApplication update(final Builder<FRApplication> builder) {
        try {
            return new TransactionWrapper<FRApplication>(this.daoApplication) {

                @Override
                public FRApplication runTransaction() throws Exception {
                    FRApplicationEntity entity = (FRApplicationEntity) builder.build();
                    return (FRApplication) FRApplicationPersistenceService.this.daoApplication.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRApplication get(final UID uid) {
        try {
            return new TransactionWrapper<FRApplication>(this.daoApplication) {

                @Override
                public FRApplication runTransaction() throws Exception {
                    return FRApplicationPersistenceService.this.daoApplication.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
