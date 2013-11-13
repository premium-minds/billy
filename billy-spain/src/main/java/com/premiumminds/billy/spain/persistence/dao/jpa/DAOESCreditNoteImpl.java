/**
 * Copyright (C) 2013 Premium Minds.
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

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESReceiptInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;

public class DAOESCreditNoteImpl extends DAOESGenericInvoiceImpl implements
	DAOESCreditNote {

	@Inject
	public DAOESCreditNoteImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public ESCreditNoteEntity getEntityInstance() {
		return new JPAESCreditNoteEntity();
	}

	@Override
	protected Class<JPAESCreditNoteEntity> getEntityClass() {
		return JPAESCreditNoteEntity.class;
	}

	@Override
	public List<ESCreditNoteEntity> getBusinessCreditNotesForSAFTES(UID uid,
			Date from, Date to) {
		QJPAESCreditNoteEntity creditNote = QJPAESCreditNoteEntity.jPAESCreditNoteEntity;

		JPAQuery query = createQuery();

		query.from(creditNote)
			.where(creditNote.instanceOf(JPAESCreditNoteEntity.class)
					.and(creditNote.date.between(from, to))
					.and(toDSL(creditNote.business, QJPAESBusinessEntity.class).uid.eq(uid.toString())));

		List<ESCreditNoteEntity> result = this.checkEntityList(
				query.list(creditNote), ESCreditNoteEntity.class);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ESCreditNote> findByReferencedDocument(UID uidCompany,
			UID uidInvoice) {
		QJPAESCreditNoteEntity creditNote = QJPAESCreditNoteEntity.jPAESCreditNoteEntity;
		QJPAESCreditNoteEntryEntity entry = QJPAESCreditNoteEntryEntity.jPAESCreditNoteEntryEntity;
		QJPAESGenericInvoiceEntity invoice = QJPAESGenericInvoiceEntity.jPAESGenericInvoiceEntity;
			
		JPASubQuery invQ = new JPASubQuery()
			.from(invoice)
			.where(invoice.uid.eq(uidInvoice.toString()));
		
		JPASubQuery entQ = new JPASubQuery()
		.from(entry)
		.where(toDSL(entry.reference, QJPAESReceiptInvoiceEntity.class).uid.in(
				invQ.list(invoice.uid)));
		
		return (List<ESCreditNote>) (List<?>) createQuery()
				.from(creditNote)
				.where(toDSL(creditNote.business, QJPAESBusinessEntity.class).uid.eq(uidCompany.toString())
						.and(toDSL(creditNote.entries.any(), QJPAESCreditNoteEntryEntity.class).uid.in(
								entQ.list(entry.uid))))
				.list(creditNote);
	}

}
