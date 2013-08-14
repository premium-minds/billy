/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTReceiptInvoiceEntity;


public class DAOPTReceiptInvoiceImpl extends DAOPTInvoiceImpl implements
		DAOPTReceiptInvoice {

	@Inject
	public DAOPTReceiptInvoiceImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}
	
	@Override
	public PTReceiptInvoiceEntity getEntityInstance() {
		return new JPAPTReceiptInvoiceEntity();
	}

	@Override
	protected Class<JPAPTReceiptInvoiceEntity> getEntityClass() {
		return JPAPTReceiptInvoiceEntity.class;
	}

	@Override
	public List<PTReceiptInvoiceEntity> getBusinessReceiptInvoicesForSAFTPT(UID uid,
			Date from, Date to) {
		QJPAPTReceiptInvoiceEntity receiptInvoice = QJPAPTReceiptInvoiceEntity.jPAPTReceiptInvoiceEntity;
		JPAQuery query = new JPAQuery(this.getEntityManager());
		PTBusinessEntity business = this.getBusinessEntity(uid);

		query.from(receiptInvoice);

		List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
		BooleanExpression receiptInvoiceBusiness = receiptInvoice.business
				.eq(business);
		predicates.add(receiptInvoiceBusiness);
		BooleanExpression active = receiptInvoice.active.eq(true);
		predicates.add(active);
		BooleanExpression valid = receiptInvoice.date.between(from, to);
		predicates.add(valid);

		for (BooleanExpression e : predicates) {
			query.where(e);
		}

		List<PTReceiptInvoiceEntity> result = this.checkEntityList(query.list(receiptInvoice),
				PTReceiptInvoiceEntity.class); 
		return result;
	}

}
