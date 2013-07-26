/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core JPA.
 *
 * billy core JPA is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core JPA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.dao.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntity_;

public class DAOGenericInvoiceImpl extends
		AbstractDAO<GenericInvoiceEntity, JPAGenericInvoiceEntity> implements
		DAOGenericInvoice {

	@Inject
	public DAOGenericInvoiceImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	protected Class<? extends JPAGenericInvoiceEntity> getEntityClass() {
		return JPAGenericInvoiceEntity.class;
	}

	@Override
	public GenericInvoiceEntity getEntityInstance() {
		return new JPAGenericInvoiceEntity();
	}

	public GenericInvoiceEntity getLatestInvoiceFromSeries(String series) {
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

		Root<JPAGenericInvoiceEntity> invoiceRoot = query
				.from(JPAGenericInvoiceEntity.class);

		Path<String> uid = invoiceRoot.get(JPAGenericInvoiceEntity_.uid);

		query.multiselect(uid, builder.max(invoiceRoot
				.get(JPAGenericInvoiceEntity_.seriesNumber)));
		query.where(builder.equal(
				invoiceRoot.get(JPAGenericInvoiceEntity_.series), series));
		query.groupBy(uid);

		try {
			List<Object[]> list = this.getEntityManager().createQuery(query)
					.getResultList();

		} catch (Exception e) {
			System.out.println("######## No one home :P");
		}

		return null;
	}
}
