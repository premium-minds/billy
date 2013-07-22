package com.premiumminds.billy.portugal.persistence.dao.jpa;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.DAOGenericInvoiceEntryImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTInvoiceEntryEntity;

public class DAOPTInvoiceEntryImpl extends DAOGenericInvoiceEntryImpl implements
		DAOPTInvoiceEntry {

	public DAOPTInvoiceEntryImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTInvoiceEntryEntity getEntityInstance() {
		return new JPAPTInvoiceEntryEntity();
	}

	@Override
	protected Class<? extends JPAPTInvoiceEntryEntity> getEntityClass() {
		return JPAPTInvoiceEntryEntity.class;
	}

}
