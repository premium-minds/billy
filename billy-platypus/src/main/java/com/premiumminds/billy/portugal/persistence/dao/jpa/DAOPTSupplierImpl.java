package com.premiumminds.billy.portugal.persistence.dao.jpa;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.DAOSupplierImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTSupplierEntity;

public class DAOPTSupplierImpl extends DAOSupplierImpl implements DAOPTSupplier {

	public DAOPTSupplierImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTSupplierEntity getEntityInstance() {
		return new JPAPTSupplierEntity();
	}

	@Override
	protected Class<JPAPTSupplierEntity> getEntityClass() {
		return JPAPTSupplierEntity.class;
	}
}
