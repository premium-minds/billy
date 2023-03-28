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
import com.premiumminds.billy.andorra.Config;
import com.premiumminds.billy.andorra.services.entities.ADCustomer;
import com.premiumminds.billy.andorra.services.persistence.ADCustomerPersistenceService;

public class Customers {

    private Config configuration = new Config();
    private final Injector injector;
    private final ADCustomerPersistenceService persistenceService;

    public Customers(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ADCustomerPersistenceService.class);
    }

    public ADCustomer.Builder builder() {
        return this.getInstance(ADCustomer.Builder.class);
    }

    public ADCustomer.Builder builder(ADCustomer customer) {
        ADCustomer.Builder builder = this.getInstance(ADCustomer.Builder.class);
        BuilderManager.setTypeInstance(builder, customer);
        return builder;
    }

    public ADCustomerPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

}
