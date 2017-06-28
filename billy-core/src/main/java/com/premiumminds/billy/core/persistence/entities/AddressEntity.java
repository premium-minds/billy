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
package com.premiumminds.billy.core.persistence.entities;

import com.premiumminds.billy.core.services.entities.Address;

/**
 * @author Francisco Vargas
 *
 *         The definition of a Billy persistence Address entity
 */
public interface AddressEntity extends Address, BaseEntity {

	public void setStreetName(String streetName);

	public void setNumber(String number);

	public void setDetails(String details);

	public void setBuilding(String building);

	public void setCity(String city);

	public void setPostalCode(String postalCode);

	/**
	 * Gets the ISO 3166-2 code for the country region
	 *
	 * @param region The region ISO code
	 */
	public void setRegion(String region);

	/**
	 * Gets the address country ISO 3166-1 code.
	 *
	 * @param country The country iso code.
	 */
	public void setISOCountry(String country);

}
