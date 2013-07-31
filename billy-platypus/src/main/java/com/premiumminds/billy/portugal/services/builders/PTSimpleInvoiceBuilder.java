package com.premiumminds.billy.portugal.services.builders;

import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;

public interface PTSimpleInvoiceBuilder<TBuilder extends PTSimpleInvoiceBuilder<TBuilder, TEntry, TDocument>, TEntry extends PTInvoiceEntry, TDocument extends PTSimpleInvoice>
		extends PTInvoiceBuilder<TBuilder, TEntry, TDocument> {

}
