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
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESInvoiceEntity;

public class DAOESInvoiceImpl extends DAOESGenericInvoiceImpl implements
	DAOESInvoice {

	@Inject
	public DAOESInvoiceImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public ESInvoiceEntity getEntityInstance() {
		return new JPAESInvoiceEntity();
	}

	@Override
	protected Class<? extends JPAESInvoiceEntity> getEntityClass() {
		return JPAESInvoiceEntity.class;
	}

	@Override
	public List<ESInvoiceEntity> getBusinessInvoicesForSAFTES(UID uid,
			Date from, Date to) {

		QJPAESInvoiceEntity invoice = QJPAESInvoiceEntity.jPAESInvoiceEntity;

		JPAQuery query = createQuery();

		query.from(invoice)
			.where(invoice.instanceOf(JPAESInvoiceEntity.class)
					.and(invoice.date.between(from, to))
					.and(toDSL(invoice.business, QJPAESBusinessEntity.class).uid.eq(uid.toString())));

		List<ESInvoiceEntity> result = this.checkEntityList(
				query.list(invoice), ESInvoiceEntity.class);
		return result;
	}

}
