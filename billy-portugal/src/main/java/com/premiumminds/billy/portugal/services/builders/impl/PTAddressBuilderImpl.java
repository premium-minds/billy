/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.AddressBuilderImpl;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.services.builders.PTAddressBuilder;
import com.premiumminds.billy.portugal.services.entities.PTAddress;

public class PTAddressBuilderImpl<TBuilder extends PTAddressBuilderImpl<TBuilder, TAddress>, TAddress extends PTAddress>
		extends AddressBuilderImpl<TBuilder, TAddress> implements
		PTAddressBuilder<TBuilder, TAddress> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/portugal/i18n/FieldNames_pt");

	@Inject
	protected PTAddressBuilderImpl(DAOPTAddress daoPTAddress) {
		super(daoPTAddress);
	}

	@Override
	public TBuilder setNumber(String number) {
		this.getTypeInstance().setNumber(number);
		return this.getBuilder();
	}

	@Override
	public TBuilder setStreetName(String streetName) {
		this.getTypeInstance().setStreetName(streetName);
		return this.getBuilder();
	}

	@Override
	public TBuilder setRegion(String region) {
		this.getTypeInstance().setRegion(region);
		return this.getBuilder();
	}

	@Override
	public TBuilder setBuilding(String building) {
		this.getTypeInstance().setBuilding(building);
		return this.getBuilder();
	}

	@Override
	protected PTAddressEntity getTypeInstance() {
		return (PTAddressEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
	}
}
