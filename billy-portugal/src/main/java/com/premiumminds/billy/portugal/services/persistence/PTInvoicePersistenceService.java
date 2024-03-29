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
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

public class PTInvoicePersistenceService {

    protected final DAOPTInvoice daoInvoice;

    @Inject
    public PTInvoicePersistenceService(DAOPTInvoice daoInvoice) {
        this.daoInvoice = daoInvoice;
    }

    public PTInvoice update(final Builder<PTInvoice> builder) {
        try {
            return new TransactionWrapper<PTInvoice>(this.daoInvoice) {

                @Override
                public PTInvoice runTransaction() throws Exception {
                    PTInvoiceEntity entity = (PTInvoiceEntity) builder.build();
                    return PTInvoicePersistenceService.this.daoInvoice.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public PTInvoice get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<PTInvoice>(this.daoInvoice) {

                @Override
                public PTInvoice runTransaction() throws Exception {
                    return PTInvoicePersistenceService.this.daoInvoice.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public PTInvoice findByNumber(final StringID<Business> uidBusiness, final String number) {
        try {
            return new TransactionWrapper<PTInvoice>(this.daoInvoice) {

                @Override
                public PTInvoice runTransaction() throws Exception {
                    return PTInvoicePersistenceService.this.daoInvoice.findByNumber(uidBusiness, number);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
