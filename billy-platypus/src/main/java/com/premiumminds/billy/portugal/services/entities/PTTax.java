package com.premiumminds.billy.portugal.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.services.builders.impl.PTTaxBuilderImpl;

public interface PTTax extends Tax {

	public static class Builder extends PTTaxBuilderImpl<Builder, PTTax> {

		@Inject
		public Builder(DAOPTTax daoPTTax, DAOPTRegionContext daoPTRegionContext) {
			super(daoPTTax, daoPTRegionContext);
		}

	}

}
