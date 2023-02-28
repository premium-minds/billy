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

import com.premiumminds.billy.andorra.persistence.entities.ADTaxEntity;
import com.premiumminds.billy.andorra.services.entities.ADTax;
import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;

public class ADTaxPersistenceService implements PersistenceService<Tax, ADTax> {

    protected final DAOADTax daoTax;

    @Inject
    public ADTaxPersistenceService(DAOADTax daoTax) {
        this.daoTax = daoTax;
    }

    @Override
    public ADTax create(final Builder<ADTax> builder) {
        try {
            return new TransactionWrapper<ADTax>(this.daoTax) {

                @Override
                public ADTax runTransaction() throws Exception {
                    ADTaxEntity entity = (ADTaxEntity) builder.build();
                    return (ADTax) ADTaxPersistenceService.this.daoTax.create(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADTax update(final Builder<ADTax> builder) {
        try {
            return new TransactionWrapper<ADTax>(this.daoTax) {

                @Override
                public ADTax runTransaction() throws Exception {
                    ADTaxEntity entity = (ADTaxEntity) builder.build();
                    return (ADTax) ADTaxPersistenceService.this.daoTax.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADTax get(final StringID<Tax> uid) {
        try {
            return new TransactionWrapper<ADTax>(this.daoTax) {

                @Override
                public ADTax runTransaction() throws Exception {
                    return (ADTax) ADTaxPersistenceService.this.daoTax.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
