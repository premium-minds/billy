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
package com.premiumminds.billy.portugal;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.premiumminds.billy.portugal.util.Builders;
import com.premiumminds.billy.portugal.util.Contexts;
import com.premiumminds.billy.portugal.util.Customers;
import com.premiumminds.billy.portugal.util.Services;
import com.premiumminds.billy.portugal.util.Taxes;

/**
 * Portuguese Module for Billy.
 * 
 */
public class BillyPortugal {

	private static final String	DEFAULT_PERSISTENCE_UNIT	= "BillyPortugalPersistenceUnit";

	private Injector			injector;
	private Builders			builders;
	private Taxes				taxes;
	private Services			services;
	private Customers			customers;
	private Contexts			contexts;

	public BillyPortugal() {
		this(Guice.createInjector(new JpaPersistModule(
				BillyPortugal.DEFAULT_PERSISTENCE_UNIT)));
		this.injector.getInstance(PersistService.class).start();
		setup();
	}

	public BillyPortugal(Injector injector) {
		this.injector = injector
				.createChildInjector(new PortugalDependencyModule());
	}

	public void setup() {
		this.builders = new Builders(this.injector);
		this.taxes = new Taxes(this.injector);
		this.contexts = new Contexts(this.injector);
		this.services = new Services(this.injector);
		this.customers = new Customers(this.injector);
	}

	/**
	 * Provides access to Billy-Portugal entity builders.
	 * 
	 * @return {@link Builders}
	 */
	public Builders builders() {
		return this.builders;
	}

	/**
	 * Provides access to predefined taxes for Billy-Portugal module.
	 * 
	 * @return {@link Taxes}
	 */
	public Taxes taxes() {
		return this.taxes;
	}

	/**
	 * Provides access to persistence and document issuing services.
	 * 
	 * @return {@link Services}
	 */
	public Services services() {
		return this.services;
	}

	/**
	 * Provides access to the different contexts for Billy.
	 * 
	 * @return {@link Contexts}
	 */
	public Contexts contexts() {
		return this.contexts;
	}

	/**
	 * Provides access to customers.
	 * 
	 * @return {@link Customers}
	 */
	public Customers customers() {
		return this.customers;
	}
}
