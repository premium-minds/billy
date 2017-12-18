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
package com.premiumminds.billy.france;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public class FrancePersistenceDependencyModule extends AbstractModule {

    private final String persistenceUnitId;

    public FrancePersistenceDependencyModule(String persistenceUnitId) {
        this.persistenceUnitId = persistenceUnitId;
    }

    @Override
    protected void configure() {
        JpaPersistModule persistModule = new JpaPersistModule(this.persistenceUnitId);
        this.install(persistModule);
    }

    public static class Initializer {

        @Inject
        public Initializer(PersistService persistService) {
            persistService.start();
        }
    }

}
