package com.premiumminds.billy.spain.services.builders;

import java.math.BigDecimal;

import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoiceEntry;

public interface ESManualInvoiceBuilder<TBuilder extends ESManualInvoiceBuilder<TBuilder, TEntry, TDocument>, TEntry extends ESGenericInvoiceEntry, TDocument extends ESGenericInvoice>
	extends ESGenericInvoiceBuilder<TBuilder, TEntry, TDocument> {
	
	public TBuilder setAmount(AmountType type, BigDecimal amount);
	
	public TBuilder setTaxAmount(BigDecimal taxAmount);
}
