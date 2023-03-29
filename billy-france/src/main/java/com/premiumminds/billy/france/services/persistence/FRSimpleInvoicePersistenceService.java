/*
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
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRSimpleInvoice;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice;

public class FRSimpleInvoicePersistenceService {

    protected final DAOFRSimpleInvoice daoInvoice;
    @Inject
    public FRSimpleInvoicePersistenceService(DAOFRSimpleInvoice daoInvoice) {
        this.daoInvoice = daoInvoice;
    }

    public FRSimpleInvoice update(final Builder<FRSimpleInvoice> builder) {
        try {
            return new TransactionWrapper<FRSimpleInvoice>(this.daoInvoice) {

                @Override
                public FRSimpleInvoice runTransaction() {
                    FRSimpleInvoiceEntity entity = (FRSimpleInvoiceEntity) builder.build();
                    return FRSimpleInvoicePersistenceService.this.daoInvoice.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public FRSimpleInvoice get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<FRSimpleInvoice>(this.daoInvoice) {

                @Override
                public FRSimpleInvoice runTransaction() {
                    return FRSimpleInvoicePersistenceService.this.daoInvoice.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
