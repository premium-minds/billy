/**
 * Copyright (C) 2013 Premium Minds.
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
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceiptInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESReceiptInvoice;

public class ESReceiptInvoicePersistenceService implements
		PersistenceService<ESReceiptInvoice> {

	protected final DAOESReceiptInvoice daoReceiptInvoice;
	protected final DAOTicket daoTicket;

	@Inject
	public ESReceiptInvoicePersistenceService(
			DAOESReceiptInvoice daoReceiptInvoice, DAOTicket daoTicket) {
		this.daoReceiptInvoice = daoReceiptInvoice;
		this.daoTicket = daoTicket;
	}

	@Override
	@NotImplemented
	public ESReceiptInvoice create(final Builder<ESReceiptInvoice> builder) {
		return null;
	}

	@NotImplemented
	@Override
	public ESReceiptInvoice update(final Builder<ESReceiptInvoice> builder) {
		try {
			return new TransactionWrapper<ESReceiptInvoice>(daoReceiptInvoice) {

				@Override
				public ESReceiptInvoice runTransaction() throws Exception {
					ESReceiptInvoiceEntity entity = (ESReceiptInvoiceEntity) builder
							.build();
					return (ESReceiptInvoice) daoReceiptInvoice.update(entity);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

	@Override
	public ESReceiptInvoice get(final UID uid) {
		try {
			return new TransactionWrapper<ESReceiptInvoice>(daoReceiptInvoice) {

				@Override
				public ESReceiptInvoice runTransaction() throws Exception {
					return (ESReceiptInvoice) daoReceiptInvoice.get(uid);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

	public ESReceiptInvoice getWithTicket(final UID ticketUID) {

		try {
			return new TransactionWrapper<ESReceiptInvoice>(daoReceiptInvoice) {

				@Override
				public ESReceiptInvoice runTransaction() throws NoResultException, BillyRuntimeException {
					UID objectUID = daoTicket.getObjectEntityUID(ticketUID
							.getValue());
					return (ESReceiptInvoice) daoReceiptInvoice.get(objectUID);
				}

			}.execute();
		}catch(NoResultException e){
			throw e;
		}
		catch (Exception e){
			throw new BillyRuntimeException(e);
		}
	}

}
