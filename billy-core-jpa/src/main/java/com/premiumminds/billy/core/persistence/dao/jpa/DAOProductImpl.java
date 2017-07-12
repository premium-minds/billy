/**
 * Copyright (C) 2017 Premium Minds.
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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAProductEntity;

public class DAOProductImpl extends
	AbstractDAO<ProductEntity, JPAProductEntity> implements DAOProduct {

	@Inject
	public DAOProductImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductEntity> getAllActiveProducts() {
		List<JPAProductEntity> result = (List<JPAProductEntity>) this
				.getEntityManager()
				.createQuery(
						"select p from "
								+ this.getEntityClass().getCanonicalName()
								+ " p " + "where p.active=true",
						this.getEntityClass()).getResultList();
		return this.checkEntityList(result, ProductEntity.class);
	}

	@Override
	protected Class<? extends JPAProductEntity> getEntityClass() {
		return JPAProductEntity.class;
	}

	@Override
	public ProductEntity getEntityInstance() {
		return new JPAProductEntity();
	}

}
