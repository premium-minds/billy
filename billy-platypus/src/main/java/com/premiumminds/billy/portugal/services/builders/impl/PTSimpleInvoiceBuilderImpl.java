package com.premiumminds.billy.portugal.services.builders.impl;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.services.builders.PTSimpleInvoiceBuilder;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;

public class PTSimpleInvoiceBuilderImpl<TBuilder extends PTSimpleInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTInvoiceEntry, TDocument extends PTSimpleInvoice>
		extends PTInvoiceBuilderImpl<TBuilder, TEntry, TDocument> implements
		PTSimpleInvoiceBuilder<TBuilder, TEntry, TDocument> {

	public PTSimpleInvoiceBuilderImpl(DAOPTInvoice daoPTInvoice,
			DAOPTBusiness daoPTBusiness, DAOPTCustomer daoPTCustomer,
			DAOPTSupplier daoPTSupplier) {
		super(daoPTInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
	}

}
