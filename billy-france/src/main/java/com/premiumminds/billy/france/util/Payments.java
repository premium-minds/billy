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
import com.premiumminds.billy.france.services.entities.FRPayment;

public class Payments {

    private final Injector injector;

    public Payments(Injector injector) {
        this.injector = injector;
    }

    public FRPayment.Builder builder() {
        return this.getInstance(FRPayment.Builder.class);
    }

    public FRPayment.Builder builder(FRPayment payment) {
        FRPayment.Builder builder = this.getInstance(FRPayment.Builder.class);
        BuilderManager.setTypeInstance(builder, payment);
        return builder;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
