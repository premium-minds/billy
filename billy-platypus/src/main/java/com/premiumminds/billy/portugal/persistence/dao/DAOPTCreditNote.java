package com.premiumminds.billy.portugal.persistence.dao;

import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;

public interface DAOPTCreditNote extends DAOPTInvoice {

	@Override
	public PTCreditNoteEntity getEntityInstance();
}
