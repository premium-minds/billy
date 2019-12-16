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
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTApplication;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.services.entities.PTApplication;

public class PTApplicationPersistenceService implements PersistenceService<PTApplication> {

    protected final DAOPTApplication daoApplication;

    @Inject
    public PTApplicationPersistenceService(DAOPTApplication daoApplication) {
        this.daoApplication = daoApplication;
    }

    @Override
    public PTApplication create(final Builder<PTApplication> builder) {
        try {
            return new TransactionWrapper<PTApplication>(this.daoApplication) {

                @Override
                public PTApplication runTransaction() throws Exception {
                    PTApplicationEntity entity = (PTApplicationEntity) builder.build();
                    return (PTApplication) PTApplicationPersistenceService.this.daoApplication.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTApplication update(final Builder<PTApplication> builder) {
        try {
            return new TransactionWrapper<PTApplication>(this.daoApplication) {

                @Override
                public PTApplication runTransaction() throws Exception {
                    PTApplicationEntity entity = (PTApplicationEntity) builder.build();
                    return (PTApplication) PTApplicationPersistenceService.this.daoApplication.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTApplication get(final UID uid) {
        try {
            return new TransactionWrapper<PTApplication>(this.daoApplication) {

                @Override
                public PTApplication runTransaction() throws Exception {
                    return PTApplicationPersistenceService.this.daoApplication.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
