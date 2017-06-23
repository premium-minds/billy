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
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.persistence.PTApplicationPersistenceService;

public class Applications {

  private final Injector injector;
  private final PTApplicationPersistenceService persistenceService;

  public Applications(Injector injector) {
    this.injector = injector;
    this.persistenceService = getInstance(PTApplicationPersistenceService.class);
  }

  public PTApplication.Builder builder() {
    return getInstance(PTApplication.Builder.class);
  }

  public PTApplication.Builder builder(PTApplication application) {
    PTApplication.Builder builder = getInstance(PTApplication.Builder.class);
    BuilderManager.setTypeInstance(builder, application);
    return builder;
  }

  public PTApplicationPersistenceService persistence() {
    return this.persistenceService;
  }

  private <T> T getInstance(Class<T> clazz) {
    return this.injector.getInstance(clazz);
  }

}
