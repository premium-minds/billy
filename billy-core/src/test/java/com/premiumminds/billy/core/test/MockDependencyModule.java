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

import static org.mockito.Mockito.mock;

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
		bind(DAOAddress.class).toInstance(mock(DAOAddress.class));
		bind(DAOApplication.class).toInstance(mock(DAOApplication.class));
		bind(DAOBankAccount.class).toInstance(mock(DAOBankAccount.class));
		bind(DAOBusiness.class).toInstance(mock(DAOBusiness.class));
		bind(DAOContact.class).toInstance(mock(DAOContact.class));
		bind(DAOContext.class).toInstance(mock(DAOContext.class));
		bind(DAOCustomer.class).toInstance(mock(DAOCustomer.class));
		bind(DAOGenericInvoice.class).toInstance(mock(DAOGenericInvoice.class));
		bind(DAOGenericInvoiceEntry.class).toInstance(mock(DAOGenericInvoiceEntry.class));
		bind(DAOProduct.class).toInstance(mock(DAOProduct.class));
		bind(DAOShippingPoint.class).toInstance(mock(DAOShippingPoint.class));
		bind(DAOSupplier.class).toInstance(mock(DAOSupplier.class));
		bind(DAOTax.class).toInstance(mock(DAOTax.class));
	}

}
