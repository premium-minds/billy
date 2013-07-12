/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.fixtures;

import java.util.Date;

import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ShippingPointEntity;
import com.premiumminds.billy.core.services.entities.Address;

public class MockShippingPointEntity extends MockBaseEntity implements
		ShippingPointEntity {
	private static final long serialVersionUID = 1L;
	
	public String deliveryId;
	public Date date;
	public String warehouseId;
	public String locationId;
	public String UCR;
	public AddressEntity address;
	
	public MockShippingPointEntity() {
		
	}

	@Override
	public String getDeliveryId() {
		return deliveryId;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public String getWarehouseId() {
		return warehouseId;
	}

	@Override
	public String getLocationId() {
		return locationId;
	}

	@Override
	public String getUCR() {
		return UCR;
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void setWarehouseId(String id) {
		this.warehouseId = id;
	}

	@Override
	public void setLocationId(String id) {
		this.locationId = id;
	}

	@Override
	public void setUCR(String UCR) {
		this.UCR = UCR;
	}

	@Override
	public <T extends AddressEntity> void setAddress(T address) {
		this.address = address;
	}

}
