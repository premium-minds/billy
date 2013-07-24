/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.premiumminds.billy.portugal.util.Builders;
import com.premiumminds.billy.portugal.util.Services;
import com.premiumminds.billy.portugal.util.Taxes;

public class BillyPortugal {

	private static final String DEFAULT_PERSISTENCE_UNIT = "BillyPlatypusPersistenceUnit";

	private final Injector injector;
	private final Builders builders;
	private final Taxes taxes;
	private final Services services;

	public BillyPortugal() {
		this(new JpaPersistModule(DEFAULT_PERSISTENCE_UNIT));
	}

	public BillyPortugal(PersistModule persistModule) {
		injector = Guice.createInjector(new PlatypusDependencyModule(), persistModule);
		builders = new Builders(injector);
		taxes = new Taxes(injector);
		services = new Services(injector);
	}

	public Builders builders() {
		return this.builders;
	}
	
	public Taxes taxes() {
		return taxes;
	}
	
	public Services services() {
		return services;
	}

	private <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

}
