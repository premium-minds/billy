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
package com.premiumminds.billy.andorra.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.andorra.services.entities.ADProduct;
import com.premiumminds.billy.andorra.services.persistence.ADProductPersistenceService;

public class Products {

    private final Injector injector;
    private final ADProductPersistenceService persistenceService;

    public Products(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ADProductPersistenceService.class);
    }

    public ADProduct.Builder builder() {
        return this.getInstance(ADProduct.Builder.class);
    }

    public ADProduct.Builder builder(ADProduct product) {
        ADProduct.Builder builder = this.getInstance(ADProduct.Builder.class);
        BuilderManager.setTypeInstance(builder, product);
        return builder;
    }

    public ADProductPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

}
