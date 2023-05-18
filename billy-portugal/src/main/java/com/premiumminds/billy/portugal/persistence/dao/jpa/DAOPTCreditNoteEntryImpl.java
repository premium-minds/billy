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

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.jpa.impl.JPAQuery;

public class DAOPTCreditNoteEntryImpl
        extends AbstractDAOPTGenericInvoiceEntryImpl<PTCreditNoteEntryEntity, JPAPTCreditNoteEntryEntity>
        implements DAOPTCreditNoteEntry {

    @Inject
    public DAOPTCreditNoteEntryImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

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

        QJPAPTCreditNoteEntity creditNoteEntity = QJPAPTCreditNoteEntity.jPAPTCreditNoteEntity;

        return new JPAQuery<JPAPTCreditNoteEntity>(this.getEntityManager())
                .from(creditNoteEntity)
                .where(new QJPAPTCreditNoteEntryEntity(JPAPTCreditNoteEntryEntity.class, creditNoteEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                        .reference.id.eq(invoice.getID()))
                .select(creditNoteEntity)
                .fetchFirst();
    }

    @Override
    public boolean existsCreditNote(PTInvoice invoice) {

        QJPAPTCreditNoteEntity creditNoteEntity = QJPAPTCreditNoteEntity.jPAPTCreditNoteEntity;

        return new JPAQuery<JPAPTCreditNoteEntity>(this.getEntityManager())
            .from(creditNoteEntity)
            .where(new QJPAPTCreditNoteEntryEntity(JPAPTCreditNoteEntryEntity.class, creditNoteEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                       .reference.id.eq(invoice.getID()))
            .select(creditNoteEntity.id)
            .fetchFirst() != null;
    }
}
