/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy portugal (PT Pack).
 * 
 * billy portugal (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy portugal (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.services.persistence.PTBusinessPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTCustomerPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTProductPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTRegionContextPersitenceService;
import com.premiumminds.billy.portugal.services.persistence.PTSupplierPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTTaxPersistenceService;

/**
 * {@link PersistenceServices} provides persistence of Billy's entities.
 */
public class PersistenceServices {

	private Injector injector;

	public PersistenceServices(Injector injector) {
		this.injector = injector;
	}

	/**
	 * @return {@link PTBusinessPersistenceService}.
	 */
	public PersistenceService<PTBusiness> business() {
		return new PTBusinessPersistenceService<PTBusiness>(injector);
	}

	/**
	 * @return {@link PTCustomerPersistenceService}.
	 */
	public PersistenceService<PTCustomer> customer() {
		return new PTCustomerPersistenceService<PTCustomer>(injector);
	}

	/**
	 * @return {@link PTProductPersistenceService}.
	 */
	public PersistenceService<PTProduct> product() {
		return new PTProductPersistenceService<PTProduct>(injector);
	}

	/**
	 * @return {@link PTRegionContextPersitenceService}.
	 */
	public PersistenceService<PTRegionContext> context() {
		return new PTRegionContextPersitenceService<PTRegionContext>(injector);
	}

	/**
	 * @return {@link PTSupplierPersistenceService}.
	 */
	public PersistenceService<PTSupplier> supplier() {
		return new PTSupplierPersistenceService<PTSupplier>(injector);
	}

	/**
	 * @return {@link PTTaxPersistenceService}.
	 */
	public PersistenceService<PTTax> tax() {
		return new PTTaxPersistenceService<PTTax>(injector);
	}
}
