package com.premiumminds.billy.portugal.persistence.dao.jpa;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTCreditNoteEntity;

public class DAOPTCreditNoteImpl extends DAOPTInvoiceImpl implements
		DAOPTCreditNote {

	public DAOPTCreditNoteImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTCreditNoteEntity getEntityInstance() {
		return new JPAPTCreditNoteEntity();
	}

	@Override
	protected Class<JPAPTCreditNoteEntity> getEntityClass() {
		return JPAPTCreditNoteEntity.class;
	}

}
