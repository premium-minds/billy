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
package com.premiumminds.billy.core.services.builders;

import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.entities.Address;

public interface AddressBuilder<TBuilder extends AddressBuilder<TBuilder, TAddress>, TAddress extends Address>
		extends Builder<TAddress> {

	public TBuilder setStreetName(String streetName);

	public TBuilder setNumber(String number);

	public TBuilder setDetails(String details);

	public TBuilder setBuilding(String building);

	public TBuilder setCity(String city);

	public TBuilder setPostalCode(String postalCode);

	/**
	 * Gets the ISO 3166-2 code for the country region
	 * 
	 * @return The region ISO code
	 */
	public TBuilder setRegion(String region);

	/**
	 * Gets the address country ISO 3166-1 code.
	 * 
	 * @return The country iso code.
	 */
	public TBuilder setISOCountry(String country);
}
