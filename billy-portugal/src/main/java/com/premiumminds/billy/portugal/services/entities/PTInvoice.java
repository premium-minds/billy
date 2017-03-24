/**
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.portugal.services.entities;

import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.services.builders.impl.PTInvoiceBuilderImpl;
import com.premiumminds.billy.portugal.services.builders.impl.PTManualInvoiceBuilderImpl;

public interface PTInvoice extends PTGenericInvoice {

	public static class Builder extends
		PTInvoiceBuilderImpl<Builder, PTInvoiceEntry, PTInvoice> {

		@Inject
		public Builder(DAOPTInvoice daoPTInvoice, DAOPTBusiness daoPTBusiness,
						DAOPTCustomer daoPTCustomer, DAOPTSupplier daoPTSupplier) {
			super(daoPTInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
		}
	}
	
	public static class ManualBuilder extends
		PTManualInvoiceBuilderImpl<ManualBuilder, PTInvoiceEntry, PTInvoice> {
		
		@Inject
		public ManualBuilder(DAOPTInvoice daoPTInvoice, DAOPTBusiness daoPTBusiness,
						DAOPTCustomer daoPTCustomer, DAOPTSupplier daoPTSupplier) {
			super(daoPTInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
		}
	}

	@Override
	public <T extends GenericInvoiceEntry> List<T> getEntries();
	
	@Override
	public <T extends Payment> List<T> getPayments();
	
}
