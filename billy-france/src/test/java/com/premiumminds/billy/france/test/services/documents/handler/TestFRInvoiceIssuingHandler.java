/*
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
package com.premiumminds.billy.france.test.services.documents.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.documents.FRInvoiceIssuingHandler;
import com.premiumminds.billy.france.services.entities.FRInvoice;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.services.documents.FRDocumentAbstractTest;

public class TestFRInvoiceIssuingHandler extends FRDocumentAbstractTest {

    private FRInvoiceIssuingHandler handler;
    private StringID<GenericInvoice> issuedInvoiceUID;

    private String DEFAULT_SERIES = INVOICE_TYPE.FT + " " + FRPersistencyAbstractTest.DEFAULT_SERIES;

    @BeforeEach
    public void setUpNewInvoice() {
        this.handler = this.getInstance(FRInvoiceIssuingHandler.class);

        try {
            FRInvoiceEntity invoice = this.newInvoice(INVOICE_TYPE.FT);

            this.createSeries(invoice, this.DEFAULT_SERIES);

            this.issueNewInvoice(this.handler, invoice, this.DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();

        } catch (DocumentIssuingException | DocumentSeriesDoesNotExistException | SeriesUniqueCodeNotFilled e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testIssuedInvoiceSimple() {
        FRInvoice issuedInvoice = this.getInstance(DAOFRInvoice.class).get(this.issuedInvoiceUID);

        Assertions.assertEquals(this.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
    }

    @Test
    public void testIssuedInvoiceSameSeries()
            throws DocumentIssuingException, DocumentSeriesDoesNotExistException, SeriesUniqueCodeNotFilled {
        FRInvoice issuedInvoice = this.getInstance(DAOFRInvoice.class).get(this.issuedInvoiceUID);
        Integer nextNumber = 2;

        FRInvoiceEntity newInvoice = this.newInvoice(INVOICE_TYPE.FT);

        StringID<GenericInvoice> newInvoiceUID = newInvoice.getUID();
        newInvoice.setBusiness(issuedInvoice.getBusiness());

        this.issueNewInvoice(this.handler, newInvoice, this.DEFAULT_SERIES);

        FRInvoice lastInvoice = this.getInstance(DAOFRInvoice.class).get(newInvoiceUID);

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

        FRInvoiceEntity newInvoice = this.newInvoice(INVOICE_TYPE.FT);

        StringID<GenericInvoice> newInvoiceUID = newInvoice.getUID();
        this.createSeries(newInvoice, newSeries);

        this.issueNewInvoice(this.handler, newInvoice, newSeries);

        FRInvoice issuedInvoice = this.getInstance(DAOFRInvoice.class).get(newInvoiceUID);

        Assertions.assertEquals(newSeries, issuedInvoice.getSeries());
        Assertions.assertEquals(nextNumber, issuedInvoice.getSeriesNumber());
        String formatedNumber = newSeries + "/" + nextNumber;
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
    }

    @Test
    public void testIssuedInvoiceSameSourceBilling()
            throws DocumentIssuingException, DocumentSeriesDoesNotExistException, SeriesUniqueCodeNotFilled {
        FRInvoiceEntity newInvoice = this.newInvoice(INVOICE_TYPE.FT);

        StringID<GenericInvoice> newInvoiceUID = newInvoice.getUID();
        this.createSeries(newInvoice, this.DEFAULT_SERIES);

        this.issueNewInvoice(this.handler, newInvoice, this.DEFAULT_SERIES);

        this.getInstance(DAOFRInvoice.class).get(newInvoiceUID);
    }

    @Test
    public void testSeriesDoesNotExist() {
        FRInvoiceEntity invoiceEntity = this.newInvoice(INVOICE_TYPE.FT);

        Assertions.assertThrows(DocumentSeriesDoesNotExistException.class,
                () -> this.issueNewInvoice(this.handler, invoiceEntity, "A RANDOM SERIES"));
    }

}
