package com.premiumminds.billy.portugal.persistence.entities.jpa;

import com.premiumminds.billy.core.persistence.entities.jpa.JPATaxEntity;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;


public class JPAPTTaxEntity extends JPATaxEntity implements PTTaxEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Context getContext() {
		return super.getContext();
	}

}
