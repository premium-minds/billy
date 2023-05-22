/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.persistence.dao.jpa;

import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADCreditNoteEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNoteEntry;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADCreditNoteEntity;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.jpa.impl.JPAQuery;

public class DAOADCreditNoteEntryImpl
        extends AbstractDAOADGenericInvoiceEntryImpl<ADCreditNoteEntryEntity, JPAADCreditNoteEntryEntity>
        implements DAOADCreditNoteEntry
{

    @Inject
    public DAOADCreditNoteEntryImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ADCreditNoteEntryEntity getEntityInstance() {
        return new JPAADCreditNoteEntryEntity();
    }

    @Override
    protected Class<JPAADCreditNoteEntryEntity> getEntityClass() {
        return JPAADCreditNoteEntryEntity.class;
    }

    @Override
    public ADCreditNoteEntity checkCreditNote(ADInvoice invoice) {
        QJPAADCreditNoteEntity creditNoteEntity = QJPAADCreditNoteEntity.jPAADCreditNoteEntity;

        return new JPAQuery<JPAADCreditNoteEntity>(this.getEntityManager())
                .from(creditNoteEntity)
                .where(new QJPAADCreditNoteEntryEntity(JPAADCreditNoteEntryEntity.class, creditNoteEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                        .invoiceReference.id.eq(invoice.getID()))
                .select(creditNoteEntity)
                .fetchFirst();
    }

    @Override
    public boolean existsCreditNote(ADInvoice invoice) {
        QJPAADCreditNoteEntity creditNoteEntity = QJPAADCreditNoteEntity.jPAADCreditNoteEntity;

        return new JPAQuery<JPAADCreditNoteEntity>(this.getEntityManager())
            .from(creditNoteEntity)
            .where(new QJPAADCreditNoteEntryEntity(JPAADCreditNoteEntryEntity.class, creditNoteEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                       .invoiceReference.id.eq(invoice.getID()))
            .select(creditNoteEntity.id)
            .fetchFirst() != null;
    }
}
