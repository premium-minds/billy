package com.premiumminds.billy.portugal.services.builders.impl;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.services.builders.impl.AddressBuilderImpl;
import com.premiumminds.billy.portugal.services.builders.PTAddressBuilder;
import com.premiumminds.billy.portugal.services.entities.PTAddress;


public class PTAddressBuilderImpl<TBuilder extends PTAddressBuilderImpl<TBuilder, TAddress>, TAddress extends PTAddress> extends
		AddressBuilderImpl<TBuilder, TAddress> implements
		PTAddressBuilder<TBuilder, TAddress> {

	protected PTAddressBuilderImpl(DAOAddress daoAddress) {
		super(daoAddress);
		// TODO Auto-generated constructor stub
	}

}
