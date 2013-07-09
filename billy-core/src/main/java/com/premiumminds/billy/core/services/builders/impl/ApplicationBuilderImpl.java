/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.builders.ApplicationBuilder;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class ApplicationBuilderImpl<TBuilder extends ApplicationBuilderImpl<TBuilder, TApplication>, TApplication extends Application>
extends AbstractBuilder<TBuilder, TApplication>
implements ApplicationBuilder<TBuilder, TApplication> {

	protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");
	
	protected DAOApplication daoApplication;
	
	@SuppressWarnings("unchecked")
	@Inject
	public ApplicationBuilderImpl(
			DAOApplication daoApplication) {
		super((EntityFactory<? extends TApplication>) daoApplication);
		this.daoApplication = daoApplication;
	}


	@Override
	public TBuilder setName(String name) {
		BillyValidator.mandatory(name, LOCALIZER.getString("field.name"));
		getTypeInstance().setName(name);
		return getBuilder();
	}


	@Override
	public TBuilder setVersion(String version) {
		BillyValidator.mandatory(version, LOCALIZER.getString("field.version"));
		getTypeInstance().setVersion(version);
		return getBuilder();
	}


	@Override
	public TBuilder setDeveloperCompanyName(String name) {
		BillyValidator.mandatory(name, LOCALIZER.getString("field.developer_name"));
		getTypeInstance().setDeveloperCompanyName(name);
		return getBuilder();
	}


	@Override
	public TBuilder setDeveloperCompanyTaxIdentifier(String id) {
		BillyValidator.mandatory(id, LOCALIZER.getString("field.developer_tax_id"));
		getTypeInstance().setDeveloperCompanyTaxIdentifier(id);
		return getBuilder();
	}


	@Override
	public <T extends Contact> TBuilder addContact(Builder<T> contactBuilder) {
		BillyValidator.notNull(contactBuilder, LOCALIZER.getString("field.contact"));
		ContactEntity contact = (ContactEntity) contactBuilder.build();
		getTypeInstance().getContacts().add(contact);
		if(getTypeInstance().getContacts().size() == 1) {
			setMainContact(contactBuilder);
		}
		return getBuilder();
	}


	@Override
	public <T extends Contact> TBuilder setMainContact(Builder<T> contactBuilder) {
		BillyValidator.notNull(contactBuilder, LOCALIZER.getString("field.main_contact"));
		getTypeInstance().setMainContact((ContactEntity) contactBuilder.build());
		return getBuilder();
	}
	
	
	@Override
	public TBuilder setWebsiteAddress(String website) {
		getTypeInstance().setWebsiteAddress(website);
		return getBuilder();
	}


	@Override
	protected void validateInstance()
			throws javax.validation.ValidationException {
		ApplicationEntity application = getTypeInstance();
		BillyValidator.mandatory(application.getName(), LOCALIZER.getString("field.name"));
		BillyValidator.mandatory(application.getVersion(), LOCALIZER.getString("field.version"));
		BillyValidator.mandatory(application.getDeveloperCompanyName(), LOCALIZER.getString("field.developer_name"));
		BillyValidator.mandatory(application.getDeveloperCompanyTaxIdentifier(), LOCALIZER.getString("field.developer_tax_id"));
		BillyValidator.notEmpty(application.getContacts(), LOCALIZER.getString("field.contacts"));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ApplicationEntity getTypeInstance() {
		return (ApplicationEntity) super.getTypeInstance();
	}
	
}
