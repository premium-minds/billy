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

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESCreditReceiptEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESGenericInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditReceipt;

public class DAOESCreditReceiptImpl extends
        AbstractDAOESGenericInvoiceImpl<ESCreditReceiptEntity, JPAESCreditReceiptEntity> implements DAOESCreditReceipt {

    @Inject
    public DAOESCreditReceiptImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ESCreditReceiptEntity getEntityInstance() {
        return new JPAESCreditReceiptEntity();
    }

    @Override
    protected Class<JPAESCreditReceiptEntity> getEntityClass() {
        return JPAESCreditReceiptEntity.class;
    }

    @Override
    public List<ESCreditReceipt> findByReferencedDocument(UID uidCompany, UID uidInvoice) {
        QJPAESCreditReceiptEntity creditReceipt = QJPAESCreditReceiptEntity.jPAESCreditReceiptEntity;
        QJPAESCreditReceiptEntryEntity entry = QJPAESCreditReceiptEntryEntity.jPAESCreditReceiptEntryEntity;
        QJPAESGenericInvoiceEntity receipt = QJPAESGenericInvoiceEntity.jPAESGenericInvoiceEntity;

        final JPQLQuery<String> invQ = JPAExpressions
            .select(receipt.uid)
            .from(receipt)
            .where(receipt.uid.eq(uidInvoice.toString()));

        final JPQLQuery<String> entQ = JPAExpressions
            .select(entry.uid)
            .from(entry)
            .where(this.toDSL(entry.reference, QJPAESGenericInvoiceEntity.class).uid.in(invQ));

        return new ArrayList<>(this
            .createQuery()
            .from(creditReceipt)
            .where(this.toDSL(creditReceipt.business, QJPAESBusinessEntity.class).uid
                       .eq(uidCompany.toString())
                       .and(this.toDSL(creditReceipt.entries.any(), QJPAESCreditReceiptEntryEntity.class).uid.in(entQ)))
            .select(creditReceipt)
            .fetch());
    }

}
