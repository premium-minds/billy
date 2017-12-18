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
package com.premiumminds.billy.france.persistence.dao.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.JPASubQuery;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNote;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRCreditNoteEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRCreditNoteEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRCreditNoteEntryEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRGenericInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRCreditNote;

public class DAOFRCreditNoteImpl extends AbstractDAOFRGenericInvoiceImpl<FRCreditNoteEntity, JPAFRCreditNoteEntity>
        implements DAOFRCreditNote {

    @Inject
    public DAOFRCreditNoteImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public FRCreditNoteEntity getEntityInstance() {
        return new JPAFRCreditNoteEntity();
    }

    @Override
    protected Class<JPAFRCreditNoteEntity> getEntityClass() {
        return JPAFRCreditNoteEntity.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FRCreditNote> findByReferencedDocument(UID uidCompany, UID uidInvoice) {
        QJPAFRCreditNoteEntity creditNote = QJPAFRCreditNoteEntity.jPAFRCreditNoteEntity;
        QJPAFRCreditNoteEntryEntity entry = QJPAFRCreditNoteEntryEntity.jPAFRCreditNoteEntryEntity;
        QJPAFRGenericInvoiceEntity invoice = QJPAFRGenericInvoiceEntity.jPAFRGenericInvoiceEntity;

        JPASubQuery invQ = new JPASubQuery().from(invoice).where(invoice.uid.eq(uidInvoice.toString()));

        JPASubQuery entQ = new JPASubQuery().from(entry)
                .where(this.toDSL(entry.reference, QJPAFRGenericInvoiceEntity.class).uid.in(invQ.list(invoice.uid)));

        return (List<FRCreditNote>) (List<?>) this.createQuery().from(creditNote)
                .where(this.toDSL(creditNote.business, QJPAFRBusinessEntity.class).uid.eq(uidCompany.toString())
                        .and(this.toDSL(creditNote.entries.any(), QJPAFRCreditNoteEntryEntity.class).uid
                                .in(entQ.list(entry.uid))))
                .list(creditNote);
    }

}
