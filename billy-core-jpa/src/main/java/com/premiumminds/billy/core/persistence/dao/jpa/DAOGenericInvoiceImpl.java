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

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.QJPAGenericInvoiceEntity;

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

		QJPAGenericInvoiceEntity genericInvoice = QJPAGenericInvoiceEntity.jPAGenericInvoiceEntity;

		JPAQuery query = new JPAQuery(this.getEntityManager());

		GenericInvoiceEntity invoice = query
				.from(genericInvoice)
				.where(genericInvoice.series.eq(series))
				.where(genericInvoice.seriesNumber.eq(new JPASubQuery().from(
						genericInvoice).unique(
						genericInvoice.seriesNumber.max())))
				.uniqueResult(genericInvoice);

		if (invoice != null) {
			return (T) invoice;
		} else {
			throw new BillyRuntimeException();
		}
	}

}
