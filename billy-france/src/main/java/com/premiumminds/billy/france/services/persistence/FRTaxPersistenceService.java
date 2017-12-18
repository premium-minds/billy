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
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRTaxEntity;
import com.premiumminds.billy.france.services.entities.FRTax;

public class FRTaxPersistenceService implements PersistenceService<FRTax> {

    protected final DAOFRTax daoTax;

    @Inject
    public FRTaxPersistenceService(DAOFRTax daoTax) {
        this.daoTax = daoTax;
    }

    @Override
    public FRTax create(final Builder<FRTax> builder) {
        try {
            return new TransactionWrapper<FRTax>(this.daoTax) {

                @Override
                public FRTax runTransaction() throws Exception {
                    FRTaxEntity entity = (FRTaxEntity) builder.build();
                    return (FRTax) FRTaxPersistenceService.this.daoTax.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRTax update(final Builder<FRTax> builder) {
        try {
            return new TransactionWrapper<FRTax>(this.daoTax) {

                @Override
                public FRTax runTransaction() throws Exception {
                    FRTaxEntity entity = (FRTaxEntity) builder.build();
                    return (FRTax) FRTaxPersistenceService.this.daoTax.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRTax get(final UID uid) {
        try {
            return new TransactionWrapper<FRTax>(this.daoTax) {

                @Override
                public FRTax runTransaction() throws Exception {
                    return (FRTax) FRTaxPersistenceService.this.daoTax.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
