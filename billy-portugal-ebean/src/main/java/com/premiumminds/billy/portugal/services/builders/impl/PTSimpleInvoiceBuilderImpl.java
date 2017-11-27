/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import java.math.BigDecimal;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.portugal.exceptions.BillySimpleInvoiceException;
import com.premiumminds.billy.portugal.persistence.dao.AbstractDAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.builders.PTSimpleInvoiceBuilder;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice.CLIENTTYPE;

public class PTSimpleInvoiceBuilderImpl<TBuilder extends PTSimpleInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTInvoiceEntry, TDocument extends PTSimpleInvoice>
        extends PTGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements PTSimpleInvoiceBuilder<TBuilder, TEntry, TDocument> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public <TDAO extends AbstractDAOPTGenericInvoice<? extends TDocument>> PTSimpleInvoiceBuilderImpl(
            TDAO daoPTSimpleInvoice, DAOPTBusiness daoPTBusiness, DAOPTCustomer daoPTCustomer,
            DAOPTSupplier daoPTSupplier) {
        super(daoPTSimpleInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
        this.setSourceBilling(SourceBilling.P);
    }

    @Override
    public TBuilder setClientType(CLIENTTYPE type) {
        // The <generic> specs below are necessary because type inference fails here for unknown reasons
        // If removed, these lines will fail in runtime with a linkage error (ClassCastException)
        BillyValidator.<CLIENTTYPE>mandatory(type, PTGenericInvoiceBuilderImpl.LOCALIZER.getString("field.clientType"));
        this.getTypeInstance().setClientType(type);
        return this.getBuilder();
    }

    @Override
    protected PTSimpleInvoiceEntity getTypeInstance() {
        return (PTSimpleInvoiceEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        PTSimpleInvoiceEntity i = this.getTypeInstance();
        // The <generic> specs below are necessary because type inference fails here for unknown reasons
        // If removed, these lines will fail in runtime with a linkage error (ClassCastException)
        BillyValidator.<CLIENTTYPE>mandatory(i.getClientType(),
                PTGenericInvoiceBuilderImpl.LOCALIZER.getString("field.clientType"));
        super.validateInstance();

        if (i.getClientType() == CLIENTTYPE.CUSTOMER && i.getAmountWithTax().compareTo(new BigDecimal(1000)) >= 0) {
            throw new BillySimpleInvoiceException("Amount > 1000 for customer simple invoice. Issue invoice");
        } else if (i.getClientType() == CLIENTTYPE.BUSINESS &&
                i.getAmountWithTax().compareTo(new BigDecimal(100)) >= 0) {
            throw new BillySimpleInvoiceException("Amount > 100 for business simple invoice. Issue invoice");
        }
    }

    @Override
    @NotOnUpdate
    public TBuilder setSourceBilling(SourceBilling sourceBilling) {
        switch (sourceBilling) {
            case P:
                return super.setSourceBilling(sourceBilling);
            case M:
            default:
                throw new BillyValidationException();
        }
    }

}
