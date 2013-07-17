package com.premiumminds.billy.portugal.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.TaxBuilderImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.builders.PTTaxBuilder;
import com.premiumminds.billy.portugal.services.entities.PTTax;

public class PTTaxBuilderImpl<TBuilder extends PTTaxBuilderImpl<TBuilder, TTax>, TTax extends PTTax>
		extends TaxBuilderImpl<TBuilder, TTax> implements
		PTTaxBuilder<TBuilder, TTax> {

	public PTTaxBuilderImpl(DAOPTTax daoPTTax, DAOPTRegionContext daoPTContext) {
		super(daoPTTax, daoPTContext);
	}

	@Override
	protected PTTaxEntity getTypeInstance() {
		return (PTTaxEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
	}

}
