/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.persistence;

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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;

public class PTCreditNotePersistenceService implements PersistenceService<PTCreditNote> {

    protected final DAOPTCreditNote daoCreditNote;
    protected final DAOTicket daoTicket;

    @Inject
    public PTCreditNotePersistenceService(DAOPTCreditNote daoCreditNote, DAOTicket daoTicket) {
        this.daoCreditNote = daoCreditNote;
        this.daoTicket = daoTicket;
    }

    @Override
    @NotImplemented
    public PTCreditNote create(final Builder<PTCreditNote> builder) {
        return null;
    }

    @Override
    public PTCreditNote update(final Builder<PTCreditNote> builder) {
        try {
            return new TransactionWrapper<PTCreditNote>(this.daoCreditNote) {

                @Override
                public PTCreditNote runTransaction() throws Exception {
                    PTCreditNoteEntity entity = (PTCreditNoteEntity) builder.build();
                    return PTCreditNotePersistenceService.this.daoCreditNote.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public PTCreditNote get(final UID uid) {
        try {
            return new TransactionWrapper<PTCreditNote>(this.daoCreditNote) {

                @Override
                public PTCreditNote runTransaction() throws Exception {
                    return PTCreditNotePersistenceService.this.daoCreditNote.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public PTCreditNote getWithTicket(final UID ticketUID) throws NoResultException, BillyRuntimeException {

        try {
            return new TransactionWrapper<PTCreditNote>(this.daoCreditNote) {

                @Override
                public PTCreditNote runTransaction() throws Exception {
                    UID objectUID =
                            PTCreditNotePersistenceService.this.daoTicket.getObjectEntityUID(ticketUID.getValue());
                    return PTCreditNotePersistenceService.this.daoCreditNote.get(objectUID);
                }

            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public PTCreditNote findByNumber(final UID uidBusiness, final String number) {
        try {
            return new TransactionWrapper<PTCreditNote>(this.daoCreditNote) {

                @Override
                public PTCreditNote runTransaction() throws Exception {
                    return PTCreditNotePersistenceService.this.daoCreditNote.findByNumber(uidBusiness, number);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public List<PTCreditNote> findByReferencedDocument(final UID uidCompany, final UID uidInvoice) {
        try {
            return new TransactionWrapper<List<PTCreditNote>>(this.daoCreditNote) {

                @Override
                public List<PTCreditNote> runTransaction() throws Exception {
                    return PTCreditNotePersistenceService.this.daoCreditNote.findByReferencedDocument(uidCompany,
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
