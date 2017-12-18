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
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNote;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntity;
import com.premiumminds.billy.france.services.entities.FRCreditNote;

public class FRCreditNotePersistenceService implements PersistenceService<FRCreditNote> {

    protected final DAOFRCreditNote daoCreditNote;
    protected final DAOTicket daoTicket;

    @Inject
    public FRCreditNotePersistenceService(DAOFRCreditNote daoCreditNote, DAOTicket daoTicket) {
        this.daoCreditNote = daoCreditNote;
        this.daoTicket = daoTicket;
    }

    @Override
    @NotImplemented
    public FRCreditNote create(final Builder<FRCreditNote> builder) {
        return null;
    }

    @Override
    public FRCreditNote update(final Builder<FRCreditNote> builder) {
        try {
            return new TransactionWrapper<FRCreditNote>(this.daoCreditNote) {

                @Override
                public FRCreditNote runTransaction() throws Exception {
                    FRCreditNoteEntity entity = (FRCreditNoteEntity) builder.build();
                    return FRCreditNotePersistenceService.this.daoCreditNote.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public FRCreditNote get(final UID uid) {
        try {
            return new TransactionWrapper<FRCreditNote>(this.daoCreditNote) {

                @Override
                public FRCreditNote runTransaction() throws Exception {
                    return FRCreditNotePersistenceService.this.daoCreditNote.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public FRCreditNote getWithTicket(final UID ticketUID) throws NoResultException, BillyRuntimeException {

        try {
            return new TransactionWrapper<FRCreditNote>(this.daoCreditNote) {

                @Override
                public FRCreditNote runTransaction() throws Exception {
                    UID objectUID =
                            FRCreditNotePersistenceService.this.daoTicket.getObjectEntityUID(ticketUID.getValue());
                    return FRCreditNotePersistenceService.this.daoCreditNote.get(objectUID);
                }

            }.execute();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public FRCreditNote findByNumber(final UID uidBusiness, final String number) {
        try {
            return new TransactionWrapper<FRCreditNote>(this.daoCreditNote) {

                @Override
                public FRCreditNote runTransaction() throws Exception {
                    return FRCreditNotePersistenceService.this.daoCreditNote.findByNumber(uidBusiness, number);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public List<FRCreditNote> findByReferencedDocument(final UID uidCompany, final UID uidInvoice) {
        try {
            return new TransactionWrapper<List<FRCreditNote>>(this.daoCreditNote) {

                @Override
                public List<FRCreditNote> runTransaction() throws Exception {
                    return FRCreditNotePersistenceService.this.daoCreditNote.findByReferencedDocument(uidCompany,
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
