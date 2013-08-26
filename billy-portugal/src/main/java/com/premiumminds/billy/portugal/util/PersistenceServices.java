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
package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.services.persistence.PTBusinessPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTCreditNotePersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTCustomerPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTInvoicePersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTProductPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTRegionContextPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTSimpleInvoicePersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTSupplierPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTTaxPersistenceService;

/**
 * {@link PersistenceServices} provides persistence of Billy's entities.
 */
public class PersistenceServices {

	private Injector	injector;

	public PersistenceServices(Injector injector) {
		this.injector = injector;
	}

	/**
	 * @return {@link PTBusinessPersistenceService}.
	 */
	public PersistenceService<PTBusiness> business() {
		return injector.getInstance(PTBusinessPersistenceService.class);
	}

	/**
	 * @return {@link PTCustomerPersistenceService}.
	 */
	public PersistenceService<PTCustomer> customer() {
		return injector.getInstance(PTCustomerPersistenceService.class);
	}

	/**
	 * @return {@link PTProductPersistenceService}.
	 */
	public PersistenceService<PTProduct> product() {
		return injector.getInstance(PTProductPersistenceService.class);
	}

	/**
	 * @return {@link PTRegionContextPersistenceService}.
	 */
	public PersistenceService<PTRegionContext> context() {
		return injector.getInstance(PTRegionContextPersistenceService.class);
	}

	/**
	 * @return {@link PTSupplierPersistenceService}.
	 */
	public PersistenceService<PTSupplier> supplier() {
		return injector.getInstance(PTSupplierPersistenceService.class);
	}

	/**
	 * @return {@link PTTaxPersistenceService}.
	 */
	public PersistenceService<PTTax> tax() {
		return injector.getInstance(PTTaxPersistenceService.class);
	}

	/**
	 * @return {@link PTSimpleInvoicePersistenceService}.
	 */
	public PersistenceService<PTInvoice> invoice() {
		return injector.getInstance(PTInvoicePersistenceService.class);
	}

	/**
	 * @return {@link PTSimpleInvoicePersistenceService}.
	 */
	public PersistenceService<PTSimpleInvoice> simpleInvoice() {
		return injector.getInstance(PTSimpleInvoicePersistenceService.class);
	}

	/**
	 * @return {@link PTCreditNotePersistenceService}.
	 */
	public PersistenceService<PTCreditNote> creditNote() {
		return injector.getInstance(PTCreditNotePersistenceService.class);
	}
}
