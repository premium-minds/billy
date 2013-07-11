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
package com.premiumminds.billy.core.persistence.entities;

import java.util.List;

import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Contact;

/**
 * @author Francisco Vargas
 * 
 *         The definition of a Billy persistence business entity. A business is
 *         an owner of financial documents.
 */
public interface BusinessEntity extends Business, BaseEntity {

	public <T extends ContextEntity> void setOperationalContext(T context);

	public void setFinancialID(String id);

	public void setName(String name);

	public void setCommercialName(String name);

	public <T extends AddressEntity> void setAddress(T address);

	public <T extends AddressEntity> void setBillingAddress(T address);

	public <T extends AddressEntity> void setShippingAddress(T address);

	@Override
	public <T extends Contact> List<T> getContacts();

	public <T extends ContactEntity> void setMainContact(T contact);

	@Override
	public <T extends Application> List<T> getApplications();

}
