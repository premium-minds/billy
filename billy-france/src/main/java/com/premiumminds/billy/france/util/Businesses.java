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
package com.premiumminds.billy.france.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.france.services.entities.FRBusiness;
import com.premiumminds.billy.france.services.persistence.FRBusinessPersistenceService;

public class Businesses {

    private final Injector injector;
    private final FRBusinessPersistenceService persistenceService;

    public Businesses(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(FRBusinessPersistenceService.class);
    }

    public FRBusiness.Builder builder() {
        return this.getInstance(FRBusiness.Builder.class);
    }

    public FRBusiness.Builder builder(FRBusiness customer) {
        FRBusiness.Builder builder = this.getInstance(FRBusiness.Builder.class);
        BuilderManager.setTypeInstance(builder, customer);
        return builder;
    }

    public FRBusinessPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

}
