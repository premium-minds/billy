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
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Contact;

public interface BusinessBuilder<TBuilder extends BusinessBuilder<TBuilder, TBusiness>, TBusiness extends Business>
	extends Builder<TBusiness> {

	public TBuilder setOperationalContextUID(UID contextUID);

	public TBuilder setFinancialID(String id, String countyCode);

	public TBuilder setName(String name);

	public TBuilder setCommercialName(String name);

	public TBuilder setWebsite(String website);

	public <T extends Address> TBuilder setAddress(Builder<T> addressBuilder);

	public <T extends Address> TBuilder setBillingAddress(
			Builder<T> addressBuilder);

	public <T extends Address> TBuilder setShippingAddress(
			Builder<T> addressBuilder);

	public <T extends Contact> TBuilder addContact(Builder<T> contactBuilder,
			boolean isMainContact);

	public TBuilder setMainContactUID(UID contactUID);

	public <T extends Application> TBuilder addApplication(
			Builder<T> applicationBuilder);

}
