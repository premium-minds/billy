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
package com.premiumminds.billy.france.services.entities;

import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.services.builders.impl.FRInvoiceBuilderImpl;
import com.premiumminds.billy.france.services.builders.impl.FRManualInvoiceBuilderImpl;

public interface FRInvoice extends FRGenericInvoice {

    public static class Builder extends FRInvoiceBuilderImpl<Builder, FRInvoiceEntry, FRInvoice> {

        @Inject
        public Builder(DAOFRInvoice daoFRInvoice, DAOFRBusiness daoFRBusiness, DAOFRCustomer daoFRCustomer,
                DAOFRSupplier daoFRSupplier) {
            super(daoFRInvoice, daoFRBusiness, daoFRCustomer, daoFRSupplier);
        }
    }

    public static class ManualBuilder extends FRManualInvoiceBuilderImpl<ManualBuilder, FRInvoiceEntry, FRInvoice> {

        @Inject
        public ManualBuilder(DAOFRInvoice daoFRInvoice, DAOFRBusiness daoFRBusiness, DAOFRCustomer daoFRCustomer,
                DAOFRSupplier daoFRSupplier) {
            super(daoFRInvoice, daoFRBusiness, daoFRCustomer, daoFRSupplier);
        }
    }

    @Override
    public <T extends GenericInvoiceEntry> List<T> getEntries();

    @Override
    public <T extends Payment> List<T> getPayments();

}
