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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTInvoiceEntity_;

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
	protected Class<JPAPTInvoiceEntity> getEntityClass() {
		return JPAPTInvoiceEntity.class;
	}

	@Override
	public PTInvoiceEntity getLatestInvoiceFromSeries(String series) {
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

		Root<JPAPTInvoiceEntity> invoiceRoot = query
				.from(JPAPTInvoiceEntity.class);

		Path<String> uid = invoiceRoot.get(JPAPTInvoiceEntity_.uid);

		query.multiselect(uid,
				builder.max(invoiceRoot.get(JPAPTInvoiceEntity_.seriesNumber)));
		query.where(builder.equal(invoiceRoot.get(JPAPTInvoiceEntity_.series),
				series));
		query.groupBy(uid);

		try {
			List<Object[]> list = this.getEntityManager().createQuery(query)
					.getResultList();
		} catch (Exception e) {
			return null;
		}
		return null;
	}
}
