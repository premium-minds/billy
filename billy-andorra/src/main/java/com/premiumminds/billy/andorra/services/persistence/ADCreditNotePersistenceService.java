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

import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditNote;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.persistence.services.PersistenceService;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNote;

public class ADCreditNotePersistenceService implements PersistenceService<GenericInvoice, ADCreditNote> {

    protected final DAOADCreditNote daoCreditNote;
    protected final DAOTicket daoTicket;

    @Inject
    public ADCreditNotePersistenceService(DAOADCreditNote daoCreditNote, DAOTicket daoTicket) {
        this.daoCreditNote = daoCreditNote;
        this.daoTicket = daoTicket;
    }

    @Override
    @NotImplemented
    public ADCreditNote create(final Builder<ADCreditNote> builder) {
        return null;
    }

    @Override
    public ADCreditNote update(final Builder<ADCreditNote> builder) {
        try {
            return new TransactionWrapper<ADCreditNote>(this.daoCreditNote) {

                @Override
                public ADCreditNote runTransaction() throws Exception {
                    ADCreditNoteEntity entity = (ADCreditNoteEntity) builder.build();
                    return ADCreditNotePersistenceService.this.daoCreditNote.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ADCreditNote get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<ADCreditNote>(this.daoCreditNote) {

                @Override
                public ADCreditNote runTransaction() throws Exception {
                    return ADCreditNotePersistenceService.this.daoCreditNote.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Deprecated
    public ADCreditNote getWithTicket(final StringID<Ticket> ticketUID) throws NoResultException, BillyRuntimeException {

        try {
            return new TransactionWrapper<ADCreditNote>(this.daoCreditNote) {

                @Override
                public ADCreditNote runTransaction() throws Exception {
                    StringID<GenericInvoice> objectUID =
                            ADCreditNotePersistenceService.this.daoTicket.getObjectEntityUID(ticketUID);
                    return ADCreditNotePersistenceService.this.daoCreditNote.get(objectUID);
                }

            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ADCreditNote findByNumber(final StringID<Business> uidBusiness, final String number) {
        try {
            return new TransactionWrapper<ADCreditNote>(this.daoCreditNote) {

                @Override
                public ADCreditNote runTransaction() throws Exception {
                    return ADCreditNotePersistenceService.this.daoCreditNote.findByNumber(uidBusiness, number);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public List<ADCreditNote> findByReferencedDocument(
        final StringID<Business> uidCompany,
        final StringID<GenericInvoice> uidInvoice)
    {
        try {
            return new TransactionWrapper<List<ADCreditNote>>(this.daoCreditNote) {

                @Override
                public List<ADCreditNote> runTransaction() throws Exception {
                    return ADCreditNotePersistenceService.this.daoCreditNote.findByReferencedDocument(uidCompany,
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
