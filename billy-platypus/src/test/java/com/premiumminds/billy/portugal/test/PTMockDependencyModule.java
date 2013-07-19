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
package com.premiumminds.billy.portugal.test;

import static org.mockito.Mockito.mock;

import com.premiumminds.billy.core.test.MockDependencyModule;
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

public class PTMockDependencyModule extends MockDependencyModule {

	@Override
	protected void configure() {
		super.configure();
		bind(DAOPTRegionContext.class).toInstance(
				mock(DAOPTRegionContext.class));
		bind(DAOPTContact.class).toInstance(mock(DAOPTContact.class));
		bind(DAOPTAddress.class).toInstance(mock(DAOPTAddress.class));
		bind(DAOPTApplication.class).toInstance(mock(DAOPTApplication.class));
		bind(DAOPTTax.class).toInstance(mock(DAOPTTax.class));
		bind(DAOPTProduct.class).toInstance(mock(DAOPTProduct.class));
		bind(DAOPTSupplier.class).toInstance(mock(DAOPTSupplier.class));
		bind(DAOPTBusiness.class).toInstance(mock(DAOPTBusiness.class));
		bind(DAOPTShippingPoint.class).toInstance(
				mock(DAOPTShippingPoint.class));
		bind(DAOPTCustomer.class).toInstance(mock(DAOPTCustomer.class));
	}

}
