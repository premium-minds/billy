/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;

public class DAOPTCreditNoteImpl extends AbstractDAOPTGenericInvoiceImpl<PTCreditNoteEntity, JPAPTCreditNoteEntity>
        implements DAOPTCreditNote {

    @Inject
    public DAOPTCreditNoteImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public PTCreditNoteEntity getEntityInstance() {
        return new JPAPTCreditNoteEntity();
    }

    @Override
    protected Class<JPAPTCreditNoteEntity> getEntityClass() {
        return JPAPTCreditNoteEntity.class;
    }

    @Override
    public List<PTCreditNoteEntity> getBusinessCreditNotesForSAFTPT(StringID<Business> uid, Date from, Date to) {
        QJPAPTCreditNoteEntity creditNote = QJPAPTCreditNoteEntity.jPAPTCreditNoteEntity;

        JPAQuery<PTCreditNoteEntity> query = this.createQuery();

        query.from(creditNote)
                .where(creditNote.instanceOf(JPAPTCreditNoteEntity.class).and(creditNote.date.between(from, to))
                        .and(this.toDSL(creditNote.business, QJPAPTBusinessEntity.class).uid.eq(uid.toString())));

        return this.checkEntityList(query.select(creditNote).fetch(), PTCreditNoteEntity.class);
    }

    @Override
    public List<PTCreditNote> findByReferencedDocument(StringID<Business> uidCompany, StringID<GenericInvoice> uidInvoice) {
        QJPAPTCreditNoteEntity creditNote = QJPAPTCreditNoteEntity.jPAPTCreditNoteEntity;
        QJPAPTCreditNoteEntryEntity entry = QJPAPTCreditNoteEntryEntity.jPAPTCreditNoteEntryEntity;
        QJPAPTGenericInvoiceEntity invoice = QJPAPTGenericInvoiceEntity.jPAPTGenericInvoiceEntity;

        final JPQLQuery<String> invQ = JPAExpressions
            .select(invoice.uid)
            .from(invoice)
            .where(invoice.uid.eq(uidInvoice.toString()));

        final JPQLQuery<String> entQ = JPAExpressions
            .select(entry.uid)
            .from(entry)
            .where(this.toDSL(entry.reference, QJPAPTGenericInvoiceEntity.class).uid.in(invQ));

        return new ArrayList<>(this
            .createQuery()
            .from(creditNote)
            .where(this.toDSL(creditNote.business, QJPAPTBusinessEntity.class).uid
                       .eq(uidCompany.toString())
                       .and(this.toDSL(creditNote.entries.any(), QJPAPTCreditNoteEntryEntity.class).uid.in(entQ)))
            .select(creditNote)
            .fetch());
    }

}
