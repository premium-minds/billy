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
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.entities.ESReceipt;

public class ESReceiptPersistenceService {

    private final DAOESReceipt daoReceipt;
    private final DAOTicket daoTicket;

    @Inject
    public ESReceiptPersistenceService(DAOESReceipt daoReceipt, DAOTicket daoTicket) {
        this.daoReceipt = daoReceipt;
        this.daoTicket = daoTicket;
    }

    public ESReceipt update(final Builder<ESReceipt> builder) {
        try {
            return new TransactionWrapper<ESReceipt>(this.daoReceipt) {

                @Override
                public ESReceipt runTransaction() throws Exception {
                    ESReceiptEntity entity = (ESReceiptEntity) builder.build();
                    return ESReceiptPersistenceService.this.daoReceipt.update(entity);
                }
            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ESReceipt get(final UID uid) {
        try {
            return new TransactionWrapper<ESReceipt>(this.daoReceipt) {

                @Override
                public ESReceipt runTransaction() throws Exception {
                    return ESReceiptPersistenceService.this.daoReceipt.get(uid);
                }
            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Deprecated
    public ESReceipt getWithTicket(final UID ticketUID) throws NoResultException {
        try {
            return new TransactionWrapper<ESReceipt>(this.daoReceipt) {

                @Override
                public ESReceipt runTransaction() throws Exception {
                    UID receiptUID =
                            ESReceiptPersistenceService.this.daoTicket.getObjectEntityUID(ticketUID.getValue());
                    return ESReceiptPersistenceService.this.daoReceipt.get(receiptUID);
                }
            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ESReceipt findByNumber(final UID uidBusiness, final String number) {
        try {
            return new TransactionWrapper<ESReceipt>(this.daoReceipt) {

                @Override
                public ESReceipt runTransaction() throws Exception {
                    return ESReceiptPersistenceService.this.daoReceipt.findByNumber(uidBusiness, number);
                }
            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }
}
