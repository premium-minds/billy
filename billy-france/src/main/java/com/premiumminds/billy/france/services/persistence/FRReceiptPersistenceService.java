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

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.entities.FRReceipt;

public class FRReceiptPersistenceService {

    private final DAOFRReceipt daoReceipt;
    private final DAOTicket daoTicket;

    @Inject
    public FRReceiptPersistenceService(DAOFRReceipt daoReceipt, DAOTicket daoTicket) {
        this.daoReceipt = daoReceipt;
        this.daoTicket = daoTicket;
    }

    public FRReceipt update(final Builder<FRReceipt> builder) {
        try {
            return new TransactionWrapper<FRReceipt>(this.daoReceipt) {

                @Override
                public FRReceipt runTransaction() throws Exception {
                    FRReceiptEntity entity = (FRReceiptEntity) builder.build();
                    return FRReceiptPersistenceService.this.daoReceipt.update(entity);
                }
            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public FRReceipt get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<FRReceipt>(this.daoReceipt) {

                @Override
                public FRReceipt runTransaction() throws Exception {
                    return FRReceiptPersistenceService.this.daoReceipt.get(uid);
                }
            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Deprecated
    public FRReceipt getWithTicket(final StringID<Ticket> ticketUID) throws NoResultException {
        try {
            return new TransactionWrapper<FRReceipt>(this.daoReceipt) {

                @Override
                public FRReceipt runTransaction() {
                    StringID<GenericInvoice> receiptUID =
                            FRReceiptPersistenceService.this.daoTicket.getObjectEntityUID(ticketUID);
                    return FRReceiptPersistenceService.this.daoReceipt.get(receiptUID);
                }
            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public FRReceipt findByNumber(final StringID<Business> uidBusiness, final String number) {
        try {
            return new TransactionWrapper<FRReceipt>(this.daoReceipt) {

                @Override
                public FRReceipt runTransaction() {
                    return FRReceiptPersistenceService.this.daoReceipt.findByNumber(uidBusiness, number);
                }
            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }
}
