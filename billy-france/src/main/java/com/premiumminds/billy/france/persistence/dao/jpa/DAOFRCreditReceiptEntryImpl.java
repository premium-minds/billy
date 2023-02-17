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

import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceiptEntry;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntryEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRCreditReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRCreditReceiptEntryEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRCreditReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRCreditReceiptEntryEntity;
import com.premiumminds.billy.france.services.entities.FRReceipt;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.jpa.impl.JPAQuery;

public class DAOFRCreditReceiptEntryImpl
        extends AbstractDAOFRGenericInvoiceEntryImpl<FRCreditReceiptEntryEntity, JPAFRCreditReceiptEntryEntity>
        implements DAOFRCreditReceiptEntry {

    @Inject
    public DAOFRCreditReceiptEntryImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public FRCreditReceiptEntryEntity getEntityInstance() {
        return new JPAFRCreditReceiptEntryEntity();
    }

    @Override
    protected Class<JPAFRCreditReceiptEntryEntity> getEntityClass() {
        return JPAFRCreditReceiptEntryEntity.class;
    }

    @Override
    public FRCreditReceiptEntity checkCreditReceipt(FRReceipt receipt) {
        QJPAFRCreditReceiptEntity creditReceiptEntity = QJPAFRCreditReceiptEntity.jPAFRCreditReceiptEntity;

        return new JPAQuery<JPAFRCreditReceiptEntity>(this.getEntityManager())
                .from(creditReceiptEntity)
                .where(new QJPAFRCreditReceiptEntryEntity(JPAFRCreditReceiptEntryEntity.class, creditReceiptEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                        .receiptReference.id.eq(receipt.getID()))
                .select(creditReceiptEntity)
                .fetchFirst();
    }
}
