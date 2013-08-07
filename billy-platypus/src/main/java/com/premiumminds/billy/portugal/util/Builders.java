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
package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTShippingPoint;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;
import com.premiumminds.billy.portugal.services.entities.PTTax;

public class Builders {

	public class Invoices {

		public PTInvoice.Builder createInvoiceBuilder() {
			return Builders.this.getInstance(PTInvoice.Builder.class);
		}

		public PTSimpleInvoice.Builder createSimpleInvoiceBuilder() {
			return Builders.this.getInstance(PTSimpleInvoice.Builder.class);
		}

		public PTCreditNote.Builder createCreditNoteBuilder() {
			return Builders.this.getInstance(PTCreditNote.Builder.class);
		}

	}

	public PTAddress.Builder createAddressBuilder() {
		return this.getInstance(PTAddress.Builder.class);
	}

	public PTContact.Builder createContactBuilder() {
		return this.getInstance(PTContact.Builder.class);
	}

	public PTProduct.Builder createProductBuilder() {
		return this.getInstance(PTProduct.Builder.class);
	}

	public PTShippingPoint.Builder createShippingPointBuilder() {
		return this.getInstance(PTShippingPoint.Builder.class);
	}

	public PTSupplier.Builder createSupplierBuilder() {
		return this.getInstance(PTSupplier.Builder.class);
	}

	public class Configuration {

		public PTApplication.Builder createApplicationBuilder() {
			return Builders.this.getInstance(PTApplication.Builder.class);
		}

		public PTRegionContext.Builder createRegionContextBuilder() {
			return Builders.this.getInstance(PTRegionContext.Builder.class);
		}

		public PTTax.Builder createTaxBuilder() {
			return Builders.this.getInstance(PTTax.Builder.class);
		}

		public PTBusiness.Builder createBusinessBuilder() {
			return Builders.this.getInstance(PTBusiness.Builder.class);
		}

	}

	private final Configuration configurationBuilders;
	private final Invoices invoiceBuilders;
	private final Injector injector;

	public Builders(Injector injector) {
		this.configurationBuilders = new Configuration();
		this.invoiceBuilders = new Invoices();
		this.injector = injector;
	}

	public Configuration configuration() {
		return this.configurationBuilders;
	}

	public Invoices invoices() {
		return this.invoiceBuilders;
	}

	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}

}
