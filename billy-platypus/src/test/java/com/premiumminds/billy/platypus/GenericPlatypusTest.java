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

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class GenericPlatypusTest {

	@BeforeClass 
	public static void setUpClass() {      
		//Injector injector = Guice.createInjector(new TestPlatypusDependencyModule());
		//injector.getInstance(TestPlatypusDependencyModule.Inicializer.class);
	}

	@Test
	public void test1() {
		assertEquals(2 , 2);
	}
}
