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
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESSimpleInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESSimpleInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice;
import javax.inject.Inject;
import javax.persistence.NoResultException;

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

    public ESSimpleInvoice get(final StringID<GenericInvoice> uid) {
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

    @Deprecated
    public ESSimpleInvoice getWithTicket(final StringID<Ticket> ticketUID) {

        try {
            return new TransactionWrapper<ESSimpleInvoice>(this.daoInvoice) {

                @Override
                public ESSimpleInvoice runTransaction() throws NoResultException, BillyRuntimeException {
                    StringID<GenericInvoice> objectUID =
                            ESSimpleInvoicePersistenceService.this.daoTicket.getObjectEntityUID(ticketUID);
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
