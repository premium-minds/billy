/**
 * Copyright (C) 2017 Premium Minds.
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
import javax.persistence.LockModeType;

import com.mysema.query.jpa.impl.JPAQuery;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.QJPABusinessEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.QJPAInvoiceSeriesEntity;
import com.premiumminds.billy.core.services.entities.InvoiceSeries;

public class DAOInvoiceSeriesImpl extends
	AbstractDAO<InvoiceSeriesEntity, JPAInvoiceSeriesEntity> implements
	DAOInvoiceSeries {

	@Inject
	public DAOInvoiceSeriesImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public InvoiceSeriesEntity getEntityInstance() {
		return new JPAInvoiceSeriesEntity();
	}

	@Override
	protected Class<? extends JPAInvoiceSeriesEntity> getEntityClass() {
		return JPAInvoiceSeriesEntity.class;
	}

	public InvoiceSeriesEntity getSeries(String series, String businessUID, LockModeType lockMode) {
		QJPAInvoiceSeriesEntity entity = QJPAInvoiceSeriesEntity.jPAInvoiceSeriesEntity;

		JPAQuery query = new JPAQuery(this.getEntityManager());

		query = new JPAQuery(this.getEntityManager());

		query.from(entity);
		query.where(entity.series.eq(series));
		query.where(toDSL(entity.business, QJPABusinessEntity.class).uid
				.eq(businessUID));

		InvoiceSeries seriesEntity = query.setLockMode(lockMode).singleResult(entity);

		return (InvoiceSeriesEntity) seriesEntity;
	}
}
