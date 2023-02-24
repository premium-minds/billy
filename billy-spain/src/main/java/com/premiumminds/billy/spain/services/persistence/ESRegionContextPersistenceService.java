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

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.entities.ESRegionContextEntity;
import com.premiumminds.billy.spain.services.entities.ESRegionContext;

public class ESRegionContextPersistenceService implements PersistenceService<Context, ESRegionContext> {

    protected final DAOESRegionContext daoRegionContext;

    @Inject
    public ESRegionContextPersistenceService(DAOESRegionContext daoRegionContext) {
        this.daoRegionContext = daoRegionContext;
    }

    @Override
    public ESRegionContext create(final Builder<ESRegionContext> builder) {
        try {
            return new TransactionWrapper<ESRegionContext>(this.daoRegionContext) {

                @Override
                public ESRegionContext runTransaction() throws Exception {
                    ESRegionContextEntity entity = (ESRegionContextEntity) builder.build();
                    return (ESRegionContext) ESRegionContextPersistenceService.this.daoRegionContext.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESRegionContext update(final Builder<ESRegionContext> builder) {
        try {
            return new TransactionWrapper<ESRegionContext>(this.daoRegionContext) {

                @Override
                public ESRegionContext runTransaction() throws Exception {
                    ESRegionContextEntity entity = (ESRegionContextEntity) builder.build();
                    return (ESRegionContext) ESRegionContextPersistenceService.this.daoRegionContext.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESRegionContext get(final StringID<Context> uid) {
        try {
            return new TransactionWrapper<ESRegionContext>(this.daoRegionContext) {

                @Override
                public ESRegionContext runTransaction() throws Exception {
                    return (ESRegionContext) ESRegionContextPersistenceService.this.daoRegionContext.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public boolean isPartOf(final ESRegionContext parent, final Context child) {
        try {
            return new TransactionWrapper<Boolean>(this.daoRegionContext) {

                @Override
                public Boolean runTransaction() throws Exception {
                    return ESRegionContextPersistenceService.this.daoRegionContext.isSameOrSubContext(child, parent);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
