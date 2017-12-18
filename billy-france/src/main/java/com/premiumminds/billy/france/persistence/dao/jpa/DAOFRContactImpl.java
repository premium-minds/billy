/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.persistence.dao.jpa;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.DAOContactImpl;
import com.premiumminds.billy.france.persistence.dao.DAOFRContact;
import com.premiumminds.billy.france.persistence.entities.FRContactEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRContactEntity;

public class DAOFRContactImpl extends DAOContactImpl implements DAOFRContact {

    @Inject
    public DAOFRContactImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public FRContactEntity getEntityInstance() {
        return new JPAFRContactEntity();
    }

    @Override
    protected Class<JPAFRContactEntity> getEntityClass() {
        return JPAFRContactEntity.class;
    }

}
