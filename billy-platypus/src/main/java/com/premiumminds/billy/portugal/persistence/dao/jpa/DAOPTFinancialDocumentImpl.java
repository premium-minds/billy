/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
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

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.AbstractDAO;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTFinancialDocument;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTFinancialDocumentEntity;

public class DAOPTFinancialDocumentImpl extends AbstractDAO<IPTFinancialDocumentEntity, PTFinancialDocumentEntity> implements
		DAOPTFinancialDocument {

	@Inject
	public DAOPTFinancialDocumentImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	
	@Override
	protected Class<PTFinancialDocumentEntity> getEntityClass() {
		return PTFinancialDocumentEntity.class;
	}

}
