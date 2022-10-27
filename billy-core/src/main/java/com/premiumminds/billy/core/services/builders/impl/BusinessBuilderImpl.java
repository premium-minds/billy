/*
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
package com.premiumminds.billy.core.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
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
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;

public class BusinessBuilderImpl<TBuilder extends BusinessBuilderImpl<TBuilder, TBusiness>, TBusiness extends Business>
        extends AbstractBuilder<TBuilder, TBusiness> implements BusinessBuilder<TBuilder, TBusiness> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    protected DAOBusiness daoBusiness;
    protected DAOContext daoContext;

    @Inject
    public BusinessBuilderImpl(DAOBusiness daoBusiness, DAOContext daoContext) {
        super(daoBusiness);
        this.daoBusiness = daoBusiness;
        this.daoContext = daoContext;
    }

    @Override
    public TBuilder setOperationalContextUID(UID contextUID) {
        BillyValidator.notNull(contextUID, BusinessBuilderImpl.LOCALIZER.getString("field.business_context"));
        ContextEntity c = BillyValidator.found(this.daoContext.get(contextUID),
                BusinessBuilderImpl.LOCALIZER.getString("field.business_context"));
        this.getTypeInstance().setOperationalContext(c);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setFinancialID(String id, String countryCode) throws BillyUpdateException {
        BillyValidator.notBlank(id, BusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
        this.getTypeInstance().setFinancialID(id);
		this.getTypeInstance().setFinancialIDCountry(countryCode);
        return this.getBuilder();
    }

    @Override
    public TBuilder setName(String name) {
        BillyValidator.notBlank(name, BusinessBuilderImpl.LOCALIZER.getString("field.business_name"));
        this.getTypeInstance().setName(name);
        return this.getBuilder();
    }

    @Override
    public TBuilder setCommercialName(String name) {
        BillyValidator.notBlank(name, BusinessBuilderImpl.LOCALIZER.getString("field.commercial_name"));
        this.getTypeInstance().setCommercialName(name);
        return this.getBuilder();
    }

    @Override
    public TBuilder setWebsite(String website) {
        BillyValidator.notBlank(website, BusinessBuilderImpl.LOCALIZER.getString("field.business_website"));
        this.getTypeInstance().setWebsiteAddress(website);
        return this.getBuilder();
    }

    @Override
    public <T extends Address> TBuilder setAddress(Builder<T> addressBuilder) {
        BillyValidator.notNull(addressBuilder, BusinessBuilderImpl.LOCALIZER.getString("field.business_address"));
        this.getTypeInstance().setAddress((AddressEntity) addressBuilder.build());
        return this.getBuilder();
    }

    @Override
    public <T extends Address> TBuilder setBillingAddress(Builder<T> addressBuilder) {
        BillyValidator.notNull(addressBuilder,
                BusinessBuilderImpl.LOCALIZER.getString("field.business_billing_address"));
        this.getTypeInstance().setBillingAddress((AddressEntity) addressBuilder.build());
        return this.getBuilder();
    }

    @Override
    public <T extends Address> TBuilder setShippingAddress(Builder<T> addressBuilder) {
        BillyValidator.notNull(addressBuilder,
                BusinessBuilderImpl.LOCALIZER.getString("field.business_shipping_address"));
        this.getTypeInstance().setShippingAddress((AddressEntity) addressBuilder.build());
        return this.getBuilder();
    }

    @Override
    public <T extends Contact> TBuilder addContact(Builder<T> contactBuilder, boolean isMainContact) {
        BillyValidator.notNull(contactBuilder, BusinessBuilderImpl.LOCALIZER.getString("field.business_contact"));

        ContactEntity contact = (ContactEntity) contactBuilder.build();

        this.getTypeInstance().getContacts().add(contact);

        if (isMainContact) {
            this.getTypeInstance().setMainContact(contact);
        }
        return this.getBuilder();
    }

    @Override
    public TBuilder setMainContactUID(UID contactUID) {
        BillyValidator.notNull(contactUID, BusinessBuilderImpl.LOCALIZER.getString("field.business_main_contact"));

        boolean found = false;
        for (Contact c : this.getTypeInstance().getContacts()) {
            if (c.getUID().equals(contactUID)) {
                this.getTypeInstance().setMainContact((ContactEntity) c);
                found = true;
                break;
            }
        }
        if (!found) {
            BillyValidator.found(null, BusinessBuilderImpl.LOCALIZER.getString("field.business_main_contact"));
        }
        return this.getBuilder();
    }

    @Override
    public <T extends Application> TBuilder addApplication(Builder<T> applicationBuilder) {
        BillyValidator.notNull(applicationBuilder, BusinessBuilderImpl.LOCALIZER.getString("field.application"));
        this.getTypeInstance().getApplications().add(applicationBuilder.build());
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        BusinessEntity b = this.getTypeInstance();
        BillyValidator.mandatory(b.getFinancialID(), BusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
        BillyValidator.mandatory(b.getName(), BusinessBuilderImpl.LOCALIZER.getString("field.business_name"));
        BillyValidator.<Object>mandatory(b.getAddress(), BusinessBuilderImpl.LOCALIZER.getString("field.business_address"));
        BillyValidator.notEmpty(b.getContacts(), BusinessBuilderImpl.LOCALIZER.getString("field.business_contact"));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BusinessEntity getTypeInstance() {
        return (BusinessEntity) super.getTypeInstance();
    }

}
