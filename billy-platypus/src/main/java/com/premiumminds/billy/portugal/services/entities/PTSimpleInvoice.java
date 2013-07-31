package com.premiumminds.billy.portugal.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.services.builders.impl.PTInvoiceBuilderImpl;

public interface PTSimpleInvoice extends PTInvoice {

	public static class Builder extends
			PTInvoiceBuilderImpl<Builder, PTInvoiceEntry, PTInvoice> {

		@Inject
		public Builder(DAOPTInvoice daoPTInvoice, DAOPTBusiness daoPTBusiness,
				DAOPTCustomer daoPTCustomer, DAOPTSupplier daoPTSupplier) {
			super(daoPTInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
		}
	}
}
