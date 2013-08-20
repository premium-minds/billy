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

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.QJPABusinessEntity;
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

	@SuppressWarnings("unchecked")
	@Override
	public <T extends GenericInvoiceEntity> T getLatestInvoiceFromSeries(
			String series, String businessUID) throws BillyRuntimeException {

		QJPAGenericInvoiceEntity genericInvoice = QJPAGenericInvoiceEntity.jPAGenericInvoiceEntity;
		QJPABusinessEntity business = QJPABusinessEntity.jPABusinessEntity;

		JPAQuery query = new JPAQuery(this.getEntityManager());

		BusinessEntity businessEnity = query.from(business)
				.where(business.uid.eq(businessUID)).uniqueResult(business);

		if (businessEnity == null) {
			throw new BillyRuntimeException();
		}

		query = new JPAQuery(this.getEntityManager());

		query.from(genericInvoice);
		query.where(genericInvoice.series.eq(series));
		query.where(genericInvoice.seriesNumber.eq(new JPASubQuery()
				.from(genericInvoice)
				.where(genericInvoice.business.eq(businessEnity))
				.unique(genericInvoice.seriesNumber.max())));

		List<JPAGenericInvoiceEntity> invoiceList = query.list(genericInvoice);

		GenericInvoiceEntity invoice = null;
		boolean found = false;
		for (JPAGenericInvoiceEntity entity : invoiceList) {
			if (entity.getBusiness().getUID().equals(businessUID) && !found) {
				found = true;
				invoice = entity;
			} else if (entity.getBusiness().getUID().equals(businessUID)
					&& found) {
				invoice = null;
			}
		}

		if (invoice != null) {
			return (T) invoice;
		} else {
			throw new BillyRuntimeException();
		}
	}
}
