package com.premiumminds.billy.portugal.persistence.dao;

import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;

public interface DAOPTCreditNoteEntry extends DAOPTInvoiceEntry {

	@Override
	public PTCreditNoteEntryEntity getEntityInstance();
}
