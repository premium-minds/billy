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
package com.premiumminds.billy.france.test;

import org.mockito.Mockito;

import com.premiumminds.billy.core.test.MockDependencyModule;
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
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRCreditNoteEntryImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRCreditNoteImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRCreditReceiptEntryImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRCreditReceiptImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRGenericInvoiceEntryImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRGenericInvoiceImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRPaymentImpl;
import com.premiumminds.billy.france.persistence.dao.jpa.DAOFRSimpleInvoiceImpl;

public class FRMockDependencyModule extends MockDependencyModule {

    @Override
    protected void configure() {
        super.configure();

        this.bind(DAOFRRegionContext.class).toInstance(Mockito.mock(DAOFRRegionContext.class));
        this.bind(DAOFRContact.class).toInstance(Mockito.mock(DAOFRContact.class));
        this.bind(DAOFRAddress.class).toInstance(Mockito.mock(DAOFRAddress.class));
        this.bind(DAOFRApplication.class).toInstance(Mockito.mock(DAOFRApplication.class));
        this.bind(DAOFRTax.class).toInstance(Mockito.mock(DAOFRTax.class));
        this.bind(DAOFRProduct.class).toInstance(Mockito.mock(DAOFRProduct.class));
        this.bind(DAOFRSupplier.class).toInstance(Mockito.mock(DAOFRSupplier.class));
        this.bind(DAOFRBusiness.class).toInstance(Mockito.mock(DAOFRBusiness.class));
        this.bind(DAOFRShippingPoint.class).toInstance(Mockito.mock(DAOFRShippingPoint.class));
        this.bind(DAOFRCustomer.class).toInstance(Mockito.mock(DAOFRCustomer.class));
        this.bind(DAOFRInvoiceEntry.class).toInstance(Mockito.mock(DAOFRInvoiceEntry.class));
        this.bind(DAOFRInvoice.class).toInstance(Mockito.mock(DAOFRInvoice.class));
        this.bind(DAOFRCreditNote.class).toInstance(Mockito.mock(DAOFRCreditNoteImpl.class));
        this.bind(DAOFRCreditNoteEntry.class).toInstance(Mockito.mock(DAOFRCreditNoteEntryImpl.class));
        this.bind(DAOFRGenericInvoice.class).toInstance(Mockito.mock(DAOFRGenericInvoiceImpl.class));
        this.bind(DAOFRGenericInvoiceEntry.class).toInstance(Mockito.mock(DAOFRGenericInvoiceEntryImpl.class));
        this.bind(DAOFRSimpleInvoice.class).toInstance(Mockito.mock(DAOFRSimpleInvoiceImpl.class));
        this.bind(DAOFRPayment.class).toInstance(Mockito.mock(DAOFRPaymentImpl.class));
        this.bind(DAOFRReceipt.class).toInstance(Mockito.mock(DAOFRReceipt.class));
        this.bind(DAOFRReceiptEntry.class).toInstance(Mockito.mock(DAOFRReceiptEntry.class));
        this.bind(DAOFRCreditReceipt.class).toInstance(Mockito.mock(DAOFRCreditReceiptImpl.class));
        this.bind(DAOFRCreditReceiptEntry.class).toInstance(Mockito.mock(DAOFRCreditReceiptEntryImpl.class));
    }

}
