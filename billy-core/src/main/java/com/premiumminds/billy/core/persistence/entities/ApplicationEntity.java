/**
 * Copyright (C) 2017 Premium Minds.
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

import java.util.List;

import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Contact;

/**
 * @author Francisco Vargas
 * 
 *         The definition of a Billy persistence application entity. An
 *         application is the piece of software that uses Billy. This is needed
 *         to identity the origin of the billing data.
 */
public interface ApplicationEntity extends Application, BaseEntity {

	public void setName(String name);

	public void setVersion(String version);

	public void setDeveloperCompanyName(String name);

	public void setDeveloperCompanyTaxIdentifier(String id);

	@Override
	public <T extends Contact> List<T> getContacts();

	public <T extends ContactEntity> void setMainContact(T contact);

	public void setWebsiteAddress(String website);

}
