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
package com.premiumminds.billy.france.persistence.dao.jpa;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNoteEntry;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntity;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntryEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRCreditNoteEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRCreditNoteEntryEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRCreditNoteEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRCreditNoteEntryEntity;
import com.premiumminds.billy.france.services.entities.FRInvoice;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.jpa.impl.JPAQuery;

public class DAOFRCreditNoteEntryImpl
        extends AbstractDAOFRGenericInvoiceEntryImpl<FRCreditNoteEntryEntity, JPAFRCreditNoteEntryEntity>
        implements DAOFRCreditNoteEntry {

    @Inject
    public DAOFRCreditNoteEntryImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public FRCreditNoteEntryEntity getEntityInstance() {
        return new JPAFRCreditNoteEntryEntity();
    }

    @Override
    protected Class<JPAFRCreditNoteEntryEntity> getEntityClass() {
        return JPAFRCreditNoteEntryEntity.class;
    }

    @Override
    public FRCreditNoteEntity checkCreditNote(FRInvoice invoice) {
        QJPAFRCreditNoteEntity creditNoteEntity = QJPAFRCreditNoteEntity.jPAFRCreditNoteEntity;

        return new JPAQuery<JPAFRCreditNoteEntity>(this.getEntityManager())
                .from(creditNoteEntity)
                .where(new QJPAFRCreditNoteEntryEntity(JPAFRCreditNoteEntryEntity.class, creditNoteEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                        .invoiceReference.id.eq(invoice.getID()))
                .select(creditNoteEntity)
                .fetchFirst();
    }

    @Override
    public boolean existsCreditNote(FRInvoice invoice) {
        QJPAFRCreditNoteEntity creditNoteEntity = QJPAFRCreditNoteEntity.jPAFRCreditNoteEntity;

        return new JPAQuery<JPAFRCreditNoteEntity>(this.getEntityManager())
            .from(creditNoteEntity)
            .where(new QJPAFRCreditNoteEntryEntity(JPAFRCreditNoteEntryEntity.class, creditNoteEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                       .invoiceReference.id.eq(invoice.getID()))
            .select(creditNoteEntity.id)
            .fetchFirst() != null;
    }
}
