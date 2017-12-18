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

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoiceEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntryEntity;
import com.premiumminds.billy.france.services.builders.FRInvoiceEntryBuilder;
import com.premiumminds.billy.france.services.entities.FRInvoiceEntry;

public class FRInvoiceEntryBuilderImpl<TBuilder extends FRInvoiceEntryBuilderImpl<TBuilder, TEntry>, TEntry extends FRInvoiceEntry>
        extends FRGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, DAOFRInvoiceEntry, DAOFRInvoice>
        implements FRInvoiceEntryBuilder<TBuilder, TEntry> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public FRInvoiceEntryBuilderImpl(DAOFRInvoiceEntry daoFREntry, DAOFRInvoice daoFRInvoice, DAOFRTax daoFRTax,
            DAOFRProduct daoFRProduct, DAOFRRegionContext daoFRRegionContext) {
        super(daoFREntry, daoFRInvoice, daoFRTax, daoFRProduct, daoFRRegionContext);
    }

    @Override
    protected FRInvoiceEntryEntity getTypeInstance() {
        return (FRInvoiceEntryEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateInstance();
    }

}
