package com.premiumminds.billy.portugal.services.builders;

import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;

public interface PTInvoiceEntryBuilder<TBuilder extends PTInvoiceEntryBuilder<TBuilder, TEntry>, TEntry extends PTInvoiceEntry>
		extends GenericInvoiceEntryBuilder<TBuilder, TEntry> {

}
