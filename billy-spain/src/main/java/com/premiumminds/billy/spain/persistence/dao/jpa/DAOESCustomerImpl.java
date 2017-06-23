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
package com.premiumminds.billy.spain.persistence.dao.jpa;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.DAOCustomerImpl;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.entities.ESCustomerEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESCustomerEntity;

public class DAOESCustomerImpl extends DAOCustomerImpl implements DAOESCustomer {

  @Inject
  public DAOESCustomerImpl(Provider<EntityManager> emProvider) {
    super(emProvider);
  }

  @Override
  public ESCustomerEntity getEntityInstance() {
    return new JPAESCustomerEntity();
  }

  @Override
  protected Class<JPAESCustomerEntity> getEntityClass() {
    return JPAESCustomerEntity.class;
  }
}
