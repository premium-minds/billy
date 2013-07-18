/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.premiumminds.billy.core.CoreDependencyModule;
import com.premiumminds.billy.core.CoreJPADependencyModule;
import com.premiumminds.billy.gin.GINDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTApplication;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTAddressImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTApplicationImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTContactImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTProductImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTRegionContextImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTSupplierImpl;
import com.premiumminds.billy.portugal.persistence.dao.jpa.DAOPTTaxImpl;

public class PlatypusDependencyModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new CoreDependencyModule());
		install(new CoreJPADependencyModule());
		install(new GINDependencyModule());

		// bind(DAOPTFinancialDocument.class).to(DAOPTFinancialDocumentImpl.class);
		// bind(DAOPTInvoice.class).to(DAOPTInvoiceImpl.class);
		// bind(DAOPTSimpleInvoice.class).to(DAOPTSimpleInvoiceImpl.class);
		// bind(DAOPTCreditNote.class).to(DAOPTCreditNoteImpl.class);
		// bind(DAOPTBusiness.class).to(DAOPTBusinessImpl.class);
		// bind(DAOPTFinancialDocumentEntry.class).to(DAOPTFinancialDocumentEntryImpl.class);
		// bind(DAOPTTax.class).to(DAOPTTaxImpl.class);

		bind(DAOPTContact.class).to(DAOPTContactImpl.class);
		bind(DAOPTRegionContext.class).to(DAOPTRegionContextImpl.class);
		bind(DAOPTAddress.class).to(DAOPTAddressImpl.class);
		bind(DAOPTApplication.class).to(DAOPTApplicationImpl.class);
		bind(DAOPTTax.class).to(DAOPTTaxImpl.class);
		bind(DAOPTProduct.class).to(DAOPTProductImpl.class);
		bind(DAOPTSupplier.class).to(DAOPTSupplierImpl.class);
	}

	public static class Initializer {

		@Inject
		public Initializer() {
			// Nothing to initialize
		}
	}

}
