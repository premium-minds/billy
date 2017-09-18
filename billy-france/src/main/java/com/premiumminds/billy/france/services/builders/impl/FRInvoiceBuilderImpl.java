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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.builders.FRInvoiceBuilder;
import com.premiumminds.billy.france.services.entities.FRInvoice;
import com.premiumminds.billy.france.services.entities.FRInvoiceEntry;

public class FRInvoiceBuilderImpl<TBuilder extends FRInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends FRInvoiceEntry, TDocument extends FRInvoice>
        extends FRGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements FRInvoiceBuilder<TBuilder, TEntry, TDocument> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public <TDAO extends AbstractDAOFRGenericInvoice<? extends TDocument>> FRInvoiceBuilderImpl(TDAO daoFRInvoice,
            DAOFRBusiness daoFRBusiness, DAOFRCustomer daoFRCustomer, DAOFRSupplier daoFRSupplier) {
        super(daoFRInvoice, daoFRBusiness, daoFRCustomer, daoFRSupplier);
    }

    @Override
    protected FRInvoiceEntity getTypeInstance() {
        return (FRInvoiceEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        FRInvoiceEntity i = this.getTypeInstance();
        i.setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateInstance();
    }
}
