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
package com.premiumminds.billy.portugal.services.persistence;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;

public class PTSupplierPersistenceService implements
	PersistenceService<PTSupplier> {

	protected final DAOPTSupplier	daoSupplier;

	@Inject
	public PTSupplierPersistenceService(DAOPTSupplier daoSupplier) {
		this.daoSupplier = daoSupplier;
	}

	@Override
	public PTSupplier create(final Builder<PTSupplier> builder) {
		try {
			return new TransactionWrapper<PTSupplier>(daoSupplier) {

				@Override
				public PTSupplier runTransaction() throws Exception {
					PTSupplierEntity entity = (PTSupplierEntity) builder
							.build();
					return (PTSupplier) daoSupplier.create(entity);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

	@Override
	public PTSupplier update(final Builder<PTSupplier> builder) {
		try {
			return new TransactionWrapper<PTSupplier>(daoSupplier) {

				@Override
				public PTSupplier runTransaction() throws Exception {
					PTSupplierEntity entity = (PTSupplierEntity) builder
							.build();
					return (PTSupplier) daoSupplier.update(entity);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

	@Override
	public PTSupplier get(final UID uid) {
		try {
			return new TransactionWrapper<PTSupplier>(daoSupplier) {

				@Override
				public PTSupplier runTransaction() throws Exception {
					return (PTSupplier) daoSupplier.get(uid);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

}
