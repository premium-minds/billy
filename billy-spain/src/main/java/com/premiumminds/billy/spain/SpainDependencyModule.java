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
package com.premiumminds.billy.spain;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.premiumminds.billy.core.CoreDependencyModule;
import com.premiumminds.billy.core.CoreJPADependencyModule;
import com.premiumminds.billy.gin.GINDependencyModule;
import com.premiumminds.billy.spain.persistence.dao.DAOESAddress;
import com.premiumminds.billy.spain.persistence.dao.DAOESApplication;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESContact;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNoteEntry;
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
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESAddressImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESApplicationImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESBusinessImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESContactImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESCreditNoteEntryImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESCreditNoteImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESCustomerImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESGenericInvoiceEntryImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESGenericInvoiceImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESInvoiceEntryImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESInvoiceImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESPaymentImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESProductImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESReceiptEntryImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESReceiptImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESRegionContextImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESShippingPointImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESSimpleInvoiceImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESSupplierImpl;
import com.premiumminds.billy.spain.persistence.dao.jpa.DAOESTaxImpl;

public class SpainDependencyModule extends AbstractModule {

	@Override
	protected void configure() {
		this.install(new CoreDependencyModule());
		this.install(new CoreJPADependencyModule());
		this.install(new GINDependencyModule());

		this.bind(DAOESContact.class).to(DAOESContactImpl.class);
		this.bind(DAOESBusiness.class).to(DAOESBusinessImpl.class);
		this.bind(DAOESRegionContext.class).to(DAOESRegionContextImpl.class);
		this.bind(DAOESAddress.class).to(DAOESAddressImpl.class);
		this.bind(DAOESApplication.class).to(DAOESApplicationImpl.class);
		this.bind(DAOESTax.class).to(DAOESTaxImpl.class);
		this.bind(DAOESProduct.class).to(DAOESProductImpl.class);
		this.bind(DAOESSupplier.class).to(DAOESSupplierImpl.class);
		this.bind(DAOESShippingPoint.class).to(DAOESShippingPointImpl.class);
		this.bind(DAOESCustomer.class).to(DAOESCustomerImpl.class);
		this.bind(DAOESInvoice.class).to(DAOESInvoiceImpl.class);
		this.bind(DAOESInvoiceEntry.class).to(DAOESInvoiceEntryImpl.class);
		this.bind(DAOESCreditNote.class).to(DAOESCreditNoteImpl.class);
		this.bind(DAOESCreditNoteEntry.class)
				.to(DAOESCreditNoteEntryImpl.class);
		this.bind(DAOESGenericInvoice.class).to(DAOESGenericInvoiceImpl.class);
		this.bind(DAOESGenericInvoiceEntry.class).to(
				DAOESGenericInvoiceEntryImpl.class);
		this.bind(DAOESSimpleInvoice.class).to(DAOESSimpleInvoiceImpl.class);
		this.bind(DAOESReceipt.class).to(DAOESReceiptImpl.class);
		this.bind(DAOESReceiptEntry.class).to(DAOESReceiptEntryImpl.class);
		this.bind(DAOESPayment.class).to(DAOESPaymentImpl.class);
		this.bind(BillySpain.class);
	}

	public static class Initializer {

		@Inject
		public Initializer() {
			// Nothing to initialize
		}
	}

}
