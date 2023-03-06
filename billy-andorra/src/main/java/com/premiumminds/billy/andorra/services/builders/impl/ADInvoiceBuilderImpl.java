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

import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.services.builders.ADInvoiceBuilder;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry;

public class ADInvoiceBuilderImpl<TBuilder extends ADInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ADInvoiceEntry, TDocument extends ADInvoice>
        extends ADGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements ADInvoiceBuilder<TBuilder, TEntry, TDocument>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public <TDAO extends AbstractDAOADGenericInvoice<? extends TDocument>> ADInvoiceBuilderImpl(
		TDAO daoADInvoice,
		DAOADBusiness daoADBusiness,
		DAOADCustomer daoADCustomer,
		DAOADSupplier daoADSupplier)
	{
        super(daoADInvoice, daoADBusiness, daoADCustomer, daoADSupplier);
    }

    @Override
    protected ADInvoiceEntity getTypeInstance() {
        return (ADInvoiceEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        ADInvoiceEntity i = this.getTypeInstance();
        i.setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateInstance();
    }
}
