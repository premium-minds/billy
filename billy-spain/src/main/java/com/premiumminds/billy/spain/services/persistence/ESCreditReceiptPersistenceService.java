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

import java.util.List;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditReceipt;

public class ESCreditReceiptPersistenceService implements PersistenceService<GenericInvoice, ESCreditReceipt> {

    protected final DAOESCreditReceipt daoCreditReceipt;

    @Inject
    public ESCreditReceiptPersistenceService(DAOESCreditReceipt daoCreditReceipt) {
        this.daoCreditReceipt = daoCreditReceipt;
    }

    @Override
    @NotImplemented
    public ESCreditReceipt create(final Builder<ESCreditReceipt> builder) {
        return null;
    }

    @Override
    public ESCreditReceipt update(final Builder<ESCreditReceipt> builder) {
        try {
            return new TransactionWrapper<ESCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public ESCreditReceipt runTransaction() throws Exception {
                    ESCreditReceiptEntity entity = (ESCreditReceiptEntity) builder.build();
                    return ESCreditReceiptPersistenceService.this.daoCreditReceipt.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESCreditReceipt get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<ESCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public ESCreditReceipt runTransaction() throws Exception {
                    return ESCreditReceiptPersistenceService.this.daoCreditReceipt.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ESCreditReceipt findByNumber(final StringID<Business> uidBusiness, final String number) {
        try {
            return new TransactionWrapper<ESCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public ESCreditReceipt runTransaction() throws Exception {
                    return ESCreditReceiptPersistenceService.this.daoCreditReceipt.findByNumber(uidBusiness, number);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public List<ESCreditReceipt> findByReferencedDocument(final StringID<Business> uidCompany,
            final StringID<GenericInvoice> uidInvoice) {
        try {
            return new TransactionWrapper<List<ESCreditReceipt>>(this.daoCreditReceipt) {

                @Override
                public List<ESCreditReceipt> runTransaction() throws Exception {
                    return ESCreditReceiptPersistenceService.this.daoCreditReceipt.findByReferencedDocument(uidCompany,
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
