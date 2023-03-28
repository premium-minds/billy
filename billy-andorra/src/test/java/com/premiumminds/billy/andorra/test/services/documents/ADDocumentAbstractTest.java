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
package com.premiumminds.billy.andorra.test.services.documents;

import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParamsImpl;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import com.premiumminds.billy.andorra.test.util.ADInvoiceTestUtil;
import com.premiumminds.billy.andorra.test.util.ADReceiptTestUtil;
import com.premiumminds.billy.andorra.test.util.ADSimpleInvoiceTestUtil;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;

import com.premiumminds.billy.core.exceptions.NotImplementedException;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;

public class ADDocumentAbstractTest extends ADPersistencyAbstractTest {

    protected ADIssuingParams parameters;

    public enum INVOICE_TYPE {
        FT, // Invoice
        RC, // Receipt
        FS, // Simple Invoice
        NC	// Credit Note
    }

    public enum SOURCE_BILLING {
        APPLICATION, MANUAL
    }

    @BeforeEach
    public void setUpParamenters() {
        this.parameters = new ADIssuingParamsImpl();
        this.parameters.setEACCode("31400");
    }

    protected <T extends ADGenericInvoiceEntity> T newInvoice(INVOICE_TYPE type) {
        return this.newInvoice(type, SOURCE_BILLING.APPLICATION);
    }

    @SuppressWarnings("unchecked")
    protected <T extends ADGenericInvoiceEntity> T newInvoice(INVOICE_TYPE type, SOURCE_BILLING source) {

        switch (type) {
            case FT:
                return (T) new ADInvoiceTestUtil(ADAbstractTest.injector).getInvoiceEntity(source);
            case RC:
                return (T) new ADReceiptTestUtil(ADAbstractTest.injector).getReceiptEntity();
            case FS:
                return (T) new ADSimpleInvoiceTestUtil(ADAbstractTest.injector).getSimpleInvoiceEntity();
            case NC:
                throw new NotImplementedException();
            default:
                return null;
        }
    }

    protected <T extends DocumentIssuingHandler<I, ADIssuingParams>, I extends ADGenericInvoiceEntity> void
            issueNewInvoice(T handler, I invoice, String series)
            throws DocumentIssuingException, DocumentSeriesDoesNotExistException, SeriesUniqueCodeNotFilled {
        DAOADInvoice dao = this.getInstance(DAOADInvoice.class);
        try {
            dao.beginTransaction();
            invoice.initializeEntityDates();
            this.issueNewInvoice(handler, invoice, series, new Date(invoice.getCreateTimestamp().getTime() + 100));
            dao.commit();
        } catch (DocumentIssuingException | DocumentSeriesDoesNotExistException | SeriesUniqueCodeNotFilled up) {
            dao.rollback();
            throw up;
        }
    }

    protected <T extends DocumentIssuingHandler<I, ADIssuingParams>, I extends ADGenericInvoiceEntity> void
            issueNewInvoice(T handler, I invoice, String series, Date date)
            throws DocumentIssuingException, DocumentSeriesDoesNotExistException, SeriesUniqueCodeNotFilled {
        this.parameters.setInvoiceSeries(series);
        invoice.setDate(date);
        handler.issue(invoice, this.parameters);
    }

}
