package com.premiumminds.billy.portugal.services.entities;

import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.portugal.services.builders.impl.PTProductBuilderImpl;


public interface PTProduct extends Product {
	
	public static class Builder extends
	PTProductBuilderImpl<Builder, PTProduct> {

		public Builder(DAOProduct daoProduct, DAOTax daoTax) {
			super(daoProduct, daoTax);
		}	
	}
}
