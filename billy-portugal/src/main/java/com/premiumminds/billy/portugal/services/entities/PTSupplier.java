/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy portugal (PT Pack).
 * 
 * billy portugal (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy portugal (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.services.builders.impl.PTSupplierBuilderImpl;

public interface PTSupplier extends Supplier {

	public static class Builder extends
			PTSupplierBuilderImpl<Builder, PTSupplier> {

		@Inject
		public Builder(DAOPTSupplier daoPTSupplier) {
			super(daoPTSupplier);
		}
	}

	public String getReferralName();
}
