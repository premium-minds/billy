/**
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

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.AbstractDAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.builders.PTManualInvoiceBuilder;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;

public class PTManualInvoiceBuilderImpl<TBuilder extends PTManualInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTGenericInvoiceEntry, TDocument extends PTGenericInvoice>
        extends PTManualBuilderImpl<TBuilder, TEntry, TDocument>
        implements PTManualInvoiceBuilder<TBuilder, TEntry, TDocument> {

    @Inject
    public <TDAO extends AbstractDAOPTGenericInvoice<? extends TDocument>> PTManualInvoiceBuilderImpl(
            TDAO daoPTGenericInvoice, DAOPTBusiness daoPTBusiness, DAOPTCustomer daoPTCustomer,
            DAOPTSupplier daoPTSupplier) {
        super(daoPTGenericInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
    }

    @Override
    protected PTInvoiceEntity getTypeInstance() {
        return (PTInvoiceEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        PTGenericInvoiceEntity i = this.getTypeInstance();
        i.setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateInstance();
    }

}
