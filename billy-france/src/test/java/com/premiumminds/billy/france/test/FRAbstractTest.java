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
package com.premiumminds.billy.france.test;

import org.junit.BeforeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.FranceDependencyModule;

public class FRAbstractTest extends AbstractTest {

    protected static Injector injector;
    protected static final String FR_COUNTRY_CODE = "FR";

    @BeforeClass
    public static void setUpClass() {
        FRAbstractTest.injector =
                Guice.createInjector(Modules.override(new FranceDependencyModule()).with(new FRMockDependencyModule()));
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return FRAbstractTest.injector.getInstance(clazz);
    }

}
