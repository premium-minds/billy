package com.premiumminds.billy.portugal.persistence.dao.jpa;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTCreditNoteEntryEntity;

public class DAOPTCreditNoteEntryImpl extends DAOPTInvoiceEntryImpl implements
		DAOPTCreditNoteEntry {

	public DAOPTCreditNoteEntryImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTCreditNoteEntryEntity getEntityInstance() {
		return new JPAPTCreditNoteEntryEntity();
	}

	@Override
	protected Class<JPAPTCreditNoteEntryEntity> getEntityClass() {
		return JPAPTCreditNoteEntryEntity.class;
	}
}
