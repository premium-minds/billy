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
package com.premiumminds.billy.spain.services.persistence;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESTaxEntity;
import com.premiumminds.billy.spain.services.entities.ESTax;

public class ESTaxPersistenceService implements PersistenceService<ESTax> {

  protected final DAOESTax daoTax;

  @Inject
  public ESTaxPersistenceService(DAOESTax daoTax) {
    this.daoTax = daoTax;
  }

  @Override
  public ESTax create(final Builder<ESTax> builder) {
    try {
      return new TransactionWrapper<ESTax>(daoTax) {

        @Override
        public ESTax runTransaction() throws Exception {
          ESTaxEntity entity = (ESTaxEntity) builder.build();
          return (ESTax) daoTax.create(entity);
        }

      }.execute();
    } catch (Exception e) {
      throw new BillyRuntimeException(e);
    }
  }

  @Override
  public ESTax update(final Builder<ESTax> builder) {
    try {
      return new TransactionWrapper<ESTax>(daoTax) {

        @Override
        public ESTax runTransaction() throws Exception {
          ESTaxEntity entity = (ESTaxEntity) builder.build();
          return (ESTax) daoTax.update(entity);
        }

      }.execute();
    } catch (Exception e) {
      throw new BillyRuntimeException(e);
    }
  }

  @Override
  public ESTax get(final UID uid) {
    try {
      return new TransactionWrapper<ESTax>(daoTax) {

        @Override
        public ESTax runTransaction() throws Exception {
          return (ESTax) daoTax.get(uid);
        }

      }.execute();
    } catch (Exception e) {
      throw new BillyRuntimeException(e);
    }
  }

}
