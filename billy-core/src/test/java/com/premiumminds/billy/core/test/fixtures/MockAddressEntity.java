package com.premiumminds.billy.core.test.fixtures;

import com.premiumminds.billy.core.persistence.entities.AddressEntity;

public class MockAddressEntity extends MockBaseEntity implements AddressEntity {
	private static final long serialVersionUID = 1L;
	
	public String streetName;
	public String number;
	public String details;
	public String building;
	public String city;
	public String postalCode;
	public String region;
	public String country;
	
	public MockAddressEntity() {
		
	}

	@Override
	public String getStreetName() {
		return streetName;
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public String getDetails() {
		return details;
	}

	@Override
	public String getBuilding() {
		return building;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public String getPostalCode() {
		return postalCode;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getISOCountry() {
		return country;
	}

	@Override
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	@Override
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public void setBuilding(String building) {
		this.building = building;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public void setISOCountry(String country) {
		this.country = country;
	}

}
