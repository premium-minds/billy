package com.premiumminds.billy.portugal.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.services.builders.impl.ContactBuilderImpl;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.services.builders.PTContactBuilder;
import com.premiumminds.billy.portugal.services.entities.PTContact;


public class PTContactBuilderImpl<TBuilder extends PTContactBuilderImpl<TBuilder, TContact>, TContact extends PTContact>
extends ContactBuilderImpl<TBuilder, TContact> implements
PTContactBuilder<TBuilder, TContact> {
	
	@Inject
	public PTContactBuilderImpl(DAOContact daoContact) {
		super(daoContact);
	}

	@Override
	protected PTContactEntity getTypeInstance() {
		return (PTContactEntity) super.getTypeInstance();
	}
}
