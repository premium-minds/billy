/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
 *
 * billy is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.platypus;

import org.junit.BeforeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.portugal.PlatypusBootstrap;
import com.premiumminds.billy.portugal.PlatypusDependencyModule;

public abstract class PlatypusBaseTest {

	private static Injector injector;

	@BeforeClass
	protected static void setup() {
		setupDependencies();
		setupPersistence();
	}

	private static void setupDependencies() {
		injector = Guice.createInjector(
				new PlatypusDependencyModule(),
				new PlatypusTestPersistenceDependencyModule());
	}

	private static void setupPersistence() {
		getInstance(PlatypusTestPersistenceDependencyModule.Initializer.class);
		bootstrap();
	}
	
	protected static void bootstrap() {
		PlatypusBootstrap.execute(injector);
	}

	private static Injector getInjector() {
		return injector;
	}

	protected static <T> T getInstance(Class<T> clazz) {
		return getInjector().getInstance(clazz);
	}

}
