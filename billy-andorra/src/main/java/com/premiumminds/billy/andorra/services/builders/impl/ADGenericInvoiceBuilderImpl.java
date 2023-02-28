/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.services.builders.impl;

import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoice;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoiceEntry;
import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.services.builders.ADGenericInvoiceBuilder;

public class ADGenericInvoiceBuilderImpl<TBuilder extends ADGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ADGenericInvoiceEntry, TDocument extends ADGenericInvoice>
        extends GenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements ADGenericInvoiceBuilder<TBuilder, TEntry, TDocument>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public <TDAO extends AbstractDAOADGenericInvoice<? extends TDocument>> ADGenericInvoiceBuilderImpl(
		TDAO daoESGenericInvoice, DAOADBusiness daoESBusiness, DAOADCustomer daoESCustomer,
		DAOADSupplier daoESSupplier) {
        super(daoESGenericInvoice, daoESBusiness, daoESCustomer, daoESSupplier);
    }

    @Override
    @NotOnUpdate
    public TBuilder setSelfBilled(boolean selfBilled) {
        BillyValidator.notNull(selfBilled, ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.self_billed"));
        this.getTypeInstance().setSelfBilled(selfBilled);
        return this.getBuilder();
    }

    @Override
    protected ADGenericInvoiceEntity getTypeInstance() {
        return (ADGenericInvoiceEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setCancelled(boolean cancelled) {
        if (this.getTypeInstance().isCancelled()) {
            throw new BillyUpdateException("Invoice is allready cancelled!");
        }
        BillyValidator.notNull(cancelled, ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.cancelled"));
        this.getTypeInstance().setCancelled(cancelled);
        return this.getBuilder();
    }

    @Override
    public TBuilder setBilled(boolean billed) {
        if (this.getTypeInstance().isCancelled()) {
            throw new BillyUpdateException("Invoice is allready cancelled!");
        }
        if (this.getTypeInstance().isBilled()) {
            throw new BillyUpdateException("Invoice is allready billed!");
        }
        BillyValidator.notNull(billed, ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.billed"));
        this.getTypeInstance().setBilled(billed);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setSourceId(String source) {
        BillyValidator.notBlank(source, ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.source"));
        this.getTypeInstance().setSourceId(source);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        ADGenericInvoiceEntity i = this.getTypeInstance();
        super.validateValues();
        this.validateESInstance(i);
    }

    protected void validateESInstance(ADGenericInvoiceEntity i) {
        super.validateDate();
        BillyValidator.<Object>mandatory(i.getCustomer(), GenericInvoiceBuilderImpl.LOCALIZER.getString("field.customer"));

        BillyValidator.mandatory(i.getSourceId(), ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.source"));
        BillyValidator.mandatory(i.getDate(), ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.date"));
        if (i.isSelfBilled() != null) {
            BillyValidator.mandatory(i.isSelfBilled(),
									 ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.self_billed"));
        } else {
            i.setSelfBilled(false);
        }
        BillyValidator.mandatory(i.isCancelled(), ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.cancelled"));
        BillyValidator.mandatory(i.isBilled(), ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.billed"));
        BillyValidator.notEmpty(i.getPayments(),
								ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.payment_mechanism"));
        BillyValidator.mandatory(i.getCreditOrDebit(),
								 ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.credit_or_debit"));
    }

}
