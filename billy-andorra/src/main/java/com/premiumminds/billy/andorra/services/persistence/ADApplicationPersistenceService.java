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

import com.premiumminds.billy.andorra.persistence.entities.ADApplicationEntity;
import com.premiumminds.billy.andorra.services.entities.ADApplication;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.andorra.persistence.dao.DAOADApplication;
import jakarta.inject.Inject;

public class ADApplicationPersistenceService implements PersistenceService<Application, ADApplication> {

    protected final DAOADApplication daoApplication;

    @Inject
    public ADApplicationPersistenceService(DAOADApplication daoApplication) {
        this.daoApplication = daoApplication;
    }

    @Override
    public ADApplication create(final Builder<ADApplication> builder) {
        try {
            return new TransactionWrapper<ADApplication>(this.daoApplication) {

                @Override
                public ADApplication runTransaction() throws Exception {
                    ADApplicationEntity entity = (ADApplicationEntity) builder.build();
                    return (ADApplication) ADApplicationPersistenceService.this.daoApplication.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADApplication update(final Builder<ADApplication> builder) {
        try {
            return new TransactionWrapper<ADApplication>(this.daoApplication) {

                @Override
                public ADApplication runTransaction() throws Exception {
                    ADApplicationEntity entity = (ADApplicationEntity) builder.build();
                    return (ADApplication) ADApplicationPersistenceService.this.daoApplication.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADApplication get(final StringID<Application> uid) {
        try {
            return new TransactionWrapper<ADApplication>(this.daoApplication) {

                @Override
                public ADApplication runTransaction() throws Exception {
                    return ADApplicationPersistenceService.this.daoApplication.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
