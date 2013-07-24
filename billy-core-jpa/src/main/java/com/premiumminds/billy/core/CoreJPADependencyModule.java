/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core JPA.
 *
 * billy core JPA is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core JPA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core;

import com.google.inject.AbstractModule;
import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.dao.DAOBankAccount;
import com.premiumminds.billy.core.persistence.dao.DAOBusiness;
import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOShippingPoint;
import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOAddressImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOApplicationImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOBankAccountImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOBusinessImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOContactImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOContextImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOCustomerImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOGenericInvoiceEntryImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOGenericInvoiceImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOProductImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOShippingPointImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOSupplierImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOTaxImpl;

public class CoreJPADependencyModule extends AbstractModule {

	@Override
	protected void configure() {
		// DAOs
		bind(DAOAddress.class).to(DAOAddressImpl.class);
		bind(DAOApplication.class).to(DAOApplicationImpl.class);
		bind(DAOBankAccount.class).to(DAOBankAccountImpl.class);
		bind(DAOBusiness.class).to(DAOBusinessImpl.class);
		bind(DAOContact.class).to(DAOContactImpl.class);
		bind(DAOContext.class).to(DAOContextImpl.class);
		bind(DAOCustomer.class).to(DAOCustomerImpl.class);
		bind(DAOGenericInvoice.class).to(DAOGenericInvoiceImpl.class);
		bind(DAOGenericInvoiceEntry.class).to(DAOGenericInvoiceEntryImpl.class);
		bind(DAOProduct.class).to(DAOProductImpl.class);
		bind(DAOShippingPoint.class).to(DAOShippingPointImpl.class);
		bind(DAOSupplier.class).to(DAOSupplierImpl.class);
		bind(DAOTax.class).to(DAOTaxImpl.class);
	}

	public static class Initializer {

		public Initializer() {
			// Nothing to initialize
		}
	}

}
