/*
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
package com.premiumminds.billy.portugal.services.entities;

import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.services.builders.impl.PTGenericInvoiceBuilderImpl;

public interface PTGenericInvoice extends GenericInvoice {

    public static enum TYPE {
        FT, FS, FR, NC, ND
    }

    /**
     * Represents if an invoice was emitted by the application or manually
     * emitted.
     *
     * @author Hugo Correia
     *
     */
    public static enum SourceBilling {
        P, M
    }

    public static class Builder extends PTGenericInvoiceBuilderImpl<Builder, PTGenericInvoiceEntry, PTGenericInvoice> {

        @Inject
        public Builder(DAOPTGenericInvoice daoPTGenericInvoice, DAOPTBusiness daoPTBusiness,
                DAOPTCustomer daoPTCustomer, DAOPTSupplier daoPTSupplier) {
            super(daoPTGenericInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
        }
    }

    public TYPE getType();

    public boolean isCancelled();

    public boolean isBilled();

    public String getHash();

    public String getSourceHash();

    public String getHashControl();

    public String getEACCode();

    public SourceBilling getSourceBilling();

    public String getChangeReason();

    public String getATCUD();

    @Override
    public <T extends GenericInvoiceEntry> List<T> getEntries();

    @Override
    public <T extends Payment> List<T> getPayments();

}
