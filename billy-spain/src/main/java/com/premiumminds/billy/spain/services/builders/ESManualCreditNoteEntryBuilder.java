package com.premiumminds.billy.spain.services.builders;

import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;

public interface ESManualCreditNoteEntryBuilder<TBuilder extends ESManualCreditNoteEntryBuilder<TBuilder, TEntry>, TEntry extends ESCreditNoteEntry>
	extends ESManualInvoiceEntryBuilder<TBuilder, TEntry>,  ESCreditNoteEntryBuilder<TBuilder, TEntry>{

}
