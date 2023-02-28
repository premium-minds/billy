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
import com.premiumminds.billy.andorra.services.entities.ADBusiness;
import com.premiumminds.billy.andorra.services.persistence.ADBusinessPersistenceService;

public class Businesses {

    private final Injector injector;
    private final ADBusinessPersistenceService persistenceService;

    public Businesses(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ADBusinessPersistenceService.class);
    }

    public ADBusiness.Builder builder() {
        return this.getInstance(ADBusiness.Builder.class);
    }

    public ADBusiness.Builder builder(ADBusiness customer) {
        ADBusiness.Builder builder = this.getInstance(ADBusiness.Builder.class);
        BuilderManager.setTypeInstance(builder, customer);
        return builder;
    }

    public ADBusinessPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

}
