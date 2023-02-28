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

import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditReceipt;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceipt;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.NoResultException;

public class ADCreditReceiptPersistenceService implements PersistenceService<GenericInvoice, ADCreditReceipt> {

    protected final DAOADCreditReceipt daoCreditReceipt;
    protected final DAOTicket daoTicket;

    @Inject
    public ADCreditReceiptPersistenceService(DAOADCreditReceipt daoCreditReceipt, DAOTicket daoTicket) {
        this.daoCreditReceipt = daoCreditReceipt;
        this.daoTicket = daoTicket;
    }

    @Override
    @NotImplemented
    public ADCreditReceipt create(final Builder<ADCreditReceipt> builder) {
        return null;
    }

    @Override
    public ADCreditReceipt update(final Builder<ADCreditReceipt> builder) {
        try {
            return new TransactionWrapper<ADCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public ADCreditReceipt runTransaction() throws Exception {
                    ADCreditReceiptEntity entity = (ADCreditReceiptEntity) builder.build();
                    return ADCreditReceiptPersistenceService.this.daoCreditReceipt.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADCreditReceipt get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<ADCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public ADCreditReceipt runTransaction() throws Exception {
                    return ADCreditReceiptPersistenceService.this.daoCreditReceipt.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Deprecated
    public ADCreditReceipt getWithTicket(final StringID<Ticket> ticketUID) throws NoResultException, BillyRuntimeException {

        try {
            return new TransactionWrapper<ADCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public ADCreditReceipt runTransaction() throws Exception {
                    StringID<GenericInvoice> objectUID =
                            ADCreditReceiptPersistenceService.this.daoTicket.getObjectEntityUID(ticketUID);
                    return ADCreditReceiptPersistenceService.this.daoCreditReceipt.get(objectUID);
                }

            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ADCreditReceipt findByNumber(final StringID<Business> uidBusiness, final String number) {
        try {
            return new TransactionWrapper<ADCreditReceipt>(this.daoCreditReceipt) {

                @Override
                public ADCreditReceipt runTransaction() throws Exception {
                    return ADCreditReceiptPersistenceService.this.daoCreditReceipt.findByNumber(uidBusiness, number);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public List<ADCreditReceipt> findByReferencedDocument(final StringID<Business> uidCompany,
														  final StringID<GenericInvoice> uidInvoice) {
        try {
            return new TransactionWrapper<List<ADCreditReceipt>>(this.daoCreditReceipt) {

                @Override
                public List<ADCreditReceipt> runTransaction() throws Exception {
                    return ADCreditReceiptPersistenceService.this.daoCreditReceipt.findByReferencedDocument(uidCompany,
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
