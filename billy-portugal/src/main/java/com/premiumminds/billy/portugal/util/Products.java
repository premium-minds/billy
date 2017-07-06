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
package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.services.persistence.PTProductPersistenceService;

public class Products {

  private final Injector injector;
  private final PTProductPersistenceService persistenceService;

  public Products(Injector injector) {
    this.injector = injector;
    this.persistenceService = getInstance(PTProductPersistenceService.class);
  }

  public PTProduct.Builder builder() {
    return getInstance(PTProduct.Builder.class);
  }

  public PTProduct.Builder builder(PTProduct customer) {
    PTProduct.Builder builder = getInstance(PTProduct.Builder.class);
    BuilderManager.setTypeInstance(builder, customer);
    return builder;
  }

  public PTProductPersistenceService persistence() {
    return this.persistenceService;
  }

  private <T> T getInstance(Class<T> clazz) {
    return this.injector.getInstance(clazz);
  }

}
