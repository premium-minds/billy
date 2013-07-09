/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.services.builders.impl;


import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.services.builders.AddressBuilder;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class AddressBuilderImpl<TBuilder extends AddressBuilderImpl<TBuilder, TAddress>, TAddress extends Address>
extends AbstractBuilder<TBuilder, TAddress>
implements AddressBuilder<TBuilder, TAddress> {
	
	protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOAddress daoAddress;
	protected AddressEntity address;
	
	@SuppressWarnings("unchecked")
	@Inject
	protected AddressBuilderImpl(
			DAOAddress daoAddress) {
		super((EntityFactory<? extends TAddress>) daoAddress);
		this.daoAddress = daoAddress;
	}
	
	@Override
	public TBuilder setStreetName(String streetName) {
		BillyValidator.notNull(streetName, LOCALIZER.getString("field.street_name"));
		getTypeInstance().setStreetName(streetName);
		return getBuilder();
	}
	
	@Override
	public TBuilder setNumber(String number) {
		BillyValidator.notNull(number, LOCALIZER.getString("field.number"));
		getTypeInstance().setNumber(number);
		return getBuilder();
	}

	@Override
	public TBuilder setDetails(String details) {
		BillyValidator.mandatory(details, LOCALIZER.getString("field.details"));
		getTypeInstance().setDetails(details);
		return getBuilder();
	}

	@Override
	public TBuilder setBuilding(String building) {
		BillyValidator.notNull(building, LOCALIZER.getString("field.building"));
		getTypeInstance().setBuilding(building);
		return getBuilder();
	}

	@Override
	public TBuilder setCity(String city) {
		BillyValidator.mandatory(city, LOCALIZER.getString("field.city"));
		getTypeInstance().setCity(city);
		return getBuilder();
	}

	@Override
	public TBuilder setPostalCode(String postalCode) {
		BillyValidator.mandatory(postalCode, LOCALIZER.getString("field.postal_code"));
		getTypeInstance().setPostalCode(postalCode);
		return getBuilder();
	}

	@Override
	public TBuilder setRegion(String region) {
		BillyValidator.notNull(region, LOCALIZER.getString("field.region"));
		getTypeInstance().setRegion(region);
		return getBuilder();
	}

	@Override
	public TBuilder setISOCountry(String country) {
		BillyValidator.mandatory(country, LOCALIZER.getString("field.country"));
		getTypeInstance().setISOCountry(country);
		return getBuilder();
	}

	@Override
	protected void validateInstance()
			throws BillyValidationException {
		AddressEntity address = getTypeInstance();
		BillyValidator.mandatory(address.getDetails(), LOCALIZER.getString("field.details"));
		BillyValidator.mandatory(address.getCity(), LOCALIZER.getString("field.city"));
		BillyValidator.mandatory(address.getPostalCode(), LOCALIZER.getString("field.postal_code"));
		BillyValidator.mandatory(address.getISOCountry(), LOCALIZER.getString("field.country"));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected AddressEntity getTypeInstance() {
		return (AddressEntity) super.getTypeInstance();
	}

}
