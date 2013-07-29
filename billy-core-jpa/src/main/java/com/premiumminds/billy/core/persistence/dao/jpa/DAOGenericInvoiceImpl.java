/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy core JPA.
 * 
 * billy core JPA is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
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

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntity_;
import com.premiumminds.billy.core.services.UID;

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

	@Override
	public <T extends GenericInvoiceEntity> T getLatestInvoiceFromSeries(
			String series) throws BillyRuntimeException {

		List<Object[]> list = findLastestUID(this.getEntityClass(), series);

		if (list.size() != 0)
			return (T) this.get(new UID((String) list.get(0)[0]));
		else
			throw new BillyRuntimeException();
	}

	protected <T extends JPAGenericInvoiceEntity> List<Object[]> findLastestUID(
			Class<T> rootClass, String series) {
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

		Root<T> invoiceRoot = query.from(rootClass);

		Path<String> uidPath = invoiceRoot.get(JPAGenericInvoiceEntity_.uid);

		query.multiselect(uidPath, builder.max(invoiceRoot
				.get(JPAGenericInvoiceEntity_.seriesNumber)));

		query.where(builder.equal(
				invoiceRoot.get(JPAGenericInvoiceEntity_.series), series));
		query.groupBy(uidPath);

		return this.getEntityManager().createQuery(query).getResultList();
	}
}
