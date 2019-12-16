/*
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
package com.premiumminds.billy.spain.services.entities;

import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.services.builders.impl.ESInvoiceBuilderImpl;
import com.premiumminds.billy.spain.services.builders.impl.ESManualInvoiceBuilderImpl;

public interface ESInvoice extends ESGenericInvoice {

    public static class Builder extends ESInvoiceBuilderImpl<Builder, ESInvoiceEntry, ESInvoice> {

        @Inject
        public Builder(DAOESInvoice daoESInvoice, DAOESBusiness daoESBusiness, DAOESCustomer daoESCustomer,
                DAOESSupplier daoESSupplier) {
            super(daoESInvoice, daoESBusiness, daoESCustomer, daoESSupplier);
        }
    }

    public static class ManualBuilder extends ESManualInvoiceBuilderImpl<ManualBuilder, ESInvoiceEntry, ESInvoice> {

        @Inject
        public ManualBuilder(DAOESInvoice daoESInvoice, DAOESBusiness daoESBusiness, DAOESCustomer daoESCustomer,
                DAOESSupplier daoESSupplier) {
            super(daoESInvoice, daoESBusiness, daoESCustomer, daoESSupplier);
        }
    }

    @Override
    public <T extends GenericInvoiceEntry> List<T> getEntries();

    @Override
    public <T extends Payment> List<T> getPayments();

}
