package com.premiumminds.billy.portugal.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.services.builders.impl.PTInvoiceEntryBuilderImpl;

public interface PTInvoiceEntry extends GenericInvoiceEntry {

	public static class Builder extends
			PTInvoiceEntryBuilderImpl<Builder, PTInvoiceEntry> {

		@Inject
		public Builder(DAOPTInvoiceEntry daoPTEntry, DAOPTInvoice daoPTInvoice,
				DAOPTTax daoPTTax, DAOPTProduct daoPTProduct,
				DAOPTRegionContext daoPTRegionContext) {
			super(daoPTEntry, daoPTInvoice, daoPTTax, daoPTProduct,
					daoPTRegionContext);
		}
	}
}
