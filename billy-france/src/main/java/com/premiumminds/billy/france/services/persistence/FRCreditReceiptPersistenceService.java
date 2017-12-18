/**
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

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceipt;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntity;
import com.premiumminds.billy.france.services.entities.FRCreditReceipt;

public class FRCreditReceiptPersistenceService implements PersistenceService<FRCreditReceipt> {

    protected final DAOFRCreditReceipt daoCreditReceipt;
    protected final DAOTicket daoTicket;

    @Inject
    public FRCreditReceiptPersistenceService(DAOFRCreditReceipt daoCreditReceipt, DAOTicket daoTicket) {
        this.daoCreditReceipt = daoCreditReceipt;
        this.daoTicket = daoTicket;
    }

    @Override
    @NotImplemented
    public FRCreditReceipt create(final Builder<FRCreditReceipt> builder) {
        return null;
    }

    @Override
    public FRCreditReceipt update(final Builder<FRCreditReceipt> builder) {
        try {
            return new TransactionWrapper<FRCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public FRCreditReceipt runTransaction() throws Exception {
                    FRCreditReceiptEntity entity = (FRCreditReceiptEntity) builder.build();
                    return FRCreditReceiptPersistenceService.this.daoCreditReceipt.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRCreditReceipt get(final UID uid) {
        try {
            return new TransactionWrapper<FRCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public FRCreditReceipt runTransaction() throws Exception {
                    return FRCreditReceiptPersistenceService.this.daoCreditReceipt.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public FRCreditReceipt getWithTicket(final UID ticketUID) throws NoResultException, BillyRuntimeException {

        try {
            return new TransactionWrapper<FRCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public FRCreditReceipt runTransaction() throws Exception {
                    UID objectUID =
                            FRCreditReceiptPersistenceService.this.daoTicket.getObjectEntityUID(ticketUID.getValue());
                    return FRCreditReceiptPersistenceService.this.daoCreditReceipt.get(objectUID);
                }

            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public FRCreditReceipt findByNumber(final UID uidBusiness, final String number) {
        try {
            return new TransactionWrapper<FRCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public FRCreditReceipt runTransaction() throws Exception {
                    return FRCreditReceiptPersistenceService.this.daoCreditReceipt.findByNumber(uidBusiness, number);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public List<FRCreditReceipt> findByReferencedDocument(final UID uidCompany, final UID uidInvoice) {
        try {
            return new TransactionWrapper<List<FRCreditReceipt>>(this.daoCreditReceipt) {

                @Override
                public List<FRCreditReceipt> runTransaction() throws Exception {
                    return FRCreditReceiptPersistenceService.this.daoCreditReceipt.findByReferencedDocument(uidCompany,
                            uidInvoice);
                }

            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
