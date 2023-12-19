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

import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceiptEntry;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESCreditReceiptEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESCreditReceiptEntryEntity;
import com.premiumminds.billy.spain.services.entities.ESReceipt;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.jpa.impl.JPAQuery;

public class DAOESCreditReceiptEntryImpl
        extends AbstractDAOESGenericInvoiceEntryImpl<ESCreditReceiptEntryEntity, JPAESCreditReceiptEntryEntity>
        implements DAOESCreditReceiptEntry {

    @Inject
    public DAOESCreditReceiptEntryImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ESCreditReceiptEntryEntity getEntityInstance() {
        return new JPAESCreditReceiptEntryEntity();
    }

    @Override
    protected Class<JPAESCreditReceiptEntryEntity> getEntityClass() {
        return JPAESCreditReceiptEntryEntity.class;
    }

    @Override
    public ESCreditReceiptEntity checkCreditReceipt(ESReceipt receipt) {
        QJPAESCreditReceiptEntity creditReceiptEntity = QJPAESCreditReceiptEntity.jPAESCreditReceiptEntity;

        return new JPAQuery<JPAESCreditReceiptEntity>(this.getEntityManager())
                .from(creditReceiptEntity)
                .where(new QJPAESCreditReceiptEntryEntity(JPAESCreditReceiptEntryEntity.class, creditReceiptEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                        .receiptReference.id.eq(receipt.getID()))
                .select(creditReceiptEntity)
                .fetchFirst();
    }

    @Override
    public boolean existsCreditReceipt(ESReceipt receipt) {
        QJPAESCreditReceiptEntity creditReceiptEntity = QJPAESCreditReceiptEntity.jPAESCreditReceiptEntity;

        return new JPAQuery<JPAESCreditReceiptEntity>(this.getEntityManager())
                .from(creditReceiptEntity)
                .where(new QJPAESCreditReceiptEntryEntity(JPAESCreditReceiptEntryEntity.class, creditReceiptEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                        .receiptReference.id.eq(receipt.getID()))
                .select(creditReceiptEntity.id)
                .fetchFirst() != null;
    }
}
