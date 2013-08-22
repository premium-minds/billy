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
package com.premiumminds.billy.portugal.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.services.builders.impl.PTReceiptInvoiceBuilderImpl;

public interface PTReceiptInvoice extends PTInvoice {

	public static class Builder
			extends
			PTReceiptInvoiceBuilderImpl<Builder, PTInvoiceEntry, PTReceiptInvoice> {

		@Inject
		public Builder(DAOPTReceiptInvoice daoPTReceiptInvoice, DAOPTBusiness daoPTBusiness,
				DAOPTCustomer daoPTCustomer, DAOPTSupplier daoPTSupplier) {
			super(daoPTReceiptInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
		}
	}
}
