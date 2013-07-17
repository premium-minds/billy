package com.premiumminds.billy.portugal.persistence.dao.jpa;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.DAOTaxImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTTaxEntity;


public class DAOPTTaxImpl extends DAOTaxImpl implements DAOPTTax {

	public DAOPTTaxImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTTaxEntity getEntityInstance() {
		return new JPAPTTaxEntity();
	}

	@Override
	protected Class<JPAPTTaxEntity> getEntityClass() {
		return JPAPTTaxEntity.class;
	}
}
