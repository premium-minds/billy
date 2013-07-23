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

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.premiumminds.billy.core.CoreDependencyModule;
import com.premiumminds.billy.core.CoreJPADependencyModule;
import com.premiumminds.billy.gin.GINDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTApplication;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTShippingPoint;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTAddressImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTApplicationImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTBusinessImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTContactImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTCustomerImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTProductImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTRegionContextImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTShippingPointImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTSupplierImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTTaxImpl;

public class PlatypusDependencyModule extends AbstractModule {

	@Override
	protected void configure() {
		this.install(new CoreDependencyModule());
		this.install(new CoreJPADependencyModule());
		this.install(new GINDependencyModule());

		this.bind(DAOPTContact.class).to(DAOPTContactImpl.class);
		this.bind(DAOPTBusiness.class).to(DAOPTBusinessImpl.class);
		this.bind(DAOPTRegionContext.class).to(DAOPTRegionContextImpl.class);
		this.bind(DAOPTAddress.class).to(DAOPTAddressImpl.class);
		this.bind(DAOPTApplication.class).to(DAOPTApplicationImpl.class);
		this.bind(DAOPTTax.class).to(DAOPTTaxImpl.class);
		this.bind(DAOPTProduct.class).to(DAOPTProductImpl.class);
		this.bind(DAOPTSupplier.class).to(DAOPTSupplierImpl.class);
		this.bind(DAOPTShippingPoint.class).to(DAOPTShippingPointImpl.class);
		this.bind(DAOPTCustomer.class).to(DAOPTCustomerImpl.class);
	}

	public static class Initializer {

		@Inject
		public Initializer() {
			// Nothing to initialize
		}
	}

}
