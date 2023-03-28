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
package com.premiumminds.billy.andorra.test.services.documents.handler;

import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.andorra.services.documents.ADInvoiceIssuingHandler;
import com.premiumminds.billy.andorra.test.services.documents.ADDocumentAbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestADInvoiceIssuingHandler extends ADDocumentAbstractTest {

    private ADInvoiceIssuingHandler handler;
    private StringID<GenericInvoice> issuedInvoiceUID;

    private String DEFAULT_SERIES = INVOICE_TYPE.FT + " " + ADPersistencyAbstractTest.DEFAULT_SERIES;

    @BeforeEach
    public void setUpNewInvoice() {
        this.handler = this.getInstance(ADInvoiceIssuingHandler.class);

        try {
            ADInvoiceEntity invoice = this.newInvoice(INVOICE_TYPE.FT);

            this.createSeries(invoice, DEFAULT_SERIES);

            this.issueNewInvoice(this.handler, invoice, this.DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();
        } catch (DocumentIssuingException | DocumentSeriesDoesNotExistException | SeriesUniqueCodeNotFilled e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testIssuedInvoiceSimple() {
        ADInvoice issuedInvoice = this.getInstance(DAOADInvoice.class).get(this.issuedInvoiceUID);

        Assertions.assertEquals(this.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
    }

    @Test
    public void testIssuedInvoiceSameSeries()
            throws DocumentIssuingException, DocumentSeriesDoesNotExistException, SeriesUniqueCodeNotFilled {
        ADInvoice issuedInvoice = this.getInstance(DAOADInvoice.class).get(this.issuedInvoiceUID);
        Integer nextNumber = 2;

        ADInvoiceEntity newInvoice = this.newInvoice(INVOICE_TYPE.FT);

        StringID<GenericInvoice> newInvoiceUID = newInvoice.getUID();
        newInvoice.setBusiness(issuedInvoice.getBusiness());

        this.issueNewInvoice(this.handler, newInvoice, this.DEFAULT_SERIES);

        ADInvoice lastInvoice = this.getInstance(DAOADInvoice.class).get(newInvoiceUID);

        Assertions.assertEquals(this.DEFAULT_SERIES, lastInvoice.getSeries());
        Assertions.assertEquals(nextNumber, lastInvoice.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/" + nextNumber;
        Assertions.assertEquals(formatedNumber, lastInvoice.getNumber());
    }

    @Test
    public void testIssuedInvoiceDifferentSeries()
            throws DocumentIssuingException, DocumentSeriesDoesNotExistException, SeriesUniqueCodeNotFilled {
        Integer nextNumber = 1;
        String newSeries = "FT NEW_SERIES";

        ADInvoiceEntity newInvoice = this.newInvoice(INVOICE_TYPE.FT);

        StringID<GenericInvoice> newInvoiceUID = newInvoice.getUID();
        this.createSeries(newInvoice, newSeries);

        this.issueNewInvoice(this.handler, newInvoice, newSeries);

        ADInvoice issuedInvoice = this.getInstance(DAOADInvoice.class).get(newInvoiceUID);

        Assertions.assertEquals(newSeries, issuedInvoice.getSeries());
        Assertions.assertEquals(nextNumber, issuedInvoice.getSeriesNumber());
        String formatedNumber = newSeries + "/" + nextNumber;
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
    }

    @Test
    public void testIssuedInvoiceSameSourceBilling()
            throws DocumentIssuingException, DocumentSeriesDoesNotExistException, SeriesUniqueCodeNotFilled {
        ADInvoiceEntity newInvoice = this.newInvoice(INVOICE_TYPE.FT);

        StringID<GenericInvoice> newInvoiceUID = newInvoice.getUID();

        this.createSeries(newInvoice, this.DEFAULT_SERIES);

        this.issueNewInvoice(this.handler, newInvoice, this.DEFAULT_SERIES);

        this.getInstance(DAOADInvoice.class).get(newInvoiceUID);
    }

    @Test
    public void testSeriesDoesNotExist() {
        ADInvoiceEntity invoiceEntity = this.newInvoice(INVOICE_TYPE.FT);

        Assertions.assertThrows(DocumentSeriesDoesNotExistException.class,
                () -> this.issueNewInvoice(this.handler, invoiceEntity, "A RANDOM SERIES"));
    }
}
