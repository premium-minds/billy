package com.premiumminds.billy.portugal.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.services.builders.impl.ProductBuilderImpl;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.builders.PTProductBuilder;
import com.premiumminds.billy.portugal.services.entities.PTProduct;


public class PTProductBuilderImpl<TBuilder extends PTProductBuilderImpl<TBuilder, TProduct>, TProduct extends PTProduct> extends ProductBuilderImpl<TBuilder, TProduct> implements PTProductBuilder<TBuilder, TProduct>{

	public PTProductBuilderImpl(DAOProduct daoProduct, DAOTax daoTax) {
		super(daoProduct, daoTax);
	}
	
	@Override
	protected PTProductEntity getTypeInstance() {
		return (PTProductEntity) super.getTypeInstance();
	}
	
	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
	}

}
