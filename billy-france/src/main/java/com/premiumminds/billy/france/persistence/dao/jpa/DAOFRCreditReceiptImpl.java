/**
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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.JPASubQuery;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceipt;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRCreditReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRCreditReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRCreditReceiptEntryEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRGenericInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRCreditReceipt;

public class DAOFRCreditReceiptImpl extends
        AbstractDAOFRGenericInvoiceImpl<FRCreditReceiptEntity, JPAFRCreditReceiptEntity> implements DAOFRCreditReceipt {

    @Inject
    public DAOFRCreditReceiptImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public FRCreditReceiptEntity getEntityInstance() {
        return new JPAFRCreditReceiptEntity();
    }

    @Override
    protected Class<JPAFRCreditReceiptEntity> getEntityClass() {
        return JPAFRCreditReceiptEntity.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FRCreditReceipt> findByReferencedDocument(UID uidCompany, UID uidInvoice) {
        QJPAFRCreditReceiptEntity creditReceipt = QJPAFRCreditReceiptEntity.jPAFRCreditReceiptEntity;
        QJPAFRCreditReceiptEntryEntity entry = QJPAFRCreditReceiptEntryEntity.jPAFRCreditReceiptEntryEntity;
        QJPAFRGenericInvoiceEntity recepit = QJPAFRGenericInvoiceEntity.jPAFRGenericInvoiceEntity;

        JPASubQuery invQ = new JPASubQuery().from(recepit).where(recepit.uid.eq(uidInvoice.toString()));

        JPASubQuery entQ = new JPASubQuery().from(entry)
                .where(this.toDSL(entry.reference, QJPAFRGenericInvoiceEntity.class).uid.in(invQ.list(recepit.uid)));

        return (List<FRCreditReceipt>) (List<?>) this.createQuery().from(creditReceipt)
                .where(this.toDSL(creditReceipt.business, QJPAFRBusinessEntity.class).uid.eq(uidCompany.toString())
                        .and(this.toDSL(creditReceipt.entries.any(), QJPAFRCreditReceiptEntryEntity.class).uid
                                .in(entQ.list(entry.uid))))
                .list(creditReceipt);
    }

}
