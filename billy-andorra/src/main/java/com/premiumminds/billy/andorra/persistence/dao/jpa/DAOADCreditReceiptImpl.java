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
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADBusinessEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADCreditReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADCreditReceiptEntryEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditReceipt;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceipt;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADCreditReceiptEntity;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public class DAOADCreditReceiptImpl extends AbstractDAOADGenericInvoiceImpl<ADCreditReceiptEntity, JPAADCreditReceiptEntity>
    implements DAOADCreditReceipt
{

    @Inject
    public DAOADCreditReceiptImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ADCreditReceiptEntity getEntityInstance() {
        return new JPAADCreditReceiptEntity();
    }

    @Override
    protected Class<JPAADCreditReceiptEntity> getEntityClass() {
        return JPAADCreditReceiptEntity.class;
    }

    @Override public List<ADCreditReceipt> findByReferencedDocument(StringID<Business> uidCompany,
                                                                    StringID<GenericInvoice> uidInvoice) {
        QJPAADCreditReceiptEntity creditReceipt = QJPAADCreditReceiptEntity.jPAADCreditReceiptEntity;
        QJPAADCreditReceiptEntryEntity entry = QJPAADCreditReceiptEntryEntity.jPAADCreditReceiptEntryEntity;
        QJPAADGenericInvoiceEntity receipt = QJPAADGenericInvoiceEntity.jPAADGenericInvoiceEntity;

        final JPQLQuery<String> invQ = JPAExpressions
            .select(receipt.uid)
            .from(receipt)
            .where(receipt.uid.eq(uidInvoice.getIdentifier()));

        final JPQLQuery<String> entQ = JPAExpressions
            .select(entry.uid)
            .from(entry)
            .where(this.toDSL(entry.receiptReference, QJPAADGenericInvoiceEntity.class).uid.in(invQ));

        return new ArrayList<>(this
            .createQuery()
            .from(creditReceipt)
            .where(this.toDSL(creditReceipt.business, QJPAADBusinessEntity.class).uid
                       .eq(uidCompany.toString())
                       .and(this.toDSL(creditReceipt.entries.any(), QJPAADCreditReceiptEntryEntity.class).uid.in(entQ)))
            .select(creditReceipt)
            .fetch());
    }

}
