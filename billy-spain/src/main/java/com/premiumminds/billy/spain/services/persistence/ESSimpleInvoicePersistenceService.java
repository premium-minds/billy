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

import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESSimpleInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESSimpleInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice;

public class ESSimpleInvoicePersistenceService {

    protected final DAOESSimpleInvoice daoInvoice;
    protected final DAOTicket daoTicket;

    @Inject
    public ESSimpleInvoicePersistenceService(DAOESSimpleInvoice daoInvoice, DAOTicket daoTicket) {
        this.daoInvoice = daoInvoice;
        this.daoTicket = daoTicket;
    }

    public ESSimpleInvoice update(final Builder<ESSimpleInvoice> builder) {
        try {
            return new TransactionWrapper<ESSimpleInvoice>(this.daoInvoice) {

                @Override
                public ESSimpleInvoice runTransaction() throws Exception {
                    ESSimpleInvoiceEntity entity = (ESSimpleInvoiceEntity) builder.build();
                    return ESSimpleInvoicePersistenceService.this.daoInvoice.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ESSimpleInvoice get(final UID uid) {
        try {
            return new TransactionWrapper<ESSimpleInvoice>(this.daoInvoice) {

                @Override
                public ESSimpleInvoice runTransaction() throws Exception {
                    return ESSimpleInvoicePersistenceService.this.daoInvoice.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ESSimpleInvoice getWithTicket(final UID ticketUID) {

        try {
            return new TransactionWrapper<ESSimpleInvoice>(this.daoInvoice) {

                @Override
                public ESSimpleInvoice runTransaction() throws NoResultException, BillyRuntimeException {
                    UID objectUID =
                            ESSimpleInvoicePersistenceService.this.daoTicket.getObjectEntityUID(ticketUID.getValue());
                    return ESSimpleInvoicePersistenceService.this.daoInvoice.get(objectUID);
                }

            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
