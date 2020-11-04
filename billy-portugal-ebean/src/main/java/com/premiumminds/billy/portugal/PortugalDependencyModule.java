/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.premiumminds.billy.core.CoreDependencyModule;
import com.premiumminds.billy.core.CoreJPADependencyModule;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.dao.ebean.DAOInvoiceSeriesImpl;
import com.premiumminds.billy.gin.GINDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTApplication;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTPayment;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTShippingPoint;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTAddressImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTApplicationImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTBusinessImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTContactImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTCreditNoteEntryImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTCreditNoteImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTCustomerImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTGenericInvoiceEntryImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTGenericInvoiceImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTInvoiceEntryImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTInvoiceImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTPaymentImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTProductImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTReceiptInvoiceImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTRegionContextImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTShippingPointImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTSimpleInvoiceImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTSupplierImpl;
import com.premiumminds.billy.portugal.persistence.dao.ebean.DAOPTTaxImpl;

public class PortugalDependencyModule extends AbstractModule {

    @Override
    protected void configure() {
        this.install(new CoreDependencyModule());
        this.install(new CoreJPADependencyModule());
        this.install(new GINDependencyModule());

        this.bind(DAOPTContact.class).to(DAOPTContactImpl.class);
        this.bind(DAOPTBusiness.class).to(DAOPTBusinessImpl.class);
        this.bind(DAOPTRegionContext.class).to(DAOPTRegionContextImpl.class);
        this.bind(DAOPTAddress.class).to(DAOPTAddressImpl.class);
        this.bind(DAOPTApplication.class).to(DAOPTApplicationImpl.class);
        this.bind(DAOPTTax.class).to(DAOPTTaxImpl.class);
        this.bind(DAOPTProduct.class).to(DAOPTProductImpl.class);
        this.bind(DAOPTSupplier.class).to(DAOPTSupplierImpl.class);
        this.bind(DAOPTShippingPoint.class).to(DAOPTShippingPointImpl.class);
        this.bind(DAOPTCustomer.class).to(DAOPTCustomerImpl.class);
        this.bind(DAOPTInvoice.class).to(DAOPTInvoiceImpl.class);
        this.bind(DAOPTInvoiceEntry.class).to(DAOPTInvoiceEntryImpl.class);
        this.bind(DAOPTCreditNote.class).to(DAOPTCreditNoteImpl.class);
        this.bind(DAOPTCreditNoteEntry.class).to(DAOPTCreditNoteEntryImpl.class);
        this.bind(DAOPTGenericInvoice.class).to(DAOPTGenericInvoiceImpl.class);
        this.bind(DAOPTGenericInvoiceEntry.class).to(DAOPTGenericInvoiceEntryImpl.class);
        this.bind(DAOPTSimpleInvoice.class).to(DAOPTSimpleInvoiceImpl.class);
        this.bind(DAOPTPayment.class).to(DAOPTPaymentImpl.class);
        this.bind(DAOPTReceiptInvoice.class).to(DAOPTReceiptInvoiceImpl.class);
        this.bind(DAOInvoiceSeries.class).to(DAOInvoiceSeriesImpl.class);
        this.bind(BillyPortugal.class);
    }

    public static class Initializer {

        @Inject
        public Initializer() {
            // Nothing to initialize
        }
    }

}
