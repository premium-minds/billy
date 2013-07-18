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

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPATaxEntity;

public class DAOTaxImpl extends AbstractDAO<TaxEntity, JPATaxEntity> implements DAOTax {

	@Inject
	public DAOTaxImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}
	
	@Override
	protected Class<? extends JPATaxEntity> getEntityClass() {
		return JPATaxEntity.class;
	}

	@Override
	public TaxEntity getEntityInstance() {
		return new JPATaxEntity();
	}
	
}
