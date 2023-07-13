/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.InvalidAmountForDocumentTypeException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.portugal.Config;
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
import java.math.BigDecimal;

public class PTSimpleInvoiceBuilderImpl<TBuilder extends PTSimpleInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTInvoiceEntry, TDocument extends PTSimpleInvoice>
        extends PTGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements PTSimpleInvoiceBuilder<TBuilder, TEntry, TDocument> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    private final Config config;

    public <TDAO extends AbstractDAOPTGenericInvoice<? extends TDocument>> PTSimpleInvoiceBuilderImpl(
            TDAO daoPTSimpleInvoice, DAOPTBusiness daoPTBusiness, DAOPTCustomer daoPTCustomer,
            DAOPTSupplier daoPTSupplier) {
        super(daoPTSimpleInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
        this.setSourceBilling(SourceBilling.P);
        this.config = new Config();
    }

    @Override
    public TBuilder setClientType(CLIENTTYPE type) {
        BillyValidator.mandatory(type, PTGenericInvoiceBuilderImpl.LOCALIZER.getString("field.clientType"));
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

        i.setSourceBilling(SourceBilling.P);
        i.setCreditOrDebit(GenericInvoice.CreditOrDebit.CREDIT);

        BillyValidator.mandatory(i.getClientType(),
                PTGenericInvoiceBuilderImpl.LOCALIZER.getString("field.clientType"));
        super.validateInstance();

        BigDecimal limit = new BigDecimal(config.get(Config.Key.SimpleInvoice.LIMIT_VALUE));

        if (i.getClientType() == CLIENTTYPE.CUSTOMER && i.getAmountWithTax().compareTo(limit) >= 0) {
            throw new InvalidAmountForDocumentTypeException(String.format(
                "Amount > %s for customer simple invoice. Issue invoice", limit));
        } else if (i.getClientType() == CLIENTTYPE.BUSINESS &&
                i.getAmountWithTax().compareTo(new BigDecimal(100)) >= 0) {
            throw new InvalidAmountForDocumentTypeException("Amount > 100 for business simple invoice. Issue invoice");
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
