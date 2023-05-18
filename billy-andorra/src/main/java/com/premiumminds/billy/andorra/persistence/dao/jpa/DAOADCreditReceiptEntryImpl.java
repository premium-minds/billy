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

import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntryEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADCreditReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADCreditReceiptEntryEntity;
import com.premiumminds.billy.andorra.services.entities.ADReceipt;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceiptEntry;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADCreditReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADCreditReceiptEntryEntity;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.jpa.impl.JPAQuery;

public class DAOADCreditReceiptEntryImpl
        extends AbstractDAOADGenericInvoiceEntryImpl<ADCreditReceiptEntryEntity, JPAADCreditReceiptEntryEntity>
        implements DAOADCreditReceiptEntry
{

    @Inject
    public DAOADCreditReceiptEntryImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ADCreditReceiptEntryEntity getEntityInstance() {
        return new JPAADCreditReceiptEntryEntity();
    }

    @Override
    protected Class<JPAADCreditReceiptEntryEntity> getEntityClass() {
        return JPAADCreditReceiptEntryEntity.class;
    }

    @Override
    public ADCreditReceiptEntity checkCreditReceipt(ADReceipt receipt) {
        QJPAADCreditReceiptEntity creditReceiptEntity = QJPAADCreditReceiptEntity.jPAADCreditReceiptEntity;

        return new JPAQuery<JPAADCreditReceiptEntity>(this.getEntityManager())
                .from(creditReceiptEntity)
                .where(new QJPAADCreditReceiptEntryEntity(JPAADCreditReceiptEntryEntity.class, creditReceiptEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                        .receiptReference.id.eq(receipt.getID()))
                .select(creditReceiptEntity)
                .fetchFirst();
    }

    @Override
    public boolean existsCreditReceipt(ADReceipt receipt) {
        QJPAADCreditReceiptEntity creditReceiptEntity = QJPAADCreditReceiptEntity.jPAADCreditReceiptEntity;

        return new JPAQuery<JPAADCreditReceiptEntity>(this.getEntityManager())
                .from(creditReceiptEntity)
                .where(new QJPAADCreditReceiptEntryEntity(JPAADCreditReceiptEntryEntity.class, creditReceiptEntity.entries.any().getMetadata(), PathInits.DIRECT2)
                        .receiptReference.id.eq(receipt.getID()))
                .select(creditReceiptEntity.id)
                .fetchFirst() != null;
    }
}
