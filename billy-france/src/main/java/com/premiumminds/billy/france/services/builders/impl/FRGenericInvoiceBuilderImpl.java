/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.persistence.entities.FRGenericInvoiceEntity;
import com.premiumminds.billy.france.services.builders.FRGenericInvoiceBuilder;
import com.premiumminds.billy.france.services.entities.FRGenericInvoice;
import com.premiumminds.billy.france.services.entities.FRGenericInvoiceEntry;

public class FRGenericInvoiceBuilderImpl<TBuilder extends FRGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends FRGenericInvoiceEntry, TDocument extends FRGenericInvoice>
        extends GenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements FRGenericInvoiceBuilder<TBuilder, TEntry, TDocument> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public <TDAO extends AbstractDAOFRGenericInvoice<? extends TDocument>> FRGenericInvoiceBuilderImpl(
            TDAO daoFRGenericInvoice, DAOFRBusiness daoFRBusiness, DAOFRCustomer daoFRCustomer,
            DAOFRSupplier daoFRSupplier) {
        super(daoFRGenericInvoice, daoFRBusiness, daoFRCustomer, daoFRSupplier);
    }

    @Override
    @NotOnUpdate
    public TBuilder setSelfBilled(boolean selfBilled) {
        BillyValidator.notNull(selfBilled, FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.self_billed"));
        this.getTypeInstance().setSelfBilled(selfBilled);
        return this.getBuilder();
    }

    @Override
    protected FRGenericInvoiceEntity getTypeInstance() {
        return (FRGenericInvoiceEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setCancelled(boolean cancelled) {
        if (this.getTypeInstance().isCancelled()) {
            throw new BillyUpdateException("Invoice is allready cancelled!");
        }
        BillyValidator.notNull(cancelled, FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.cancelled"));
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
        BillyValidator.notNull(billed, FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.billed"));
        this.getTypeInstance().setBilled(billed);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setSourceId(String source) {
        BillyValidator.notBlank(source, FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.source"));
        this.getTypeInstance().setSourceId(source);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        FRGenericInvoiceEntity i = this.getTypeInstance();
        super.validateValues();
        this.validateFRInstance(i);
    }

    protected void validateFRInstance(FRGenericInvoiceEntity i) {
        super.validateDate();
        BillyValidator.mandatory(i.getCustomer(), GenericInvoiceBuilderImpl.LOCALIZER.getString("field.customer"));

        BillyValidator.mandatory(i.getSourceId(), FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.source"));
        BillyValidator.mandatory(i.getDate(), FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.date"));
        if (i.isSelfBilled() != null) {
            BillyValidator.mandatory(i.isSelfBilled(),
                    FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.self_billed"));
        } else {
            i.setSelfBilled(false);
        }
        BillyValidator.mandatory(i.isCancelled(), FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.cancelled"));
        BillyValidator.mandatory(i.isBilled(), FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.billed"));
        BillyValidator.notEmpty(i.getPayments(),
                FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.payment_mechanism"));
    }

}
