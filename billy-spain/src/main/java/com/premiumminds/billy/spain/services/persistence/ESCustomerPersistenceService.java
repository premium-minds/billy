/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.services.persistence;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.entities.ESCustomerEntity;
import com.premiumminds.billy.spain.services.entities.ESCustomer;

public class ESCustomerPersistenceService implements
	PersistenceService<ESCustomer> {

	protected final DAOESCustomer	daoCustomer;

	@Inject
	public ESCustomerPersistenceService(DAOESCustomer daoCustomer) {
		this.daoCustomer = daoCustomer;
	}

	@Override
	public ESCustomer create(final Builder<ESCustomer> builder) {
		try {
			return new TransactionWrapper<ESCustomer>(daoCustomer) {

				@Override
				public ESCustomer runTransaction() throws Exception {
					ESCustomerEntity entity = (ESCustomerEntity) builder
							.build();
					return (ESCustomer) daoCustomer.create(entity);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

	@Override
	public ESCustomer update(final Builder<ESCustomer> builder) {
		try {
			return new TransactionWrapper<ESCustomer>(daoCustomer) {

				@Override
				public ESCustomer runTransaction() throws Exception {
					ESCustomerEntity entity = (ESCustomerEntity) builder
							.build();
					return (ESCustomer) daoCustomer.update(entity);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

	@Override
	public ESCustomer get(final UID uid) {
		try {
			return new TransactionWrapper<ESCustomer>(daoCustomer) {

				@Override
				public ESCustomer runTransaction() throws Exception {
					return (ESCustomer) daoCustomer.get(uid);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

}