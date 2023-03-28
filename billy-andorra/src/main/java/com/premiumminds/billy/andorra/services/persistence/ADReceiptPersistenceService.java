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

import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.services.entities.ADReceipt;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import javax.inject.Inject;
import javax.persistence.NoResultException;

public class ADReceiptPersistenceService {

    private final DAOADReceipt daoReceipt;
    private final DAOTicket daoTicket;

    @Inject
    public ADReceiptPersistenceService(DAOADReceipt daoReceipt, DAOTicket daoTicket) {
        this.daoReceipt = daoReceipt;
        this.daoTicket = daoTicket;
    }

    public ADReceipt update(final Builder<ADReceipt> builder) {
        try {
            return new TransactionWrapper<ADReceipt>(this.daoReceipt) {

                @Override
                public ADReceipt runTransaction() throws Exception {
                    ADReceiptEntity entity = (ADReceiptEntity) builder.build();
                    return ADReceiptPersistenceService.this.daoReceipt.update(entity);
                }
            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ADReceipt get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<ADReceipt>(this.daoReceipt) {

                @Override
                public ADReceipt runTransaction() throws Exception {
                    return ADReceiptPersistenceService.this.daoReceipt.get(uid);
                }
            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Deprecated
    public ADReceipt getWithTicket(final StringID<Ticket> ticketUID) throws NoResultException {
        try {
            return new TransactionWrapper<ADReceipt>(this.daoReceipt) {

                @Override
                public ADReceipt runTransaction() throws Exception {
                    StringID<GenericInvoice> receiptUID =
                            ADReceiptPersistenceService.this.daoTicket.getObjectEntityUID(ticketUID);
                    return ADReceiptPersistenceService.this.daoReceipt.get(receiptUID);
                }
            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ADReceipt findByNumber(final StringID<Business> uidBusiness, final String number) {
        try {
            return new TransactionWrapper<ADReceipt>(this.daoReceipt) {

                @Override
                public ADReceipt runTransaction() throws Exception {
                    return ADReceiptPersistenceService.this.daoReceipt.findByNumber(uidBusiness, number);
                }
            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }
}
