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
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.dao.DAOPayment;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOShippingPoint;
import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOAddressImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOApplicationImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOBankAccountImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOBusinessImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOContactImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOContextImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOCustomerImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOGenericInvoiceEntryImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOGenericInvoiceImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOInvoiceSeriesImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOPaymentImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOProductImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOShippingPointImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOSupplierImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOTaxImpl;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOTicketImpl;

public class CoreJPADependencyModule extends AbstractModule {

	@Override
	protected void configure() {
		// DAOs
		this.bind(DAOAddress.class).to(DAOAddressImpl.class);
		this.bind(DAOApplication.class).to(DAOApplicationImpl.class);
		this.bind(DAOBankAccount.class).to(DAOBankAccountImpl.class);
		this.bind(DAOBusiness.class).to(DAOBusinessImpl.class);
		this.bind(DAOContact.class).to(DAOContactImpl.class);
		this.bind(DAOContext.class).to(DAOContextImpl.class);
		this.bind(DAOCustomer.class).to(DAOCustomerImpl.class);
		this.bind(DAOGenericInvoice.class).to(DAOGenericInvoiceImpl.class);
		this.bind(DAOGenericInvoiceEntry.class).to(
				DAOGenericInvoiceEntryImpl.class);
		this.bind(DAOProduct.class).to(DAOProductImpl.class);
		this.bind(DAOShippingPoint.class).to(DAOShippingPointImpl.class);
		this.bind(DAOSupplier.class).to(DAOSupplierImpl.class);
		this.bind(DAOTax.class).to(DAOTaxImpl.class);
		this.bind(DAOTicket.class).to(DAOTicketImpl.class);
		this.bind(DAOPayment.class).to(DAOPaymentImpl.class);
		this.bind(DAOInvoiceSeries.class).to(DAOInvoiceSeriesImpl.class);
	}

	public static class Initializer {

		public Initializer() {
			// Nothing to initialize
		}
	}

}
