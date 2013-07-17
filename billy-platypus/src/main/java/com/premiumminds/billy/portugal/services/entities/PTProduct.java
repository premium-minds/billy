package com.premiumminds.billy.portugal.services.entities;

import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.services.builders.impl.PTProductBuilderImpl;


public interface PTProduct extends Product {
	
	public static class Builder extends
	PTProductBuilderImpl<Builder, PTProduct> {

		public Builder(DAOPTProduct daoPTProduct, DAOPTTax daoPTTax) {
			super(daoPTProduct, daoPTTax);
		}	
	}
}
