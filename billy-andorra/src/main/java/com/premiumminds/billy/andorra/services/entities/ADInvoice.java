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
package com.premiumminds.billy.andorra.services.entities;

import com.premiumminds.billy.andorra.services.builders.impl.ADInvoiceBuilderImpl;
import com.premiumminds.billy.andorra.services.builders.impl.ADManualInvoiceBuilderImpl;
import java.util.List;

import jakarta.inject.Inject;

import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;

public interface ADInvoice extends ADGenericInvoice {

    public static class Builder extends ADInvoiceBuilderImpl<Builder, ADInvoiceEntry, ADInvoice> {

        @Inject
        public Builder(
            DAOADInvoice daoADInvoice,
            DAOADBusiness daoADBusiness,
            DAOADCustomer daoADCustomer,
            DAOADSupplier daoADSupplier) {
            super(daoADInvoice, daoADBusiness, daoADCustomer, daoADSupplier);
        }
    }

    public static class ManualBuilder extends ADManualInvoiceBuilderImpl<ManualBuilder, ADInvoiceEntry, ADInvoice> {

        @Inject
        public ManualBuilder(
            DAOADInvoice daoADInvoice,
            DAOADBusiness daoADBusiness,
            DAOADCustomer daoADCustomer,
            DAOADSupplier daoADSupplier)
        {
            super(daoADInvoice, daoADBusiness, daoADCustomer, daoADSupplier);
        }
    }

    @Override
    public <T extends GenericInvoiceEntry> List<T> getEntries();

    @Override
    public <T extends Payment> List<T> getPayments();

}
