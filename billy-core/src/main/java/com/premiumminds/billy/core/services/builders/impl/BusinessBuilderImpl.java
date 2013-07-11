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
package com.premiumminds.billy.core.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOBusiness;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.BusinessBuilder;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class BusinessBuilderImpl<TBuilder extends BusinessBuilderImpl<TBuilder, TBusiness>, TBusiness extends Business>
		extends AbstractBuilder<TBuilder, TBusiness> implements
		BusinessBuilder<TBuilder, TBusiness> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOBusiness daoBusiness;
	protected DAOContext daoContext;

	@SuppressWarnings("unchecked")
	@Inject
	public BusinessBuilderImpl(DAOBusiness daoBusiness, DAOContext daoContext) {
		super((EntityFactory<? extends TBusiness>) daoBusiness);
		this.daoBusiness = daoBusiness;
		this.daoContext = daoContext;
	}

	@Override
	public TBuilder setOperationalContextUID(UID contextUID) {
		BillyValidator.notNull(contextUID,
				BusinessBuilderImpl.LOCALIZER.getString("field.context"));
		ContextEntity c = BillyValidator.found(this.daoContext.get(contextUID),
				BusinessBuilderImpl.LOCALIZER.getString("field.context"));
		this.getTypeInstance().setOperationalContext(c);
		return this.getBuilder();
	}

	@Override
	public TBuilder setFinancialID(String id) {
		BillyValidator.mandatory(id,
				BusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
		this.getTypeInstance().setFinancialID(id);
		return this.getBuilder();
	}

	@Override
	public TBuilder setName(String name) {
		BillyValidator.mandatory(name,
				BusinessBuilderImpl.LOCALIZER.getString("field.name"));
		this.getTypeInstance().setName(name);
		return this.getBuilder();
	}

	@Override
	public TBuilder setCommercialName(String name) {
		BillyValidator.mandatory(name, BusinessBuilderImpl.LOCALIZER
				.getString("field.commercial_name"));
		this.getTypeInstance().setCommercialName(name);
		return this.getBuilder();
	}

	@Override
	public TBuilder setWebsite(String name) {
		BillyValidator.notBlank(name,
				BusinessBuilderImpl.LOCALIZER.getString("field.website"));
		this.getTypeInstance().setWebsiteAddress(name);
		return this.getBuilder();
	}

	@Override
	public <T extends Address> TBuilder setAddress(Builder<T> addressBuilder) {
		BillyValidator.mandatory(addressBuilder,
				BusinessBuilderImpl.LOCALIZER.getString("field.address"));
		this.getTypeInstance().setAddress(
				(AddressEntity) addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends Address> TBuilder setBillingAddress(
			Builder<T> addressBuilder) {
		BillyValidator.mandatory(addressBuilder, BusinessBuilderImpl.LOCALIZER
				.getString("field.billing_address"));
		this.getTypeInstance().setBillingAddress(
				(AddressEntity) addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends Address> TBuilder setShippingAddress(
			Builder<T> addressBuilder) {
		BillyValidator.notNull(addressBuilder, BusinessBuilderImpl.LOCALIZER
				.getString("field.shipping_address"));
		this.getTypeInstance().setShippingAddress(
				(AddressEntity) addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends Contact> TBuilder addContact(Builder<T> contactBuilder) {
		BillyValidator.notNull(contactBuilder,
				BusinessBuilderImpl.LOCALIZER.getString("field.contact"));
		this.getTypeInstance().getContacts().add(contactBuilder.build());
		return this.getBuilder();
	}

	@Override
	public TBuilder setMainContactUID(UID contactUID) {
		BillyValidator.notNull(contactUID,
				BusinessBuilderImpl.LOCALIZER.getString("field.main_contact"));

		boolean found = false;
		for (Contact c : this.getTypeInstance().getContacts()) {
			if (c.getUID().equals(contactUID)) {
				this.getTypeInstance().setMainContact((ContactEntity) c);
				found = true;
				break;
			}
		}
		if (!found) {
			BillyValidator.found(null, BusinessBuilderImpl.LOCALIZER
					.getString("field.main_contact"));
		}
		return this.getBuilder();
	}

	@Override
	public <T extends Application> TBuilder addApplication(
			Builder<T> applicationBuilder) {
		BillyValidator.notNull(applicationBuilder,
				BusinessBuilderImpl.LOCALIZER.getString("field.application"));
		this.getTypeInstance().getApplications()
				.add(applicationBuilder.build());
		return this.getBuilder();
	}

	@Override
	protected void validateInstance()
			throws javax.validation.ValidationException {
		BusinessEntity b = this.getTypeInstance();
		BillyValidator.mandatory(b.getOperationalContext(), "field.context");
		BillyValidator.mandatory(b.getFinancialID(), "field.financial_id");
		BillyValidator.mandatory(b.getName(), "field.name");
		BillyValidator
				.mandatory(b.getCommercialName(), "field.commercial_name");
		BillyValidator.mandatory(b.getAddress(), "field.address");
		BillyValidator
				.mandatory(b.getBillingAddress(), "field.billing_address");
		BillyValidator.notEmpty(b.getContacts(), "field.contacts");
		BillyValidator.notEmpty(b.getApplications(), "field.applications");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BusinessEntity getTypeInstance() {
		return (BusinessEntity) super.getTypeInstance();
	}

}
