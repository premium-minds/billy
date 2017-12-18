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
package com.premiumminds.billy.france.test.services.documents;

import java.util.Date;

import org.junit.Before;

import com.premiumminds.billy.core.exceptions.NotImplementedException;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.entities.FRGenericInvoiceEntity;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParamsImpl;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.util.FRInvoiceTestUtil;
import com.premiumminds.billy.france.test.util.FRReceiptTestUtil;
import com.premiumminds.billy.france.test.util.FRSimpleInvoiceTestUtil;

public class FRDocumentAbstractTest extends FRPersistencyAbstractTest {

    protected FRIssuingParams parameters;

    public enum INVOICE_TYPE {
        FT, // Invoice
        RC, // Receipt
        FS, // Simple Invoice
        NC	// Credit Note
    }

    public enum SOURCE_BILLING {
        APPLICATION, MANUAL
    }

    @Before
    public void setUpParamenters() {
        this.parameters = new FRIssuingParamsImpl();
        this.parameters.setEACCode("31400");
    }

    protected <T extends FRGenericInvoiceEntity> T newInvoice(INVOICE_TYPE type) {
        return this.newInvoice(type, SOURCE_BILLING.APPLICATION);
    }

    @SuppressWarnings("unchecked")
    protected <T extends FRGenericInvoiceEntity> T newInvoice(INVOICE_TYPE type, SOURCE_BILLING source) {

        switch (type) {
            case FT:
                return (T) new FRInvoiceTestUtil(FRAbstractTest.injector).getInvoiceEntity(source);
            case RC:
                return (T) new FRReceiptTestUtil(FRAbstractTest.injector).getReceiptEntity();
            case FS:
                return (T) new FRSimpleInvoiceTestUtil(FRAbstractTest.injector).getSimpleInvoiceEntity();
            case NC:
                throw new NotImplementedException();
            default:
                return null;
        }
    }

    protected <T extends DocumentIssuingHandler<I, FRIssuingParams>, I extends FRGenericInvoiceEntity> void
            issueNewInvoice(T handler, I invoice, String series) throws DocumentIssuingException {
        DAOFRInvoice dao = this.getInstance(DAOFRInvoice.class);
        try {
            dao.beginTransaction();
            invoice.initializeEntityDates();
            this.issueNewInvoice(handler, invoice, series, new Date(invoice.getCreateTimestamp().getTime() + 100));
            dao.commit();
        } catch (DocumentIssuingException up) {
            dao.rollback();
            throw up;
        }
    }

    protected <T extends DocumentIssuingHandler<I, FRIssuingParams>, I extends FRGenericInvoiceEntity> void
            issueNewInvoice(T handler, I invoice, String series, Date date) throws DocumentIssuingException {
        this.parameters.setInvoiceSeries(series);
        invoice.setDate(date);
        handler.issue(invoice, this.parameters);
    }

}
