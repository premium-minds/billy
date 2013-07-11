/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * billy-core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core. If not, see <http://www.gnu.org/licenses/>.
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
		extends AbstractBuilder<TBuilder, TAddress> implements
		AddressBuilder<TBuilder, TAddress> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOAddress daoAddress;
	protected AddressEntity address;

	@SuppressWarnings("unchecked")
	@Inject
	protected AddressBuilderImpl(DAOAddress daoAddress) {
		super((EntityFactory<? extends TAddress>) daoAddress);
		this.daoAddress = daoAddress;
	}

	@Override
	public TBuilder setStreetName(String streetName) {
		BillyValidator.notNull(streetName,
				AddressBuilderImpl.LOCALIZER.getString("field.street_name"));
		this.getTypeInstance().setStreetName(streetName);
		return this.getBuilder();
	}

	@Override
	public TBuilder setNumber(String number) {
		BillyValidator.notNull(number,
				AddressBuilderImpl.LOCALIZER.getString("field.number"));
		this.getTypeInstance().setNumber(number);
		return this.getBuilder();
	}

	@Override
	public TBuilder setDetails(String details) {
		BillyValidator.mandatory(details,
				AddressBuilderImpl.LOCALIZER.getString("field.details"));
		this.getTypeInstance().setDetails(details);
		return this.getBuilder();
	}

	@Override
	public TBuilder setBuilding(String building) {
		BillyValidator.notNull(building,
				AddressBuilderImpl.LOCALIZER.getString("field.building"));
		this.getTypeInstance().setBuilding(building);
		return this.getBuilder();
	}

	@Override
	public TBuilder setCity(String city) {
		BillyValidator.mandatory(city,
				AddressBuilderImpl.LOCALIZER.getString("field.city"));
		this.getTypeInstance().setCity(city);
		return this.getBuilder();
	}

	@Override
	public TBuilder setPostalCode(String postalCode) {
		BillyValidator.mandatory(postalCode,
				AddressBuilderImpl.LOCALIZER.getString("field.postal_code"));
		this.getTypeInstance().setPostalCode(postalCode);
		return this.getBuilder();
	}

	@Override
	public TBuilder setRegion(String region) {
		BillyValidator.notNull(region,
				AddressBuilderImpl.LOCALIZER.getString("field.region"));
		this.getTypeInstance().setRegion(region);
		return this.getBuilder();
	}

	@Override
	public TBuilder setISOCountry(String country) {
		BillyValidator.mandatory(country,
				AddressBuilderImpl.LOCALIZER.getString("field.country"));
		this.getTypeInstance().setISOCountry(country);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		AddressEntity address = this.getTypeInstance();
		BillyValidator.mandatory(address.getDetails(),
				AddressBuilderImpl.LOCALIZER.getString("field.details"));
		BillyValidator.mandatory(address.getCity(),
				AddressBuilderImpl.LOCALIZER.getString("field.city"));
		BillyValidator.mandatory(address.getPostalCode(),
				AddressBuilderImpl.LOCALIZER.getString("field.postal_code"));
		BillyValidator.mandatory(address.getISOCountry(),
				AddressBuilderImpl.LOCALIZER.getString("field.country"));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected AddressEntity getTypeInstance() {
		return (AddressEntity) super.getTypeInstance();
	}

}
