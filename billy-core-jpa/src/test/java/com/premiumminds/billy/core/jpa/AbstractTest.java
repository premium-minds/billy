/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core JPA.
 *
 * billy core JPA is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core JPA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.jpa;

import org.junit.BeforeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.CoreJPADependencyModule;
import com.premiumminds.billy.core.CoreJPAPersistenceDependencyModule;

public class AbstractTest {

	private static Injector injector;

	@BeforeClass
	public static void setUpClass() {
		AbstractTest.injector = Guice.createInjector(
				new CoreJPADependencyModule(),
				new CoreJPAPersistenceDependencyModule());
		AbstractTest.injector
				.getInstance(CoreJPAPersistenceDependencyModule.Initializer.class);
	}

	public <T> T getInstance(Class<T> clazz) {
		return AbstractTest.injector.getInstance(clazz);
	}

}
