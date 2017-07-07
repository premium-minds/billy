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

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;

public class ESCreditNotePersistenceService implements
	PersistenceService<ESCreditNote> {

	protected final DAOESCreditNote	daoCreditNote;
	protected final DAOTicket daoTicket;

	@Inject
	public ESCreditNotePersistenceService(DAOESCreditNote daoCreditNote,
			DAOTicket daoTicket) {
		this.daoCreditNote = daoCreditNote;
		this.daoTicket = daoTicket;
	}

	@Override
	@NotImplemented
	public ESCreditNote create(final Builder<ESCreditNote> builder) {
		return null;
	}

	@Override
	public ESCreditNote update(final Builder<ESCreditNote> builder) {
		try {
			return new TransactionWrapper<ESCreditNote>(daoCreditNote) {

				@Override
				public ESCreditNote runTransaction() throws Exception {
					ESCreditNoteEntity entity = (ESCreditNoteEntity) builder
							.build();
					return (ESCreditNote) daoCreditNote.update(entity);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

	@Override
	public ESCreditNote get(final UID uid) {
		try {
			return new TransactionWrapper<ESCreditNote>(daoCreditNote) {

				@Override
				public ESCreditNote runTransaction() throws Exception {
					return (ESCreditNote) daoCreditNote.get(uid);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

	public ESCreditNote getWithTicket(final UID ticketUID) throws NoResultException, BillyRuntimeException{

		try {
			return new TransactionWrapper<ESCreditNote>(daoCreditNote) {

				@Override
				public ESCreditNote runTransaction() throws Exception {
					UID objectUID = daoTicket.getObjectEntityUID(ticketUID
							.getValue());
					return (ESCreditNote) daoCreditNote.get(objectUID);
				}

			}.execute();
		}catch(NoResultException e){
			throw e;
		} 
		catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}
	
	public ESCreditNote findByNumber(final UID uidBusiness, final String number) {
		try {
			return new TransactionWrapper<ESCreditNote>(daoCreditNote) {

				@Override
				public ESCreditNote runTransaction() throws Exception {
					return (ESCreditNote) daoCreditNote.findByNumber(uidBusiness, number);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

	public List<ESCreditNote> findByReferencedDocument(final UID uidCompany, final UID uidInvoice) {
		try {
			return new TransactionWrapper<List<ESCreditNote>>(daoCreditNote) {

				@Override
				public List<ESCreditNote> runTransaction() throws Exception {
					return (List<ESCreditNote>) daoCreditNote.findByReferencedDocument(uidCompany, uidInvoice);
				}

			}.execute();
		}catch(NoResultException e){
			throw e;
		} 
		catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

}
