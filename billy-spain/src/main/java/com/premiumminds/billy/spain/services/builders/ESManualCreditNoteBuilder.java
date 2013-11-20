package com.premiumminds.billy.spain.services.builders;

import com.premiumminds.billy.spain.services.entities.ESCreditNote;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;

public interface ESManualCreditNoteBuilder<TBuilder extends ESManualCreditNoteBuilder<TBuilder, TEntry, TDocument>, TEntry extends ESCreditNoteEntry, TDocument extends ESCreditNote>
	extends ESManualInvoiceBuilder<TBuilder, TEntry, TDocument> {

}
