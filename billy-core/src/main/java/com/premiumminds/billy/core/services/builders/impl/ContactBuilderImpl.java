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
package com.premiumminds.billy.core.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.builders.ContactBuilder;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class ContactBuilderImpl<TBuilder extends ContactBuilderImpl<TBuilder, TContact>, TContact extends Contact>
	extends AbstractBuilder<TBuilder, TContact> implements
	ContactBuilder<TBuilder, TContact> {

	protected static final Localizer	LOCALIZER	= new Localizer(
															"com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOContact				daoContact;

	@Inject
	public ContactBuilderImpl(DAOContact daoContact) {
		super(daoContact);
		this.daoContact = daoContact;
	}

	@Override
	public TBuilder setName(String name) {
		BillyValidator.mandatory(name,
				ContactBuilderImpl.LOCALIZER.getString("field.contact_name"));
		this.getTypeInstance().setName(name);
		return this.getBuilder();
	}

	@Override
	public TBuilder setTelephone(String telephone) {
		BillyValidator.notBlank(telephone,
				ContactBuilderImpl.LOCALIZER.getString("field.telephone"));
		this.getTypeInstance().setTelephone(telephone);
		return this.getBuilder();
	}

	@Override
	public TBuilder setMobile(String mobile) {
		BillyValidator.notBlank(mobile,
				ContactBuilderImpl.LOCALIZER.getString("field.mobile"));
		this.getTypeInstance().setMobile(mobile);
		return this.getBuilder();
	}

	@Override
	public TBuilder setFax(String fax) {
		BillyValidator.notBlank(fax,
				ContactBuilderImpl.LOCALIZER.getString("field.fax"));
		this.getTypeInstance().setFax(fax);
		return this.getBuilder();
	}

	@Override
	public TBuilder setEmail(String email) {
		BillyValidator.notBlank(email,
				ContactBuilderImpl.LOCALIZER.getString("field.email"));
		this.getTypeInstance().setEmail(email);
		return this.getBuilder();
	}

	@Override
	public TBuilder setWebsite(String website) {
		BillyValidator.notBlank(website,
				ContactBuilderImpl.LOCALIZER.getString("field.website"));
		this.getTypeInstance().setWebsite(website);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance()
		throws javax.validation.ValidationException {
		Contact c = this.getTypeInstance();
		BillyValidator.mandatory(c.getName(), "field.contact_name");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ContactEntity getTypeInstance() {
		return (ContactEntity) super.getTypeInstance();
	}

}
