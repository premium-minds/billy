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

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import javax.inject.Inject;

public class PTBusinessPersistenceService implements PersistenceService<Business, PTBusiness> {

    protected final DAOPTBusiness daoBusiness;

    @Inject
    public PTBusinessPersistenceService(DAOPTBusiness daoBusiness) {
        this.daoBusiness = daoBusiness;
    }

    @Override
    public PTBusiness create(final Builder<PTBusiness> builder) {
        try {
            return new TransactionWrapper<PTBusiness>(this.daoBusiness) {

                @Override
                public PTBusiness runTransaction() throws Exception {
                    PTBusinessEntity entity = (PTBusinessEntity) builder.build();
                    return (PTBusiness) PTBusinessPersistenceService.this.daoBusiness.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTBusiness update(final Builder<PTBusiness> builder) {
        try {
            return new TransactionWrapper<PTBusiness>(this.daoBusiness) {

                @Override
                public PTBusiness runTransaction() throws Exception {
                    PTBusinessEntity entity = (PTBusinessEntity) builder.build();
                    return (PTBusiness) PTBusinessPersistenceService.this.daoBusiness.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTBusiness get(final StringID<Business> uid) {
        try {
            return new TransactionWrapper<PTBusiness>(this.daoBusiness) {

                @Override
                public PTBusiness runTransaction() throws Exception {
                    return PTBusinessPersistenceService.this.daoBusiness.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
