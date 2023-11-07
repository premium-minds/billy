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
package com.premiumminds.billy.spain.persistence.dao.jpa;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;

import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNoteEntry;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.services.entities.ESInvoice;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.jpa.impl.JPAQuery;

public class DAOESCreditNoteEntryImpl
        extends AbstractDAOESGenericInvoiceEntryImpl<ESCreditNoteEntryEntity, JPAESCreditNoteEntryEntity>
        implements DAOESCreditNoteEntry {

    @Inject
    public DAOESCreditNoteEntryImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ESCreditNoteEntryEntity getEntityInstance() {
        return new JPAESCreditNoteEntryEntity();
    }

    @Override
    protected Class<JPAESCreditNoteEntryEntity> getEntityClass() {
        return JPAESCreditNoteEntryEntity.class;
    }

    @Override
    public ESCreditNoteEntity checkCreditNote(ESInvoice invoice) {
        QJPAESCreditNoteEntity creditNoteEntity = QJPAESCreditNoteEntity.jPAESCreditNoteEntity;

        return new JPAQuery<JPAESCreditNoteEntity>(this.getEntityManager())
                .from(creditNoteEntity)
                .where(new QJPAESCreditNoteEntryEntity(JPAESCreditNoteEntryEntity.class, creditNoteEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                        .invoiceReference.id.eq(invoice.getID()))
                .select(creditNoteEntity)
                .fetchFirst();
    }

    @Override
    public boolean existsCreditNote(ESInvoice invoice) {
        QJPAESCreditNoteEntity creditNoteEntity = QJPAESCreditNoteEntity.jPAESCreditNoteEntity;

        return new JPAQuery<JPAESCreditNoteEntity>(this.getEntityManager())
            .from(creditNoteEntity)
            .where(new QJPAESCreditNoteEntryEntity(JPAESCreditNoteEntryEntity.class, creditNoteEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                       .invoiceReference.id.eq(invoice.getID()))
            .select(creditNoteEntity.id)
            .fetchFirst() != null;
    }
}
