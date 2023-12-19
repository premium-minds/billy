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

import jakarta.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTReceiptInvoice;

public class PTReceiptInvoicePersistenceService implements PersistenceService<GenericInvoice, PTReceiptInvoice> {

    protected final DAOPTReceiptInvoice daoReceiptInvoice;

    @Inject
    public PTReceiptInvoicePersistenceService(DAOPTReceiptInvoice daoReceiptInvoice) {
        this.daoReceiptInvoice = daoReceiptInvoice;
    }

    @Override
    @NotImplemented
    public PTReceiptInvoice create(final Builder<PTReceiptInvoice> builder) {
        return null;
    }

    @NotImplemented
    @Override
    public PTReceiptInvoice update(final Builder<PTReceiptInvoice> builder) {
        try {
            return new TransactionWrapper<PTReceiptInvoice>(this.daoReceiptInvoice) {

                @Override
                public PTReceiptInvoice runTransaction() throws Exception {
                    PTReceiptInvoiceEntity entity = (PTReceiptInvoiceEntity) builder.build();
                    return PTReceiptInvoicePersistenceService.this.daoReceiptInvoice.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTReceiptInvoice get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<PTReceiptInvoice>(this.daoReceiptInvoice) {

                @Override
                public PTReceiptInvoice runTransaction() throws Exception {
                    return PTReceiptInvoicePersistenceService.this.daoReceiptInvoice.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
