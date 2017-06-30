/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.services.builders.impl;

import com.google.inject.Inject;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.builders.ESManualInvoiceBuilder;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoiceEntry;

public class ESManualInvoiceBuilderImpl<TBuilder extends ESManualInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ESGenericInvoiceEntry, TDocument extends ESGenericInvoice>
        extends ESManualBuilderImpl<TBuilder, TEntry, TDocument>
        implements ESManualInvoiceBuilder<TBuilder, TEntry, TDocument> {

    @Inject
    public ESManualInvoiceBuilderImpl(DAOESGenericInvoice daoESGenericInvoice, DAOESBusiness daoESBusiness,
            DAOESCustomer daoESCustomer, DAOESSupplier daoESSupplier) {
        super(daoESGenericInvoice, daoESBusiness, daoESCustomer, daoESSupplier);
    }

    @Override
    protected ESInvoiceEntity getTypeInstance() {
        return (ESInvoiceEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        ESGenericInvoiceEntity i = this.getTypeInstance();
        i.setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateInstance();
    }
}
