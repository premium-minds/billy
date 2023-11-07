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

import java.util.List;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNote;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntity;
import com.premiumminds.billy.france.services.entities.FRCreditNote;
import com.premiumminds.billy.persistence.services.PersistenceService;

public class FRCreditNotePersistenceService implements PersistenceService<GenericInvoice, FRCreditNote> {

    protected final DAOFRCreditNote daoCreditNote;

    @Inject
    public FRCreditNotePersistenceService(DAOFRCreditNote daoCreditNote) {
        this.daoCreditNote = daoCreditNote;
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
    public FRCreditNote get(final StringID<GenericInvoice> uid) {
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

    public FRCreditNote findByNumber(final StringID<Business> uidBusiness, final String number) {
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

    public List<FRCreditNote> findByReferencedDocument(final StringID<Business> uidCompany, final StringID<GenericInvoice> uidInvoice) {
        try {
            return new TransactionWrapper<List<FRCreditNote>>(this.daoCreditNote) {

                @Override
                public List<FRCreditNote> runTransaction() throws Exception {
                    return FRCreditNotePersistenceService.this.daoCreditNote.findByReferencedDocument(
                        uidCompany,
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
