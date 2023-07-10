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

import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.persistence.entities.ADSimpleInvoiceEntity;
import com.premiumminds.billy.andorra.services.builders.ADSimpleInvoiceBuilder;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.core.exceptions.AmountTooLargeForSimpleInvoiceException;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import java.math.BigDecimal;

public class ADSimpleInvoiceBuilderImpl<TBuilder extends ADSimpleInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ADInvoiceEntry, TDocument extends ADSimpleInvoice>
        extends ADGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements ADSimpleInvoiceBuilder<TBuilder, TEntry, TDocument>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public <TDAO extends AbstractDAOADGenericInvoice<? extends TDocument>> ADSimpleInvoiceBuilderImpl(
        TDAO daoADSimpleInvoice,
        DAOADBusiness daoADBusiness,
        DAOADCustomer daoADCustomer,
        DAOADSupplier daoADSupplier)
    {
        super(daoADSimpleInvoice, daoADBusiness, daoADCustomer, daoADSupplier);
    }

    @Override
    public TBuilder setClientType(CLIENTTYPE type) {
        BillyValidator.mandatory(type, ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.clientType"));
        this.getTypeInstance().setClientType(type);
        return this.getBuilder();
    }

    @Override
    protected ADSimpleInvoiceEntity getTypeInstance() {
        return (ADSimpleInvoiceEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        ADSimpleInvoiceEntity i = this.getTypeInstance();
        BillyValidator.mandatory(i.getClientType(),
                                 ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.clientType"));
        super.validateInstance();

        if (i.getClientType() == CLIENTTYPE.CUSTOMER && i.getAmountWithTax().compareTo(new BigDecimal("40000")) >= 0) {
            throw new AmountTooLargeForSimpleInvoiceException("Amount > 40000 for customer simple invoice. Issue invoice");
        } else if (i.getClientType() == CLIENTTYPE.BUSINESS &&
                i.getAmountWithTax().compareTo(new BigDecimal(100)) >= 0) {
            throw new AmountTooLargeForSimpleInvoiceException("Amount > 100 for business simple invoice. Issue invoice");
        }
    }

}
