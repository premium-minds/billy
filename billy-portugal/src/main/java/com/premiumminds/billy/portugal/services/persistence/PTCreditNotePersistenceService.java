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
import com.premiumminds.billy.core.persistence.services.PersistenceServiceImpl;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;

public class PTCreditNotePersistenceService extends
	PersistenceServiceImpl<PTCreditNote> implements
	PersistenceService<PTCreditNote> {

	protected final DAOPTCreditNote	daoCreditNote;

	@Inject
	public PTCreditNotePersistenceService(DAOPTCreditNote daoCreditNote) {
		this.daoCreditNote = daoCreditNote;
	}

	@Override
	@NotImplemented
	public PTCreditNote create(final Builder<PTCreditNote> builder) {
		return null;
	}

	@Override
	public PTCreditNote update(final Builder<PTCreditNote> builder) {
		try {
			return new TransactionWrapper<PTCreditNote>(daoCreditNote) {

				@Override
				public PTCreditNote runTransaction() throws Exception {
					PTCreditNoteEntity entity = (PTCreditNoteEntity) builder
							.build();
					return (PTCreditNote) daoCreditNote.update(entity);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

	@Override
	public PTCreditNote get(final UID uid) {
		try {
			return new TransactionWrapper<PTCreditNote>(daoCreditNote) {

				@Override
				public PTCreditNote runTransaction() throws Exception {
					return (PTCreditNote) daoCreditNote.get(uid);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

}
