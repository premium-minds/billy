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

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTManualInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTManualInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTManualInvoiceEntity;

public class DAOPTManualInvoiceImpl extends DAOPTGenericInvoiceImpl implements
		DAOPTManualInvoice {

	@Inject
	public DAOPTManualInvoiceImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTManualInvoiceEntity getEntityInstance() {
		return new JPAPTManualInvoiceEntity();
	}

	@Override
	protected Class<? extends JPAPTManualInvoiceEntity> getEntityClass() {
		return JPAPTManualInvoiceEntity.class;
	}

	@Override
	public PTManualInvoiceEntity getLatestInvoiceFromSeries(String series)
			throws BillyRuntimeException {

		List<Object[]> list = findLastestUID(this.getEntityClass(), series);

		if (list.size() != 0)
			return (PTManualInvoiceEntity) this.get(new UID((String) list
					.get(0)[0]));
		else
			throw new BillyRuntimeException();
	}
}
