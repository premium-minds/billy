/**
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

import java.util.Date;
import java.util.List;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTCreditNoteEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;

public class DAOPTCreditNoteImpl extends AbstractDAOPTGenericInvoiceImpl<PTCreditNoteEntity, JPAPTCreditNoteEntity>
        implements DAOPTCreditNote {

    @Override
    public PTCreditNoteEntity getEntityInstance() {
        return new JPAPTCreditNoteEntity();
    }

    @Override
    protected Class<JPAPTCreditNoteEntity> getEntityClass() {
        return JPAPTCreditNoteEntity.class;
    }

    @Override
    public List<PTCreditNoteEntity> getBusinessCreditNotesForSAFTPT(UID uid, Date from, Date to) {
        /*QJPAPTCreditNoteEntity creditNote = QJPAPTCreditNoteEntity.jPAPTCreditNoteEntity;
        
        JPAQuery query = this.createQuery();
        
        query.from(creditNote)
                .where(creditNote.instanceOf(JPAPTCreditNoteEntity.class).and(creditNote.date.between(from, to))
                        .and(this.toDSL(creditNote.business, QJPAPTBusinessEntity.class).uid.eq(uid.toString())));
        
        List<PTCreditNoteEntity> result = this.checkEntityList(query.list(creditNote), PTCreditNoteEntity.class);
        return result;*/
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PTCreditNote> findByReferencedDocument(UID uidCompany, UID uidInvoice) {
        /*QJPAPTCreditNoteEntity creditNote = QJPAPTCreditNoteEntity.jPAPTCreditNoteEntity;
        QJPAPTCreditNoteEntryEntity entry = QJPAPTCreditNoteEntryEntity.jPAPTCreditNoteEntryEntity;
        QJPAPTGenericInvoiceEntity invoice = QJPAPTGenericInvoiceEntity.jPAPTGenericInvoiceEntity;

        JPASubQuery invQ = new JPASubQuery().from(invoice).where(invoice.uid.eq(uidInvoice.toString()));

        JPASubQuery entQ = new JPASubQuery().from(entry)
                .where(this.toDSL(entry.reference, QJPAPTGenericInvoiceEntity.class).uid.in(invQ.list(invoice.uid)));

        return (List<PTCreditNote>) (List<?>) this.createQuery().from(creditNote)
                .where(this.toDSL(creditNote.business, QJPAPTBusinessEntity.class).uid.eq(uidCompany.toString())
                        .and(this.toDSL(creditNote.entries.any(), QJPAPTCreditNoteEntryEntity.class).uid
                                .in(entQ.list(entry.uid))))
                .list(creditNote);*/
        return null;
    }

}
