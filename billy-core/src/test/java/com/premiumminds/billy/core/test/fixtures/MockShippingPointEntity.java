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
