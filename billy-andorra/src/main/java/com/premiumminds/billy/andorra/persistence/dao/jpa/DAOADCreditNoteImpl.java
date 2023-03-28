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
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADBusinessEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADCreditNoteEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditNote;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNote;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADCreditNoteEntity;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

public class DAOADCreditNoteImpl extends AbstractDAOADGenericInvoiceImpl<ADCreditNoteEntity, JPAADCreditNoteEntity>
        implements DAOADCreditNote
{

    @Inject
    public DAOADCreditNoteImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ADCreditNoteEntity getEntityInstance() {
        return new JPAADCreditNoteEntity();
    }

    @Override
    protected Class<JPAADCreditNoteEntity> getEntityClass() {
        return JPAADCreditNoteEntity.class;
    }

    @Override
    public List<ADCreditNote> findByReferencedDocument(StringID<Business> uidCompany, StringID<GenericInvoice> uidInvoice) {
        QJPAADCreditNoteEntity creditNote = QJPAADCreditNoteEntity.jPAADCreditNoteEntity;
        QJPAADCreditNoteEntryEntity entry = QJPAADCreditNoteEntryEntity.jPAADCreditNoteEntryEntity;
        QJPAADGenericInvoiceEntity invoice = QJPAADGenericInvoiceEntity.jPAADGenericInvoiceEntity;

        final JPQLQuery<String> invQ = JPAExpressions
            .select(invoice.uid)
            .from(invoice)
            .where(invoice.uid.eq(uidInvoice.toString()));

        final JPQLQuery<String> entQ = JPAExpressions
            .select(entry.uid)
            .from(entry)
            .where(this.toDSL(entry.invoiceReference, QJPAADGenericInvoiceEntity.class).uid.in(invQ));

        return new ArrayList<>(this
            .createQuery()
            .from(creditNote)
            .where(this.toDSL(creditNote.business, QJPAADBusinessEntity.class).uid
                       .eq(uidCompany.toString())
                       .and(this.toDSL(creditNote.entries.any(), QJPAADCreditNoteEntryEntity.class).uid.in(entQ)))
            .select(creditNote)
            .fetch());
    }

}
