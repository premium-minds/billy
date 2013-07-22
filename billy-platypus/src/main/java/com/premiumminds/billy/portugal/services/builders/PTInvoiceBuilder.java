package com.premiumminds.billy.portugal.services.builders;

import com.premiumminds.billy.core.services.builders.GenericInvoiceBuilder;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;

public interface PTInvoiceBuilder<TBuilder extends PTInvoiceBuilder<TBuilder, TEntry, TDocument>, TEntry extends PTInvoiceEntry, TDocument extends PTInvoice>
		extends GenericInvoiceBuilder<TBuilder, TEntry, TDocument> {

}
