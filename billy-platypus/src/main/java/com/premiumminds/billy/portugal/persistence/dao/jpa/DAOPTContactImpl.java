package com.premiumminds.billy.portugal.persistence.dao.jpa;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.DAOContactImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;


public class DAOPTContactImpl extends DAOContactImpl implements DAOPTContact {

	public DAOPTContactImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

}
