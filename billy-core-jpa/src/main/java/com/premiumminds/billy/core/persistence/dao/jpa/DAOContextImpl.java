/*
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

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAContextEntity;
import com.premiumminds.billy.core.services.entities.Context;

public class DAOContextImpl extends AbstractDAO<ContextEntity, JPAContextEntity> implements DAOContext {

    @Inject
    public DAOContextImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    protected Class<? extends JPAContextEntity> getEntityClass() {
        return JPAContextEntity.class;
    }

    @Override
    public ContextEntity getEntityInstance() {
        return new JPAContextEntity();
    }

    @Override
    public boolean isSameOrSubContext(Context sub, Context context) {
		if (sub.getUID().equals(context.getUID())) {
			return true;
		}
		if (sub.getParentContext() == null) {
			return false;
		}
		if (sub.getParentContext().getUID().equals(context.getUID())) {
            return true;
        }
        return this.isSameOrSubContext(sub.getParentContext(), context);
    }

}
