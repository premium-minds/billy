/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.test;

import org.mockito.Mockito;

import com.premiumminds.billy.core.test.MockDependencyModule;
import com.premiumminds.billy.spain.persistence.dao.DAOESAddress;
import com.premiumminds.billy.spain.persistence.dao.DAOESApplication;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESContact;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNoteEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceiptEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESPayment;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceiptEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESShippingPoint;
import com.premiumminds.billy.spain.persistence.dao.DAOESSimpleInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESCreditNoteEntryImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESCreditNoteImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESCreditReceiptEntryImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESCreditReceiptImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESGenericInvoiceEntryImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESGenericInvoiceImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESPaymentImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESSimpleInvoiceImpl;

public class ESMockDependencyModule extends MockDependencyModule {

	@Override
	protected void configure() {
		super.configure();

		this.bind(DAOESRegionContext.class).toInstance(
				Mockito.mock(DAOESRegionContext.class));
		this.bind(DAOESContact.class).toInstance(
				Mockito.mock(DAOESContact.class));
		this.bind(DAOESAddress.class).toInstance(
				Mockito.mock(DAOESAddress.class));
		this.bind(DAOESApplication.class).toInstance(
				Mockito.mock(DAOESApplication.class));
		this.bind(DAOESTax.class).toInstance(Mockito.mock(DAOESTax.class));
		this.bind(DAOESProduct.class).toInstance(
				Mockito.mock(DAOESProduct.class));
		this.bind(DAOESSupplier.class).toInstance(
				Mockito.mock(DAOESSupplier.class));
		this.bind(DAOESBusiness.class).toInstance(
				Mockito.mock(DAOESBusiness.class));
		this.bind(DAOESShippingPoint.class).toInstance(
				Mockito.mock(DAOESShippingPoint.class));
		this.bind(DAOESCustomer.class).toInstance(
				Mockito.mock(DAOESCustomer.class));
		this.bind(DAOESInvoiceEntry.class).toInstance(
				Mockito.mock(DAOESInvoiceEntry.class));
		this.bind(DAOESInvoice.class).toInstance(
				Mockito.mock(DAOESInvoice.class));
		this.bind(DAOESCreditNote.class).toInstance(
				Mockito.mock(DAOESCreditNoteImpl.class));
		this.bind(DAOESCreditNoteEntry.class).toInstance(
				Mockito.mock(DAOESCreditNoteEntryImpl.class));
		this.bind(DAOESGenericInvoice.class).toInstance(
				Mockito.mock(DAOESGenericInvoiceImpl.class));
		this.bind(DAOESGenericInvoiceEntry.class).toInstance(
				Mockito.mock(DAOESGenericInvoiceEntryImpl.class));
		this.bind(DAOESSimpleInvoice.class).toInstance(
				Mockito.mock(DAOESSimpleInvoiceImpl.class));
		this.bind(DAOESPayment.class).toInstance(
				Mockito.mock(DAOESPaymentImpl.class));
		this.bind(DAOESReceipt.class).toInstance(
				Mockito.mock(DAOESReceipt.class));
		this.bind(DAOESReceiptEntry.class).toInstance(
				Mockito.mock(DAOESReceiptEntry.class));
		this.bind(DAOESCreditReceipt.class).toInstance(
				Mockito.mock(DAOESCreditReceiptImpl.class));
		this.bind(DAOESCreditReceiptEntry.class).toInstance(
				Mockito.mock(DAOESCreditReceiptEntryImpl.class));
	}

}
