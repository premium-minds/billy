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
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.spain.persistence.dao.DAOESApplication;
import com.premiumminds.billy.spain.persistence.entities.ESApplicationEntity;
import com.premiumminds.billy.spain.services.entities.ESApplication;
import javax.inject.Inject;

public class ESApplicationPersistenceService implements PersistenceService<Application, ESApplication> {

    protected final DAOESApplication daoApplication;

    @Inject
    public ESApplicationPersistenceService(DAOESApplication daoApplication) {
        this.daoApplication = daoApplication;
    }

    @Override
    public ESApplication create(final Builder<ESApplication> builder) {
        try {
            return new TransactionWrapper<ESApplication>(this.daoApplication) {

                @Override
                public ESApplication runTransaction() throws Exception {
                    ESApplicationEntity entity = (ESApplicationEntity) builder.build();
                    return (ESApplication) ESApplicationPersistenceService.this.daoApplication.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESApplication update(final Builder<ESApplication> builder) {
        try {
            return new TransactionWrapper<ESApplication>(this.daoApplication) {

                @Override
                public ESApplication runTransaction() throws Exception {
                    ESApplicationEntity entity = (ESApplicationEntity) builder.build();
                    return (ESApplication) ESApplicationPersistenceService.this.daoApplication.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESApplication get(final StringID<Application> uid) {
        try {
            return new TransactionWrapper<ESApplication>(this.daoApplication) {

                @Override
                public ESApplication runTransaction() throws Exception {
                    return ESApplicationPersistenceService.this.daoApplication.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
