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
package com.premiumminds.billy.andorra.test;

import org.mockito.Mockito;

import com.premiumminds.billy.core.test.MockDependencyModule;
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
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADCreditNoteEntryImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADCreditNoteImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADCreditReceiptEntryImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADCreditReceiptImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADGenericInvoiceEntryImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADGenericInvoiceImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADPaymentImpl;
import com.premiumminds.billy.andorra.persistence.dao.jpa.DAOADSimpleInvoiceImpl;

public class ADMockDependencyModule extends MockDependencyModule {

    @Override
    protected void configure() {
        super.configure();

        this.bind(DAOADRegionContext.class).toInstance(Mockito.mock(DAOADRegionContext.class));
        this.bind(DAOADContact.class).toInstance(Mockito.mock(DAOADContact.class));
        this.bind(DAOADAddress.class).toInstance(Mockito.mock(DAOADAddress.class));
        this.bind(DAOADApplication.class).toInstance(Mockito.mock(DAOADApplication.class));
        this.bind(DAOADTax.class).toInstance(Mockito.mock(DAOADTax.class));
        this.bind(DAOADProduct.class).toInstance(Mockito.mock(DAOADProduct.class));
        this.bind(DAOADSupplier.class).toInstance(Mockito.mock(DAOADSupplier.class));
        this.bind(DAOADBusiness.class).toInstance(Mockito.mock(DAOADBusiness.class));
        this.bind(DAOADShippingPoint.class).toInstance(Mockito.mock(DAOADShippingPoint.class));
        this.bind(DAOADCustomer.class).toInstance(Mockito.mock(DAOADCustomer.class));
        this.bind(DAOADInvoiceEntry.class).toInstance(Mockito.mock(DAOADInvoiceEntry.class));
        this.bind(DAOADInvoice.class).toInstance(Mockito.mock(DAOADInvoice.class));
        this.bind(DAOADCreditNote.class).toInstance(Mockito.mock(DAOADCreditNoteImpl.class));
        this.bind(DAOADCreditNoteEntry.class).toInstance(Mockito.mock(DAOADCreditNoteEntryImpl.class));
        this.bind(DAOADGenericInvoice.class).toInstance(Mockito.mock(DAOADGenericInvoiceImpl.class));
        this.bind(DAOADGenericInvoiceEntry.class).toInstance(Mockito.mock(DAOADGenericInvoiceEntryImpl.class));
        this.bind(DAOADSimpleInvoice.class).toInstance(Mockito.mock(DAOADSimpleInvoiceImpl.class));
        this.bind(DAOADPayment.class).toInstance(Mockito.mock(DAOADPaymentImpl.class));
        this.bind(DAOADReceipt.class).toInstance(Mockito.mock(DAOADReceipt.class));
        this.bind(DAOADReceiptEntry.class).toInstance(Mockito.mock(DAOADReceiptEntry.class));
        this.bind(DAOADCreditReceipt.class).toInstance(Mockito.mock(DAOADCreditReceiptImpl.class));
        this.bind(DAOADCreditReceiptEntry.class).toInstance(Mockito.mock(DAOADCreditReceiptEntryImpl.class));
    }

}
