/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test;

import org.junit.BeforeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.PlatypusDependencyModule;

public class PTAbstractTest extends AbstractTest {

	private static Injector injector;

	@BeforeClass
	public static void setUpClass() {
		PTAbstractTest.injector = Guice.createInjector(Modules.override(
				new PlatypusDependencyModule()).with(
				new PTMockDependencyModule()));
	}

	public <T> T getInstance(Class<T> clazz) {
		return PTAbstractTest.injector.getInstance(clazz);
	}

}
