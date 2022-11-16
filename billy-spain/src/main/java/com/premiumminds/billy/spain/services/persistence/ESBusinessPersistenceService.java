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
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.services.entities.ESBusiness;
import javax.inject.Inject;

public class ESBusinessPersistenceService implements PersistenceService<Business, ESBusiness> {

    protected final DAOESBusiness daoBusiness;

    @Inject
    public ESBusinessPersistenceService(DAOESBusiness daoBusiness) {
        this.daoBusiness = daoBusiness;
    }

    @Override
    public ESBusiness create(final Builder<ESBusiness> builder) {
        try {
            return new TransactionWrapper<ESBusiness>(this.daoBusiness) {

                @Override
                public ESBusiness runTransaction() throws Exception {
                    ESBusinessEntity entity = (ESBusinessEntity) builder.build();
                    return (ESBusiness) ESBusinessPersistenceService.this.daoBusiness.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESBusiness update(final Builder<ESBusiness> builder) {
        try {
            return new TransactionWrapper<ESBusiness>(this.daoBusiness) {

                @Override
                public ESBusiness runTransaction() throws Exception {
                    ESBusinessEntity entity = (ESBusinessEntity) builder.build();
                    return (ESBusiness) ESBusinessPersistenceService.this.daoBusiness.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESBusiness get(final StringID<Business> uid) {
        try {
            return new TransactionWrapper<ESBusiness>(this.daoBusiness) {

                @Override
                public ESBusiness runTransaction() throws Exception {
                    return ESBusinessPersistenceService.this.daoBusiness.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
