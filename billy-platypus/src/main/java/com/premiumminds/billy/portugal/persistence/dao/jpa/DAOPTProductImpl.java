package com.premiumminds.billy.portugal.persistence.dao.jpa;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.DAOProductImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTProductEntity;


public class DAOPTProductImpl extends DAOProductImpl implements DAOPTProduct {

	public DAOPTProductImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}
	
	@Override
	public PTProductEntity getEntityInstance() {
		return new JPAPTProductEntity();
	}

	@Override
	protected Class<JPAPTProductEntity> getEntityClass() {
		return JPAPTProductEntity.class;
	}

}
