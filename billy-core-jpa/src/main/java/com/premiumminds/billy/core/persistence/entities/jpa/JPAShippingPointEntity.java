/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core JPA.
 *
 * billy core JPA is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core JPA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.entities.jpa;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ShippingPointEntity;
import com.premiumminds.billy.core.services.entities.Address;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "SHIPPING_POINT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JPAShippingPointEntity extends JPABaseEntity implements
		ShippingPointEntity {

	private static final long serialVersionUID = 1L;

	@OneToOne(targetEntity = JPAAddressEntity.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ID_ADDRESS", referencedColumnName = "ID")
	protected Address address;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE")
	protected Date date;

	@Column(name = "DELIVERY_ID")
	protected String deliveryId;

	@Column(name = "LOCATION_ID")
	protected String locationId;

	@Column(name = "UCR")
	protected String ucr;

	@Column(name = "WAREHOUSE_ID")
	protected String warehouseId;

	public JPAShippingPointEntity() {
	}

	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public String getWarehouseId() {
		return this.warehouseId;
	}

	@Override
	public String getLocationId() {
		return this.locationId;
	}

	@Override
	public String getUCR() {
		return this.ucr;
	}

	@Override
	public Address getAddress() {
		return this.address;
	}

	@Override
	public String getDeliveryId() {
		return this.deliveryId;
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
		this.ucr = UCR;
	}

	@Override
	public <T extends AddressEntity> void setAddress(T address) {
		this.address = address;
	}

	@Override
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

}
