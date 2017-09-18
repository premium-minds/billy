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
import com.premiumminds.billy.france.services.entities.FRProduct;
import com.premiumminds.billy.france.services.persistence.FRProductPersistenceService;

public class Products {

    private final Injector injector;
    private final FRProductPersistenceService persistenceService;

    public Products(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(FRProductPersistenceService.class);
    }

    public FRProduct.Builder builder() {
        return this.getInstance(FRProduct.Builder.class);
    }

    public FRProduct.Builder builder(FRProduct customer) {
        FRProduct.Builder builder = this.getInstance(FRProduct.Builder.class);
        BuilderManager.setTypeInstance(builder, customer);
        return builder;
    }

    public FRProductPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

}
