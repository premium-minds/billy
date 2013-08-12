/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test;

import org.junit.After;
import org.junit.Before;

import com.google.inject.Guice;
import com.premiumminds.billy.portugal.PortugalBootstrap;
import com.premiumminds.billy.portugal.PortugalDependencyModule;

public class PTPersistencyAbstractTest extends PTAbstractTest {

	@Before
	public void setUpModules() {
		PTAbstractTest.injector = Guice.createInjector(
				new PortugalDependencyModule(),
				new PortugalTestPersistenceDependencyModule());
		PTAbstractTest.injector
				.getInstance(PortugalDependencyModule.Initializer.class);
		PTAbstractTest.injector
				.getInstance(PortugalTestPersistenceDependencyModule.Initializer.class);
		PortugalBootstrap.execute(PTAbstractTest.injector);
	}

	@After
	public void tearDown() {
		PTAbstractTest.injector
				.getInstance(PortugalTestPersistenceDependencyModule.Finalizer.class);
	}

}
