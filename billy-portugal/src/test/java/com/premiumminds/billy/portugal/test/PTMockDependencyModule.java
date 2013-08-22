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
package com.premiumminds.billy.portugal.test;

import org.mockito.Mockito;

import com.premiumminds.billy.core.test.MockDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTApplication;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTPayment;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTShippingPoint;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTCreditNoteEntryImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTCreditNoteImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTGenericInvoiceEntryImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTGenericInvoiceImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTPaymentImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTReceiptInvoiceImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTSimpleInvoiceImpl;

public class PTMockDependencyModule extends MockDependencyModule {

	@Override
	protected void configure() {
		super.configure();

		this.bind(DAOPTRegionContext.class).toInstance(
				Mockito.mock(DAOPTRegionContext.class));
		this.bind(DAOPTContact.class).toInstance(
				Mockito.mock(DAOPTContact.class));
		this.bind(DAOPTAddress.class).toInstance(
				Mockito.mock(DAOPTAddress.class));
		this.bind(DAOPTApplication.class).toInstance(
				Mockito.mock(DAOPTApplication.class));
		this.bind(DAOPTTax.class).toInstance(Mockito.mock(DAOPTTax.class));
		this.bind(DAOPTProduct.class).toInstance(
				Mockito.mock(DAOPTProduct.class));
		this.bind(DAOPTSupplier.class).toInstance(
				Mockito.mock(DAOPTSupplier.class));
		this.bind(DAOPTBusiness.class).toInstance(
				Mockito.mock(DAOPTBusiness.class));
		this.bind(DAOPTShippingPoint.class).toInstance(
				Mockito.mock(DAOPTShippingPoint.class));
		this.bind(DAOPTCustomer.class).toInstance(
				Mockito.mock(DAOPTCustomer.class));
		this.bind(DAOPTInvoiceEntry.class).toInstance(
				Mockito.mock(DAOPTInvoiceEntry.class));
		this.bind(DAOPTInvoice.class).toInstance(
				Mockito.mock(DAOPTInvoice.class));
		this.bind(DAOPTCreditNote.class).toInstance(
				Mockito.mock(DAOPTCreditNoteImpl.class));
		this.bind(DAOPTCreditNoteEntry.class).toInstance(
				Mockito.mock(DAOPTCreditNoteEntryImpl.class));
		this.bind(DAOPTGenericInvoice.class).toInstance(
				Mockito.mock(DAOPTGenericInvoiceImpl.class));
		this.bind(DAOPTGenericInvoiceEntry.class).toInstance(
				Mockito.mock(DAOPTGenericInvoiceEntryImpl.class));
		this.bind(DAOPTSimpleInvoice.class).toInstance(
				Mockito.mock(DAOPTSimpleInvoiceImpl.class));
		this.bind(DAOPTPayment.class).toInstance(
				Mockito.mock(DAOPTPaymentImpl.class));
		this.bind(DAOPTReceiptInvoice.class).toInstance(
				Mockito.mock(DAOPTReceiptInvoiceImpl.class));
	}

}
