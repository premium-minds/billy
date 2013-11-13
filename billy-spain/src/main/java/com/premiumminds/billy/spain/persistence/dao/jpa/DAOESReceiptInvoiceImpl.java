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

import com.mysema.query.jpa.impl.JPAQuery;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceiptInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESReceiptInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESReceiptInvoiceEntity;


public class DAOESReceiptInvoiceImpl extends DAOESInvoiceImpl implements
		DAOESReceiptInvoice {

	@Inject
	public DAOESReceiptInvoiceImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}
	
	@Override
	public ESReceiptInvoiceEntity getEntityInstance() {
		return new JPAESReceiptInvoiceEntity();
	}

	@Override
	protected Class<JPAESReceiptInvoiceEntity> getEntityClass() {
		return JPAESReceiptInvoiceEntity.class;
	}

	@Override
	public List<ESReceiptInvoiceEntity> getBusinessReceiptInvoicesForSAFTES(UID uid,
			Date from, Date to) {
		QJPAESReceiptInvoiceEntity invoice = QJPAESReceiptInvoiceEntity.jPAESReceiptInvoiceEntity;

		JPAQuery query = createQuery();

		query.from(invoice)
			.where(invoice.instanceOf(JPAESReceiptInvoiceEntity.class)
					.and(invoice.date.between(from, to))
					.and(toDSL(invoice.business, QJPAESBusinessEntity.class).uid.eq(uid.toString())));

		List<ESReceiptInvoiceEntity> result = this.checkEntityList(
				query.list(invoice), ESReceiptInvoiceEntity.class);
		return result;
	}

}
