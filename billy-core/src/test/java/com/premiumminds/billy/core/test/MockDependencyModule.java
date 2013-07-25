/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy core.
 * 
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test;

import org.mockito.Mockito;

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

public class MockDependencyModule extends AbstractModule {

	@Override
	protected void configure() {
		this.bind(DAOAddress.class).toInstance(Mockito.mock(DAOAddress.class));
		this.bind(DAOApplication.class).toInstance(
				Mockito.mock(DAOApplication.class));
		this.bind(DAOBankAccount.class).toInstance(
				Mockito.mock(DAOBankAccount.class));
		this.bind(DAOBusiness.class)
				.toInstance(Mockito.mock(DAOBusiness.class));
		this.bind(DAOContact.class).toInstance(Mockito.mock(DAOContact.class));
		this.bind(DAOContext.class).toInstance(Mockito.mock(DAOContext.class));
		this.bind(DAOCustomer.class)
				.toInstance(Mockito.mock(DAOCustomer.class));
		this.bind(DAOGenericInvoice.class).toInstance(
				Mockito.mock(DAOGenericInvoice.class));
		this.bind(DAOGenericInvoiceEntry.class).toInstance(
				Mockito.mock(DAOGenericInvoiceEntry.class));
		this.bind(DAOProduct.class).toInstance(Mockito.mock(DAOProduct.class));
		this.bind(DAOShippingPoint.class).toInstance(
				Mockito.mock(DAOShippingPoint.class));
		this.bind(DAOSupplier.class)
				.toInstance(Mockito.mock(DAOSupplier.class));
		this.bind(DAOTax.class).toInstance(Mockito.mock(DAOTax.class));
	}

}
