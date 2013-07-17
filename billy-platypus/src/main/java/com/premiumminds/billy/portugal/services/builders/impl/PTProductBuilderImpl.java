package com.premiumminds.billy.portugal.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.ProductBuilderImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.builders.PTProductBuilder;
import com.premiumminds.billy.portugal.services.entities.PTProduct;


public class PTProductBuilderImpl<TBuilder extends PTProductBuilderImpl<TBuilder, TProduct>, TProduct extends PTProduct> extends ProductBuilderImpl<TBuilder, TProduct> implements PTProductBuilder<TBuilder, TProduct>{

	public PTProductBuilderImpl(DAOPTProduct daoPTProduct, DAOPTTax daoPTTax) {
		super(daoPTProduct, daoPTTax);
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
