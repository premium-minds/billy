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
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Customer;

public interface CustomerBuilder<TBuilder extends CustomerBuilder<TBuilder, TCustomer>, TCustomer extends Customer>
	extends Builder<TCustomer> {

	public TBuilder setName(String name);

	public TBuilder setTaxRegistrationNumber(String number, String countryCode);

	public <T extends Address> TBuilder addAddress(Builder<T> addressBuilder,
			boolean mainAddress);

	public <T extends Address> TBuilder setBillingAddress(
			Builder<T> addressBuilder);

	public <T extends Address> TBuilder setShippingAddress(
			Builder<T> addressBuilder);

	public <T extends Contact> TBuilder addContact(Builder<T> contactBuilder);

	public TBuilder setMainContactUID(UID contactUID);

	public <T extends BankAccount> TBuilder addBankAccount(
			Builder<T> accountBuilder);

	public TBuilder setHasSelfBillingAgreement(boolean selfBiling);

}
