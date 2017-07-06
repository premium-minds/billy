/**
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.premiumminds.billy.core.persistence.dao.jpa.DAOBusinessImpl;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTBusinessEntity;

public class DAOPTBusinessImpl extends DAOBusinessImpl implements DAOPTBusiness {

  @Inject
  public DAOPTBusinessImpl(Provider<EntityManager> emProvider) {
    super(emProvider);
  }

  @Override
  public PTBusinessEntity getEntityInstance() {
    return new JPAPTBusinessEntity();
  }

  @Override
  protected Class<JPAPTBusinessEntity> getEntityClass() {
    return JPAPTBusinessEntity.class;
  }

  @Override
  public PTBusinessEntity get(UID uid) throws NoResultException {
    return (PTBusinessEntity) super.get(uid);
  }

}
