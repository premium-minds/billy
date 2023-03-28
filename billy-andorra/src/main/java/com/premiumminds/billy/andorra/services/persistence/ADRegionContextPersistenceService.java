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

import com.premiumminds.billy.andorra.persistence.entities.ADRegionContextEntity;
import com.premiumminds.billy.andorra.services.entities.ADRegionContext;
import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;

public class ADRegionContextPersistenceService implements PersistenceService<Context, ADRegionContext> {

    protected final DAOADRegionContext daoRegionContext;

    @Inject
    public ADRegionContextPersistenceService(DAOADRegionContext daoRegionContext) {
        this.daoRegionContext = daoRegionContext;
    }

    @Override
    public ADRegionContext create(final Builder<ADRegionContext> builder) {
        try {
            return new TransactionWrapper<ADRegionContext>(this.daoRegionContext) {

                @Override
                public ADRegionContext runTransaction() throws Exception {
                    ADRegionContextEntity entity = (ADRegionContextEntity) builder.build();
                    return (ADRegionContext) ADRegionContextPersistenceService.this.daoRegionContext.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADRegionContext update(final Builder<ADRegionContext> builder) {
        try {
            return new TransactionWrapper<ADRegionContext>(this.daoRegionContext) {

                @Override
                public ADRegionContext runTransaction() throws Exception {
                    ADRegionContextEntity entity = (ADRegionContextEntity) builder.build();
                    return (ADRegionContext) ADRegionContextPersistenceService.this.daoRegionContext.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADRegionContext get(final StringID<Context> uid) {
        try {
            return new TransactionWrapper<ADRegionContext>(this.daoRegionContext) {

                @Override
                public ADRegionContext runTransaction() throws Exception {
                    return (ADRegionContext) ADRegionContextPersistenceService.this.daoRegionContext.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public boolean isPartOf(final ADRegionContext parent, final Context child) {
        try {
            return new TransactionWrapper<Boolean>(this.daoRegionContext) {

                @Override
                public Boolean runTransaction() throws Exception {
                    return ADRegionContextPersistenceService.this.daoRegionContext.isSameOrSubContext(child, parent);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
