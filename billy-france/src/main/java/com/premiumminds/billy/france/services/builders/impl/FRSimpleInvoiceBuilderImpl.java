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

import java.math.BigDecimal;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.france.exceptions.BillySimpleInvoiceException;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.builders.FRSimpleInvoiceBuilder;
import com.premiumminds.billy.france.services.entities.FRInvoiceEntry;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice.CLIENTTYPE;

public class FRSimpleInvoiceBuilderImpl<TBuilder extends FRSimpleInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends FRInvoiceEntry, TDocument extends FRSimpleInvoice>
        extends FRGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements FRSimpleInvoiceBuilder<TBuilder, TEntry, TDocument> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public <TDAO extends AbstractDAOFRGenericInvoice<? extends TDocument>> FRSimpleInvoiceBuilderImpl(
            TDAO daoFRSimpleInvoice, DAOFRBusiness daoFRBusiness, DAOFRCustomer daoFRCustomer,
            DAOFRSupplier daoFRSupplier) {
        super(daoFRSimpleInvoice, daoFRBusiness, daoFRCustomer, daoFRSupplier);
    }

    @Override
    public TBuilder setClientType(CLIENTTYPE type) {
        BillyValidator.mandatory(type, FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.clientType"));
        this.getTypeInstance().setClientType(type);
        return this.getBuilder();
    }

    @Override
    protected FRSimpleInvoiceEntity getTypeInstance() {
        return (FRSimpleInvoiceEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        FRSimpleInvoiceEntity i = this.getTypeInstance();
        BillyValidator.mandatory(i.getClientType(),
                FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.clientType"));
        super.validateInstance();

        if (i.getClientType() == CLIENTTYPE.CUSTOMER && i.getAmountWithTax().compareTo(new BigDecimal(1000)) >= 0) {
            throw new BillySimpleInvoiceException("Amount > 1000 for customer simple invoice. Issue invoice");
        } else if (i.getClientType() == CLIENTTYPE.BUSINESS &&
                i.getAmountWithTax().compareTo(new BigDecimal(100)) >= 0) {
            throw new BillySimpleInvoiceException("Amount > 100 for business simple invoice. Issue invoice");
        }
    }

}
