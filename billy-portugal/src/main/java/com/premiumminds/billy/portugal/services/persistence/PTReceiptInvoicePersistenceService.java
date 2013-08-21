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

import com.google.inject.Injector;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.exceptions.NotImplementedException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.persistence.services.PersistenceServiceImpl;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.services.entities.PTReceiptInvoice;


public class PTReceiptInvoicePersistenceService<T extends PTReceiptInvoice> extends PersistenceServiceImpl<T> implements PersistenceService<T> {

	public PTReceiptInvoicePersistenceService(Injector injector) {
		super(injector);
	}

	@NotImplemented
	@Override
	public T createEntity(Builder<T> builder) {
		throw new NotImplementedException();
	}
	
	@NotImplemented
	@Override
	public T updateEntity(final Builder<T> builder) {
		throw new NotImplementedException();
	}

	@Override
	public T getEntity(final UID uid) {
		final DAOPTReceiptInvoice dao = this.injector
				.getInstance(DAOPTReceiptInvoice.class);

		try {
			return new TransactionWrapper<T>(dao) {

				@SuppressWarnings("unchecked")
				@Override
				public T runTransaction() throws Exception {
					return (T) dao.get(uid);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

}
