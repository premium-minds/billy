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

import javax.inject.Singleton;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistModule;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.premiumminds.billy.portugal.util.Addresses;
import com.premiumminds.billy.portugal.util.Businesses;
import com.premiumminds.billy.portugal.util.Contexts;
import com.premiumminds.billy.portugal.util.Customers;
import com.premiumminds.billy.portugal.util.Invoices;
import com.premiumminds.billy.portugal.util.SimpleInvoices;
import com.premiumminds.billy.portugal.util.Taxes;

/**
 * Portuguese Module for Billy.
 * 
 */
@Singleton
public class BillyPortugal {

	private static final String	DEFAULT_PERSISTENCE_UNIT	= "BillyPortugalPersistenceUnit";

	private final Injector		injector;

	private final Contexts contexts;
	private final Taxes			taxes;
	private final Customers customers;
	private final Addresses addresses;
	private Businesses businesses;
	private final Invoices invoices;
	private final SimpleInvoices simpleInvoices;
	private final CreditNotes creditNotes;


	public BillyPortugal() {
		this(new JpaPersistModule(BillyPortugal.DEFAULT_PERSISTENCE_UNIT));
	}

	public BillyPortugal(PersistModule persistModule) {
		this.injector = Guice.createInjector(new PortugalDependencyModule(),
				persistModule);
		this.injector.getInstance(PersistService.class).start();
		this.taxes = new Taxes(this.injector);
		this.customers = new Customers(injector);
		this.addresses = new Addresses(injector);
		this.businesses = new Businesses(injector);
		this.invoices = new Invoices(injector);
		this.simpleInvoices = new SimpleInvoices(injector);
		this.creditNotes = new CreditNotes(injector);
		this.contexts = new Contexts(injector);
	}

	/**
	 * Provides access to predefined taxes for Billy-Portugal module.
	 * 
	 * @return {@link Taxes}
	 */
	public Taxes taxes() {
		return this.taxes;
	}

	public Customers customers() {
		return this.customers;
	}
	
	public Addresses addresses() {
		return this.addresses;
	}
	
	public Businesses businesses() {
		return this.businesses;
	}
	
	public Invoices invoices() {
		return this.invoices;
	}
	
	public SimpleInvoices simpleInvoices() {
		return this.simpleInvoices;
	}

	public CreditNotes creditNotes() {
		return this.creditNotes;
	}

	public Contexts contexts() {
		return this.contexts;
	}
	
	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}

}
