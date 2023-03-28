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

import com.premiumminds.billy.andorra.AndorraDependencyModule;
import org.junit.jupiter.api.BeforeAll;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.test.AbstractTest;

public class ADAbstractTest extends AbstractTest {

    protected static Injector injector;
    protected static final String AD_COUNTRY_CODE = "AD";

    @BeforeAll
    public static void setUpClass() {
        ADAbstractTest.injector =
                Guice.createInjector(Modules.override(new AndorraDependencyModule()).with(new ADMockDependencyModule()));
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return ADAbstractTest.injector.getInstance(clazz);
    }

}
