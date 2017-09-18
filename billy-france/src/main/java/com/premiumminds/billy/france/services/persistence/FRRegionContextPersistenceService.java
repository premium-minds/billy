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
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.entities.FRRegionContextEntity;
import com.premiumminds.billy.france.services.entities.FRRegionContext;

public class FRRegionContextPersistenceService implements PersistenceService<FRRegionContext> {

    protected final DAOFRRegionContext daoRegionContext;

    @Inject
    public FRRegionContextPersistenceService(DAOFRRegionContext daoRegionContext) {
        this.daoRegionContext = daoRegionContext;
    }

    @Override
    public FRRegionContext create(final Builder<FRRegionContext> builder) {
        try {
            return new TransactionWrapper<FRRegionContext>(this.daoRegionContext) {

                @Override
                public FRRegionContext runTransaction() throws Exception {
                    FRRegionContextEntity entity = (FRRegionContextEntity) builder.build();
                    return (FRRegionContext) FRRegionContextPersistenceService.this.daoRegionContext.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRRegionContext update(final Builder<FRRegionContext> builder) {
        try {
            return new TransactionWrapper<FRRegionContext>(this.daoRegionContext) {

                @Override
                public FRRegionContext runTransaction() throws Exception {
                    FRRegionContextEntity entity = (FRRegionContextEntity) builder.build();
                    return (FRRegionContext) FRRegionContextPersistenceService.this.daoRegionContext.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRRegionContext get(final UID uid) {
        try {
            return new TransactionWrapper<FRRegionContext>(this.daoRegionContext) {

                @Override
                public FRRegionContext runTransaction() throws Exception {
                    return (FRRegionContext) FRRegionContextPersistenceService.this.daoRegionContext.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public boolean isPartOf(final FRRegionContext parent, final Context child) {
        try {
            return new TransactionWrapper<Boolean>(this.daoRegionContext) {

                @Override
                public Boolean runTransaction() throws Exception {
                    return FRRegionContextPersistenceService.this.daoRegionContext.isSubContext(child, parent);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
