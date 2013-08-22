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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTSimpleInvoiceEntity;

public class DAOPTInvoiceImpl extends DAOPTGenericInvoiceImpl implements
		DAOPTInvoice {

	@Inject
	public DAOPTInvoiceImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTInvoiceEntity getEntityInstance() {
		return new JPAPTInvoiceEntity();
	}

	@Override
	protected Class<? extends JPAPTInvoiceEntity> getEntityClass() {
		return JPAPTInvoiceEntity.class;
	}

	public List<PTInvoiceEntity> getBusinessInvoicesForSAFTPT(UID uid,
			Date from, Date to) {

		QJPAPTInvoiceEntity invoice = QJPAPTInvoiceEntity.jPAPTInvoiceEntity;
		
		JPAQuery query = new JPAQuery(this.getEntityManager());
		PTBusinessEntity business = this.getBusinessEntity(uid);

		query.from(invoice);

		List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
		BooleanExpression invoiceBusiness = invoice.business.eq(business);
		predicates.add(invoiceBusiness);
		BooleanExpression active = invoice.active.eq(true);
		predicates.add(active);
		BooleanExpression valid = invoice.date.between(from, to);
		predicates.add(valid);
		
		List<JPAPTSimpleInvoiceEntity> simpleInvoices = checkSimpleInvoices(
				uid, from, to);
		BooleanExpression removeSimpleInvoices = invoice.notIn(simpleInvoices);
		predicates.add(removeSimpleInvoices);
		
		List<JPAPTReceiptInvoiceEntity> receiptInvoices = checkReceiptInvoices(
				uid, from, to);
		BooleanExpression removeReceiptInvoices = invoice.notIn(receiptInvoices);
		predicates.add(removeReceiptInvoices);

		for (BooleanExpression e : predicates) {
			query.where(e);
		}

		List<PTInvoiceEntity> result = this.checkEntityList(query.list(invoice), PTInvoiceEntity.class); 
		return result;
	}

	private List<JPAPTSimpleInvoiceEntity> checkSimpleInvoices(UID uid,
			Date from, Date to) {
		JPAQuery simpleInvoicesQuery = new JPAQuery(this.getEntityManager());
		QJPAPTSimpleInvoiceEntity simpleInvoice = QJPAPTSimpleInvoiceEntity.jPAPTSimpleInvoiceEntity;

		List<JPAPTSimpleInvoiceEntity> simpleInvoices = simpleInvoicesQuery.from(simpleInvoice).where(simpleInvoice.active.eq(true)).where(simpleInvoice.date.between(from, to)).where(simpleInvoice.business
				.eq(this.getBusinessEntity(uid))).list(simpleInvoice);
		return simpleInvoices;
	}

	private List<JPAPTReceiptInvoiceEntity> checkReceiptInvoices(UID uid,
			Date from, Date to) {
		JPAQuery receiptInvoicesQuery = new JPAQuery(this.getEntityManager());
		QJPAPTReceiptInvoiceEntity receiptInvoice = QJPAPTReceiptInvoiceEntity.jPAPTReceiptInvoiceEntity;

		List<JPAPTReceiptInvoiceEntity> receiptInvoices = receiptInvoicesQuery.from(receiptInvoice).where(receiptInvoice.active.eq(true)).where(receiptInvoice.date.between(from, to)).where(receiptInvoice.business
				.eq(this.getBusinessEntity(uid))).list(receiptInvoice);
		return receiptInvoices;
	}
}
