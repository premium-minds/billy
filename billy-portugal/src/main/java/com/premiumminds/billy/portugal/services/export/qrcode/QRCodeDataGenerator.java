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
package com.premiumminds.billy.portugal.services.export.qrcode;

import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.Config.Key;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import javax.inject.Inject;
import javax.persistence.LockModeType;

public class QRCodeDataGenerator {

    private final Config config;
    private final DAOInvoiceSeries daoInvoiceSeries;
    private final PTContexts ptContexts;

    @Inject
    public QRCodeDataGenerator(
        final DAOInvoiceSeries daoInvoiceSeries) {
        this.daoInvoiceSeries = daoInvoiceSeries;
        this.config = new Config();

        this.ptContexts= new PTContexts(
            this.config.getUID(Config.Key.Context.Portugal.UUID),
            this.config.getUID(Config.Key.Context.Portugal.Continental.UUID),
            this.config.getUID(Config.Key.Context.Portugal.Azores.UUID),
            this.config.getUID(Config.Key.Context.Portugal.Madeira.UUID)
        );

    }

    public String generateQRCodeData(PTGenericInvoice document) throws RequiredFieldNotFoundException {

        final Customer customer = document.getCustomer();
        final String customerFinancialID;
        if (this.config.getUID(Key.Customer.Generic.UUID).equals(customer.getUID())) {
            customerFinancialID = "999999990";
        } else {
            customerFinancialID = customer.getTaxRegistrationNumber();
        }

        final InvoiceSeriesEntity invoiceSeries =
            this.daoInvoiceSeries.getSeries(
                document.getSeries(),
                document.getBusiness().getUID().toString(),
                LockModeType.NONE);

        return QRCodeBuilder.generateQRCodeString(document, customer, customerFinancialID, invoiceSeries, ptContexts);
    }
}
