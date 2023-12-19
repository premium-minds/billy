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

import jakarta.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESInvoice;

public class ESInvoicePersistenceService {

    protected final DAOESInvoice daoInvoice;

    @Inject
    public ESInvoicePersistenceService(DAOESInvoice daoInvoice) {
        this.daoInvoice = daoInvoice;
    }

    public ESInvoice update(final Builder<ESInvoice> builder) {
        try {
            return new TransactionWrapper<ESInvoice>(this.daoInvoice) {

                @Override
                public ESInvoice runTransaction() throws Exception {
                    ESInvoiceEntity entity = (ESInvoiceEntity) builder.build();
                    return ESInvoicePersistenceService.this.daoInvoice.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ESInvoice get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<ESInvoice>(this.daoInvoice) {

                @Override
                public ESInvoice runTransaction() throws Exception {
                    return ESInvoicePersistenceService.this.daoInvoice.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ESInvoice findByNumber(final StringID<Business> uidBusiness, final String number) {
        try {
            return new TransactionWrapper<ESInvoice>(this.daoInvoice) {

                @Override
                public ESInvoice runTransaction() throws Exception {
                    return ESInvoicePersistenceService.this.daoInvoice.findByNumber(uidBusiness, number);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
