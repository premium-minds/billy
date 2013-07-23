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

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTCreditNoteEntryEntity;

public class DAOPTCreditNoteEntryImpl extends DAOPTInvoiceEntryImpl implements
		DAOPTCreditNoteEntry {

	public DAOPTCreditNoteEntryImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTCreditNoteEntryEntity getEntityInstance() {
		return new JPAPTCreditNoteEntryEntity();
	}

	@Override
	protected Class<JPAPTCreditNoteEntryEntity> getEntityClass() {
		return JPAPTCreditNoteEntryEntity.class;
	}
}
