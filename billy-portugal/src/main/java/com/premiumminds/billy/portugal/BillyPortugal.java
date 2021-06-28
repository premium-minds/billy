/*
 * Copyright (C) 2017 Premium Minds.
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

import com.premiumminds.billy.portugal.util.ReceiptInvoices;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.premiumminds.billy.portugal.util.Addresses;
import com.premiumminds.billy.portugal.util.Applications;
import com.premiumminds.billy.portugal.util.Businesses;
import com.premiumminds.billy.portugal.util.Contacts;
import com.premiumminds.billy.portugal.util.Contexts;
import com.premiumminds.billy.portugal.util.CreditNotes;
import com.premiumminds.billy.portugal.util.Customers;
import com.premiumminds.billy.portugal.util.Invoices;
import com.premiumminds.billy.portugal.util.Payments;
import com.premiumminds.billy.portugal.util.Products;
import com.premiumminds.billy.portugal.util.SAFTs;
import com.premiumminds.billy.portugal.util.SimpleInvoices;
import com.premiumminds.billy.portugal.util.Taxes;

/**
 * Portuguese Module for Billy.
 *
 */
@Singleton
public class BillyPortugal {

    static final String DEFAULT_PERSISTENCE_UNIT = "BillyPortugalPersistenceUnit";

    private final Injector injector;

    private Contexts contexts;
    private Taxes taxes;
    private Customers customers;
    private Addresses addresses;
    private Businesses businesses;
    private Invoices invoices;
    private ReceiptInvoices receiptInvoices;
    private SimpleInvoices simpleInvoices;
    private CreditNotes creditNotes;
    private Products products;
    private SAFTs saft;
    private Applications applications;
    private Contacts contacts;
    private Payments payments;

    public BillyPortugal() {
        this(BillyPortugal.DEFAULT_PERSISTENCE_UNIT);
    }

    public BillyPortugal(String persistenceUnitId) {
        this.injector = Guice.createInjector(new PortugalDependencyModule(),
                new PortugalPersistenceDependencyModule(persistenceUnitId));
        this.injector.getInstance(PersistService.class).start();
    }

    @Inject
    public BillyPortugal(Injector injector) {
        this.injector = injector;
    }

    /**
     * Provides access to predefined taxes for Billy-Portugal module.
     *
     * @return {@link Taxes}
     */
    public Taxes taxes() {
        if (this.taxes == null) {
            this.taxes = new Taxes(this.injector);
        }
        return this.taxes;
    }

    public Customers customers() {
        if (this.customers == null) {
            this.customers = new Customers(this.injector);
        }
        return this.customers;
    }

    public Addresses addresses() {
        if (this.addresses == null) {
            this.addresses = new Addresses(this.injector);
        }
        return this.addresses;
    }

    public Businesses businesses() {
        if (this.businesses == null) {
            this.businesses = new Businesses(this.injector);
        }
        return this.businesses;
    }

    public Invoices invoices() {
        if (this.invoices == null) {
            this.invoices = new Invoices(this.injector);
        }
        return this.invoices;
    }

    public ReceiptInvoices receiptInvoices() {
        if (this.receiptInvoices == null) {
            this.receiptInvoices = new ReceiptInvoices(this.injector);
        }
        return this.receiptInvoices;
    }

    public SimpleInvoices simpleInvoices() {
        if (this.simpleInvoices == null) {
            this.simpleInvoices = new SimpleInvoices(this.injector);
        }
        return this.simpleInvoices;
    }

    public CreditNotes creditNotes() {
        if (this.creditNotes == null) {
            this.creditNotes = new CreditNotes(this.injector);
        }
        return this.creditNotes;
    }

    public Products products() {
        if (this.products == null) {
            this.products = new Products(this.injector);
        }
        return this.products;
    }

    public Contexts contexts() {
        if (this.contexts == null) {
            this.contexts = new Contexts(this.injector);
        }
        return this.contexts;
    }

    public SAFTs saft() {
        if (this.saft == null) {
            this.saft = new SAFTs(this.injector);
        }
        return this.saft;
    }

    public Applications applications() {
        if (this.applications == null) {
            this.applications = new Applications(this.injector);
        }
        return this.applications;
    }

    public Contacts contacts() {
        if (this.contacts == null) {
            this.contacts = new Contacts(this.injector);
        }
        return this.contacts;
    }

    public Payments payments() {
        if (this.payments == null) {
            this.payments = new Payments(this.injector);
        }
        return this.payments;
    }

}
