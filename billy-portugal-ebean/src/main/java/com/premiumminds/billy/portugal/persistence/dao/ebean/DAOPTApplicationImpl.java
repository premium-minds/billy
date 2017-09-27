/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.ebean;

import javax.persistence.NoResultException;

import com.premiumminds.billy.core.persistence.dao.ebean.DAOApplicationImpl;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTApplication;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.JPAPTApplicationEntity;

public class DAOPTApplicationImpl extends DAOApplicationImpl implements DAOPTApplication {

    @Override
    public PTApplicationEntity getEntityInstance() {
        return new JPAPTApplicationEntity();
    }

    @Override
    protected Class<JPAPTApplicationEntity> getEntityClass() {
        return JPAPTApplicationEntity.class;
    }

    @Override
    public PTApplicationEntity get(UID uid) throws NoResultException {
        return (PTApplicationEntity) super.get(uid);
    }

}
