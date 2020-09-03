/*
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
package com.premiumminds.billy.portugal.persistence.dao.ebean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.premiumminds.billy.core.persistence.entities.ebean.JPAGenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.query.QJPAGenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.query.QJPAGenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.JPAPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.JPAPTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.JPAPTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.query.QJPAPTCreditNoteEntryEntity;
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
    public List<PTCreditNoteEntity> getBusinessCreditNotesForSAFTPT(UID businessUid, Date from, Date to) {
        List<JPAGenericInvoiceEntity> invoices =
                this.queryInvoice().business.uid.eq(businessUid.toString()).date.between(from, to).findList();

        ArrayList<PTCreditNoteEntity> creditNotes = new ArrayList<>();
        for (JPAGenericInvoiceEntity invoice : invoices) {
            if (invoice instanceof JPAPTCreditNoteEntity) {
                creditNotes.add((JPAPTCreditNoteEntity) invoice);
            }
        }
        return creditNotes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PTCreditNote> findByReferencedDocument(UID uidCompany, UID uidInvoice) {
        JPAGenericInvoiceEntity invoice = this.queryInvoice().uid.eq(uidInvoice.toString()).findOne();
        if (invoice == null) {
            return new ArrayList<>();
        }

        List<JPAPTCreditNoteEntryEntity> referencingEntries =
                this.queryCreditNoteEntry().reference.equalTo((JPAPTInvoiceEntity) invoice).findList();
        List<Long> referencingEntryIDs = new ArrayList<>();
        for (JPAPTCreditNoteEntryEntity referencingEntry : referencingEntries) {
            referencingEntryIDs.add(referencingEntry.getID());
        }

        List<JPAGenericInvoiceEntity> referencingDocuments =
                this.queryInvoice().business.uid.eq(uidCompany.toString()).entries
                        .filterMany(this.queryGenericInvoiceEntry().setIdIn(referencingEntryIDs).getExpressionList())
                        .findList();

        List<PTCreditNote> referencingCreditNotes = new ArrayList<>();
        for (JPAGenericInvoiceEntity referencingInvoice : referencingDocuments) {
            if (referencingInvoice instanceof JPAPTCreditNoteEntity) {
                referencingCreditNotes.add((JPAPTCreditNoteEntity) referencingInvoice);
            }
        }
        return referencingCreditNotes;
    }

    private QJPAGenericInvoiceEntity queryInvoice() {
        return new QJPAGenericInvoiceEntity();
    }

    private QJPAGenericInvoiceEntryEntity queryGenericInvoiceEntry() {
        return new QJPAGenericInvoiceEntryEntity();
    }

    private QJPAPTCreditNoteEntryEntity queryCreditNoteEntry() {
        return new QJPAPTCreditNoteEntryEntity();
    }
}
