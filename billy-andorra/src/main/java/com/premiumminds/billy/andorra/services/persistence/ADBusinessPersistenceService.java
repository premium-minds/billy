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

import com.premiumminds.billy.andorra.persistence.entities.ADBusinessEntity;
import com.premiumminds.billy.andorra.services.entities.ADBusiness;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import javax.inject.Inject;

public class ADBusinessPersistenceService implements PersistenceService<Business, ADBusiness> {

    protected final DAOADBusiness daoBusiness;

    @Inject
    public ADBusinessPersistenceService(DAOADBusiness daoBusiness) {
        this.daoBusiness = daoBusiness;
    }

    @Override
    public ADBusiness create(final Builder<ADBusiness> builder) {
        try {
            return new TransactionWrapper<ADBusiness>(this.daoBusiness) {

                @Override
                public ADBusiness runTransaction() throws Exception {
                    ADBusinessEntity entity = (ADBusinessEntity) builder.build();
                    return (ADBusiness) ADBusinessPersistenceService.this.daoBusiness.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADBusiness update(final Builder<ADBusiness> builder) {
        try {
            return new TransactionWrapper<ADBusiness>(this.daoBusiness) {

                @Override
                public ADBusiness runTransaction() throws Exception {
                    ADBusinessEntity entity = (ADBusinessEntity) builder.build();
                    return (ADBusiness) ADBusinessPersistenceService.this.daoBusiness.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADBusiness get(final StringID<Business> uid) {
        try {
            return new TransactionWrapper<ADBusiness>(this.daoBusiness) {

                @Override
                public ADBusiness runTransaction() throws Exception {
                    return ADBusinessPersistenceService.this.daoBusiness.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
