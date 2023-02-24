/*
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
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.services.builders.impl.FRReceiptBuilderImpl;

public interface FRReceipt extends FRGenericInvoice {

    class Builder extends FRReceiptBuilderImpl<Builder, FRReceiptEntry, FRReceipt> {

        @Inject
        public Builder(DAOFRReceipt daoFRInvoice, DAOFRBusiness daoFRBusiness, DAOFRCustomer daoFRCustomer,
                DAOFRSupplier daoFRSupplier) {
            super(daoFRInvoice, daoFRBusiness, daoFRCustomer, daoFRSupplier);
        }
    }

    @Override
    <T extends GenericInvoiceEntry> List<T> getEntries();

    @Override
    <T extends Payment> List<T> getPayments();

}
