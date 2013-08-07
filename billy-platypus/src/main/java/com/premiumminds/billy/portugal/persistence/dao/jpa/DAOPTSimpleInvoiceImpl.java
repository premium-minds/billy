/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTSimpleInvoiceEntity;

public class DAOPTSimpleInvoiceImpl extends DAOPTInvoiceImpl implements
		DAOPTSimpleInvoice {

	@Inject
	public DAOPTSimpleInvoiceImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTSimpleInvoiceEntity getEntityInstance() {
		return new JPAPTSimpleInvoiceEntity();
	}

	@Override
	protected Class<JPAPTSimpleInvoiceEntity> getEntityClass() {
		return JPAPTSimpleInvoiceEntity.class;
	}

	public List<PTSimpleInvoiceEntity> getBusinessSimpleInvoicesForSAFTPT(
			UID uid, Date from, Date to) {

		QJPAPTSimpleInvoiceEntity simpleInvoice = QJPAPTSimpleInvoiceEntity.jPAPTSimpleInvoiceEntity;
		JPAQuery query = new JPAQuery(this.getEntityManager());
		PTBusinessEntity business = getBusinessEntity(uid);

		query.from(simpleInvoice);

		List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
		BooleanExpression simpleInvoiceBusiness = simpleInvoice.business
				.eq(business);
		predicates.add(simpleInvoiceBusiness);
		BooleanExpression active = simpleInvoice.active.eq(true);
		predicates.add(active);
		BooleanExpression valid = simpleInvoice.date.between(from, to);
		predicates.add(valid);

		for (BooleanExpression e : predicates) {
			query.where(e);
		}

		return checkEntityList(query.list(simpleInvoice),
				PTSimpleInvoiceEntity.class);
	}

}
