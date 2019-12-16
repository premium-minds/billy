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

import java.util.List;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.JPAPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.JPAPTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.query.QJPAPTCreditNoteEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

public class DAOPTCreditNoteEntryImpl
        extends AbstractDAOPTGenericInvoiceEntryImpl<PTCreditNoteEntryEntity, JPAPTCreditNoteEntryEntity>
        implements DAOPTCreditNoteEntry {

    @Override
    public PTCreditNoteEntryEntity getEntityInstance() {
        return new JPAPTCreditNoteEntryEntity();
    }

    @Override
    protected Class<JPAPTCreditNoteEntryEntity> getEntityClass() {
        return JPAPTCreditNoteEntryEntity.class;
    }

    @Override
    public PTCreditNoteEntity checkCreditNote(PTInvoice invoice) {
        List<JPAPTCreditNoteEntity> creditNotes = this.queryCreditNote().findList();

        // TODO make a query to do this
        for (JPAPTCreditNoteEntity creditNote : creditNotes) {
            for (PTCreditNoteEntry entry : creditNote.getEntries()) {
                if (entry.getReference().getNumber().compareTo(invoice.getNumber()) == 0) {
                    return creditNote;
                }
            }
        }
        return null;
    }

    private QJPAPTCreditNoteEntity queryCreditNote() {
        return new QJPAPTCreditNoteEntity();
    }
}
