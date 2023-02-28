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

import com.premiumminds.billy.andorra.persistence.entities.ADSimpleInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSimpleInvoice;

public class ADSimpleInvoicePersistenceService {

    protected final DAOADSimpleInvoice daoInvoice;
    protected final DAOTicket daoTicket;

    @Inject
    public ADSimpleInvoicePersistenceService(DAOADSimpleInvoice daoInvoice, DAOTicket daoTicket) {
        this.daoInvoice = daoInvoice;
        this.daoTicket = daoTicket;
    }

    public ADSimpleInvoice update(final Builder<ADSimpleInvoice> builder) {
        try {
            return new TransactionWrapper<ADSimpleInvoice>(this.daoInvoice) {

                @Override
                public ADSimpleInvoice runTransaction() throws Exception {
                    ADSimpleInvoiceEntity entity = (ADSimpleInvoiceEntity) builder.build();
                    return ADSimpleInvoicePersistenceService.this.daoInvoice.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ADSimpleInvoice get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<ADSimpleInvoice>(this.daoInvoice) {

                @Override
                public ADSimpleInvoice runTransaction() throws Exception {
                    return ADSimpleInvoicePersistenceService.this.daoInvoice.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Deprecated
    public ADSimpleInvoice getWithTicket(final StringID<Ticket> ticketUID) {

        try {
            return new TransactionWrapper<ADSimpleInvoice>(this.daoInvoice) {

                @Override
                public ADSimpleInvoice runTransaction() throws NoResultException, BillyRuntimeException {
                    StringID<GenericInvoice> objectUID =
                            ADSimpleInvoicePersistenceService.this.daoTicket.getObjectEntityUID(ticketUID);
                    return ADSimpleInvoicePersistenceService.this.daoInvoice.get(objectUID);
                }

            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
