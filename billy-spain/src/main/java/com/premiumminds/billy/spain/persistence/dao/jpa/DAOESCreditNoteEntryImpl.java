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

import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNoteEntry;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESCreditNoteEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;
import com.premiumminds.billy.spain.services.entities.ESInvoice;

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

        List<JPAESCreditNoteEntity> allCns = new JPAQuery<>(this.getEntityManager())
            .select(creditNoteEntity)
            .from(creditNoteEntity)
            .fetch();

        // TODO make a query to do this
        for (JPAESCreditNoteEntity cne : allCns) {
            for (ESCreditNoteEntry cnee : cne.getEntries()) {
                if (cnee.getReference().getNumber().compareTo(invoice.getNumber()) == 0) {
                    return cne;
                }
            }
        }
        return null;
    }
}
