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

import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAApplicationEntity;

public class DAOApplicationImpl extends
	AbstractDAO<ApplicationEntity, JPAApplicationEntity> implements
	DAOApplication {

	@Inject
	public DAOApplicationImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	protected Class<? extends JPAApplicationEntity> getEntityClass() {
		return JPAApplicationEntity.class;
	}

	@Override
	public ApplicationEntity getEntityInstance() {
		return new JPAApplicationEntity();
	}

}
