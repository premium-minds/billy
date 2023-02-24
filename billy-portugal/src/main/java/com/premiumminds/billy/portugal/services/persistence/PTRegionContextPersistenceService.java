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
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;

public class PTRegionContextPersistenceService implements PersistenceService<Context, PTRegionContext> {

    protected final DAOPTRegionContext daoRegionContext;

    @Inject
    public PTRegionContextPersistenceService(DAOPTRegionContext daoRegionContext) {
        this.daoRegionContext = daoRegionContext;
    }

    @Override
    public PTRegionContext create(final Builder<PTRegionContext> builder) {
        try {
            return new TransactionWrapper<PTRegionContext>(this.daoRegionContext) {

                @Override
                public PTRegionContext runTransaction() throws Exception {
                    PTRegionContextEntity entity = (PTRegionContextEntity) builder.build();
                    return (PTRegionContext) PTRegionContextPersistenceService.this.daoRegionContext.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTRegionContext update(final Builder<PTRegionContext> builder) {
        try {
            return new TransactionWrapper<PTRegionContext>(this.daoRegionContext) {

                @Override
                public PTRegionContext runTransaction() throws Exception {
                    PTRegionContextEntity entity = (PTRegionContextEntity) builder.build();
                    return (PTRegionContext) PTRegionContextPersistenceService.this.daoRegionContext.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTRegionContext get(final StringID<Context> uid) {
        try {
            return new TransactionWrapper<PTRegionContext>(this.daoRegionContext) {

                @Override
                public PTRegionContext runTransaction() throws Exception {
                    return (PTRegionContext) PTRegionContextPersistenceService.this.daoRegionContext.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public boolean isPartOf(final PTRegionContext parent, final Context child) {
        try {
            return new TransactionWrapper<Boolean>(this.daoRegionContext) {

                @Override
                public Boolean runTransaction() throws Exception {
                    return PTRegionContextPersistenceService.this.daoRegionContext.isSameOrSubContext(child, parent);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
