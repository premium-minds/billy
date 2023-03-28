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
package com.premiumminds.billy.andorra;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.premiumminds.billy.core.CoreDependencyModule;
import com.premiumminds.billy.persistence.CoreJPADependencyModule;
import com.premiumminds.billy.gin.GINDependencyModule;
import com.premiumminds.billy.andorra.persistence.dao.DAOADAddress;
import com.premiumminds.billy.andorra.persistence.dao.DAOADApplication;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADContact;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNote;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNoteEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceipt;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceiptEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADGenericInvoiceEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoiceEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADPayment;
import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceiptEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.dao.DAOADShippingPoint;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSimpleInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADAddressImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADApplicationImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADBusinessImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADContactImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADCreditNoteEntryImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADCreditNoteImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADCreditReceiptEntryImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADCreditReceiptImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADCustomerImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADGenericInvoiceEntryImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADGenericInvoiceImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADInvoiceEntryImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADInvoiceImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADPaymentImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADProductImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADReceiptEntryImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADReceiptImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADRegionContextImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADShippingPointImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADSimpleInvoiceImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADSupplierImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADTaxImpl;

public class AndorraDependencyModule extends AbstractModule {

    @Override
    protected void configure() {
        this.install(new CoreDependencyModule());
        this.install(new CoreJPADependencyModule());
        this.install(new GINDependencyModule());

        this.bind(DAOADContact.class).to(DAOADContactImpl.class);
        this.bind(DAOADBusiness.class).to(DAOADBusinessImpl.class);
        this.bind(DAOADRegionContext.class).to(DAOADRegionContextImpl.class);
        this.bind(DAOADAddress.class).to(DAOADAddressImpl.class);
        this.bind(DAOADApplication.class).to(DAOADApplicationImpl.class);
        this.bind(DAOADTax.class).to(DAOADTaxImpl.class);
        this.bind(DAOADProduct.class).to(DAOADProductImpl.class);
        this.bind(DAOADSupplier.class).to(DAOADSupplierImpl.class);
        this.bind(DAOADShippingPoint.class).to(DAOADShippingPointImpl.class);
        this.bind(DAOADCustomer.class).to(DAOADCustomerImpl.class);
        this.bind(DAOADInvoice.class).to(DAOADInvoiceImpl.class);
        this.bind(DAOADInvoiceEntry.class).to(DAOADInvoiceEntryImpl.class);
        this.bind(DAOADCreditNote.class).to(DAOADCreditNoteImpl.class);
        this.bind(DAOADCreditNoteEntry.class).to(DAOADCreditNoteEntryImpl.class);
        this.bind(DAOADGenericInvoice.class).to(DAOADGenericInvoiceImpl.class);
        this.bind(DAOADGenericInvoiceEntry.class).to(DAOADGenericInvoiceEntryImpl.class);
        this.bind(DAOADSimpleInvoice.class).to(DAOADSimpleInvoiceImpl.class);
        this.bind(DAOADReceipt.class).to(DAOADReceiptImpl.class);
        this.bind(DAOADReceiptEntry.class).to(DAOADReceiptEntryImpl.class);
        this.bind(DAOADPayment.class).to(DAOADPaymentImpl.class);
        this.bind(DAOADCreditReceipt.class).to(DAOADCreditReceiptImpl.class);
        this.bind(DAOADCreditReceiptEntry.class).to(DAOADCreditReceiptEntryImpl.class);
        this.bind(BillyAndorra.class);
    }

    public static class Initializer {

        @Inject
        public Initializer() {
            // Nothing to initialize
        }
    }

}
