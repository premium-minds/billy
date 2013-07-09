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

import java.util.Date;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.dao.DAOShippingPoint;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ShippingPointEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.builders.ShippingPointBuilder;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.Localizer;

public class ShippingPointBuilderImpl<TBuilder extends ShippingPointBuilderImpl<TBuilder, TShippingPoint>, TShippingPoint extends ShippingPoint>
extends AbstractBuilder<TBuilder, TShippingPoint>
implements ShippingPointBuilder<TBuilder, TShippingPoint> {

	protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOShippingPoint daoShippingPoint;

	
	@SuppressWarnings("unchecked")
	@Inject
	public ShippingPointBuilderImpl(
			DAOShippingPoint daoShippingPoint) {
		super((EntityFactory<? extends TShippingPoint>) daoShippingPoint);
	}

	@Override
	public TBuilder setDeliveryId(String deliveryId) {
		getTypeInstance().setDeliveryId(deliveryId);
		return getBuilder();
	}


	@Override
	public TBuilder setDate(Date date) {
		getTypeInstance().setDate(date);
		return getBuilder();
	}


	@Override
	public TBuilder setWarehouseId(String id) {
		getTypeInstance().setWarehouseId(id);
		return getBuilder();
	}


	@Override
	public TBuilder setLocationId(String id) {
		getTypeInstance().setLocationId(id);
		return getBuilder();
	}


	@Override
	public TBuilder setUCR(String UCR) {
		getTypeInstance().setUCR(UCR);
		return getBuilder();
	}


	@Override
	public <T extends AddressEntity> TBuilder setAddress(Builder<T> address) {
		getTypeInstance().setAddress(address.build());
		return getBuilder();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ShippingPointEntity getTypeInstance() {
		return (ShippingPointEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		return;
	}

}
