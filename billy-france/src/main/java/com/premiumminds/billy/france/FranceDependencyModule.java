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
package com.premiumminds.billy.france;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.premiumminds.billy.core.CoreDependencyModule;
import com.premiumminds.billy.core.CoreJPADependencyModule;
import com.premiumminds.billy.france.persistence.dao.DAOFRAddress;
import com.premiumminds.billy.france.persistence.dao.DAOFRApplication;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.dao.DAOFRContact;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNote;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNoteEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceiptEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRGenericInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRGenericInvoiceEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoiceEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRPayment;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceiptEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRShippingPoint;
import com.premiumminds.billy.france.persistence.dao.DAOFRSimpleInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRAddressImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRApplicationImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRBusinessImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRContactImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRCreditNoteEntryImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRCreditNoteImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRCreditReceiptEntryImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRCreditReceiptImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRCustomerImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRGenericInvoiceEntryImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRGenericInvoiceImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRInvoiceEntryImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRInvoiceImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRPaymentImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRProductImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRReceiptEntryImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRReceiptImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRRegionContextImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRShippingPointImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRSimpleInvoiceImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRSupplierImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRTaxImpl;
import com.premiumminds.billy.gin.GINDependencyModule;

public class FranceDependencyModule extends AbstractModule {

    @Override
    protected void configure() {
        this.install(new CoreDependencyModule());
        this.install(new CoreJPADependencyModule());
        this.install(new GINDependencyModule());

        this.bind(DAOFRContact.class).to(DAOFRContactImpl.class);
        this.bind(DAOFRBusiness.class).to(DAOFRBusinessImpl.class);
        this.bind(DAOFRRegionContext.class).to(DAOFRRegionContextImpl.class);
        this.bind(DAOFRAddress.class).to(DAOFRAddressImpl.class);
        this.bind(DAOFRApplication.class).to(DAOFRApplicationImpl.class);
        this.bind(DAOFRTax.class).to(DAOFRTaxImpl.class);
        this.bind(DAOFRProduct.class).to(DAOFRProductImpl.class);
        this.bind(DAOFRSupplier.class).to(DAOFRSupplierImpl.class);
        this.bind(DAOFRShippingPoint.class).to(DAOFRShippingPointImpl.class);
        this.bind(DAOFRCustomer.class).to(DAOFRCustomerImpl.class);
        this.bind(DAOFRInvoice.class).to(DAOFRInvoiceImpl.class);
        this.bind(DAOFRInvoiceEntry.class).to(DAOFRInvoiceEntryImpl.class);
        this.bind(DAOFRCreditNote.class).to(DAOFRCreditNoteImpl.class);
        this.bind(DAOFRCreditNoteEntry.class).to(DAOFRCreditNoteEntryImpl.class);
        this.bind(DAOFRGenericInvoice.class).to(DAOFRGenericInvoiceImpl.class);
        this.bind(DAOFRGenericInvoiceEntry.class).to(DAOFRGenericInvoiceEntryImpl.class);
        this.bind(DAOFRSimpleInvoice.class).to(DAOFRSimpleInvoiceImpl.class);
        this.bind(DAOFRReceipt.class).to(DAOFRReceiptImpl.class);
        this.bind(DAOFRReceiptEntry.class).to(DAOFRReceiptEntryImpl.class);
        this.bind(DAOFRPayment.class).to(DAOFRPaymentImpl.class);
        this.bind(DAOFRCreditReceipt.class).to(DAOFRCreditReceiptImpl.class);
        this.bind(DAOFRCreditReceiptEntry.class).to(DAOFRCreditReceiptEntryImpl.class);
        this.bind(BillyFrance.class);
    }

    public static class Initializer {

        @Inject
        public Initializer() {
            // Nothing to initialize
        }
    }

}
