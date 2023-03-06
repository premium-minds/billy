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
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.services.builders.ADManualInvoiceBuilder;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoice;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoiceEntry;

public class ADManualInvoiceBuilderImpl<TBuilder extends ADManualInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ADGenericInvoiceEntry, TDocument extends ADGenericInvoice>
        extends ADManualBuilderImpl<TBuilder, TEntry, TDocument>
        implements ADManualInvoiceBuilder<TBuilder, TEntry, TDocument>
{

    public <TDAO extends AbstractDAOADGenericInvoice<? extends TDocument>> ADManualInvoiceBuilderImpl(
		TDAO daoADGenericInvoice,
		DAOADBusiness daoADBusiness,
		DAOADCustomer daoADCustomer,
		DAOADSupplier daoADSupplier)
	{
        super(daoADGenericInvoice, daoADBusiness, daoADCustomer, daoADSupplier);
    }

    @Override
    protected ADInvoiceEntity getTypeInstance() {
        return (ADInvoiceEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        ADGenericInvoiceEntity i = this.getTypeInstance();
        i.setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateInstance();
    }
}
