/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.test;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.persist.jpa.JpaPersistOptions;

public class AndorraTestPersistenceDependencyModule extends AbstractModule {

    @Override
    protected void configure() {
        final var options = JpaPersistOptions.builder().setAutoBeginWorkOnEntityManagerCreation(true).build();
        JpaPersistModule persistModule = new JpaPersistModule("BillyAndorraTestPersistenceUnit", options);
        this.install(persistModule);
    }

    public static class Initializer {

        @Inject
        public Initializer(PersistService persistService) {
            persistService.start();
        }
    }

    public static class Finalizer {

        @Inject
        public Finalizer(PersistService persistService) {
            persistService.stop();
        }
    }

}
