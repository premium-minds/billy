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

import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.services.builders.impl.ADGenericInvoiceBuilderImpl;

public interface ADGenericInvoice extends GenericInvoice {

    public static class Builder extends ADGenericInvoiceBuilderImpl<Builder, ADGenericInvoiceEntry, ADGenericInvoice> {

        @Inject
        public Builder(
			DAOADGenericInvoice daoADGenericInvoice,
			DAOADBusiness daoADBusiness,
			DAOADCustomer daoADCustomer,
			DAOADSupplier daoADSupplier)
		{
            super(daoADGenericInvoice, daoADBusiness, daoADCustomer, daoADSupplier);
        }
    }

    public boolean isCancelled();

    public boolean isBilled();

    public String getEACCode();

    @Override
    public <T extends GenericInvoiceEntry> List<T> getEntries();

    @Override
    public <T extends Payment> List<T> getPayments();

}
