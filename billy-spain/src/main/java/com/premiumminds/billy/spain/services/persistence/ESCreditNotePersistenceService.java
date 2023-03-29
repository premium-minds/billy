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
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;

public class ESCreditNotePersistenceService implements PersistenceService<GenericInvoice, ESCreditNote> {

    protected final DAOESCreditNote daoCreditNote;

    @Inject
    public ESCreditNotePersistenceService(DAOESCreditNote daoCreditNote) {
        this.daoCreditNote = daoCreditNote;
    }

    @Override
    @NotImplemented
    public ESCreditNote create(final Builder<ESCreditNote> builder) {
        return null;
    }

    @Override
    public ESCreditNote update(final Builder<ESCreditNote> builder) {
        try {
            return new TransactionWrapper<ESCreditNote>(this.daoCreditNote) {

                @Override
                public ESCreditNote runTransaction() throws Exception {
                    ESCreditNoteEntity entity = (ESCreditNoteEntity) builder.build();
                    return ESCreditNotePersistenceService.this.daoCreditNote.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    @Override
    public ESCreditNote get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<ESCreditNote>(this.daoCreditNote) {

                @Override
                public ESCreditNote runTransaction() throws Exception {
                    return ESCreditNotePersistenceService.this.daoCreditNote.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ESCreditNote findByNumber(final StringID<Business> uidBusiness, final String number) {
        try {
            return new TransactionWrapper<ESCreditNote>(this.daoCreditNote) {

                @Override
                public ESCreditNote runTransaction() throws Exception {
                    return ESCreditNotePersistenceService.this.daoCreditNote.findByNumber(uidBusiness, number);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public List<ESCreditNote> findByReferencedDocument(
        final StringID<Business> uidCompany,
        final StringID<GenericInvoice> uidInvoice)
    {
        try {
            return new TransactionWrapper<List<ESCreditNote>>(this.daoCreditNote) {

                @Override
                public List<ESCreditNote> runTransaction() throws Exception {
                    return ESCreditNotePersistenceService.this.daoCreditNote.findByReferencedDocument(uidCompany,
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
