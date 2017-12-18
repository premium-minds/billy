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
package com.premiumminds.billy.france.test.services.documents.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.documents.FRInvoiceIssuingHandler;
import com.premiumminds.billy.france.services.entities.FRInvoice;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.services.documents.FRDocumentAbstractTest;

public class TestFRInvoiceIssuingHandler extends FRDocumentAbstractTest {

    private FRInvoiceIssuingHandler handler;
    private UID issuedInvoiceUID;

    private String DEFAULT_SERIES = INVOICE_TYPE.FT + " " + FRPersistencyAbstractTest.DEFAULT_SERIES;

    @Before
    public void setUpNewInvoice() {
        this.handler = this.getInstance(FRInvoiceIssuingHandler.class);

        try {
            FRInvoiceEntity invoice = this.newInvoice(INVOICE_TYPE.FT);

            this.issueNewInvoice(this.handler, invoice, this.DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();
        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testIssuedInvoiceSimple() throws DocumentIssuingException {
        FRInvoice issuedInvoice = this.getInstance(DAOFRInvoice.class).get(this.issuedInvoiceUID);

        Assert.assertEquals(this.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assert.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/1";
        Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());
    }

    @Test
    public void testIssuedInvoiceSameSeries() throws DocumentIssuingException {
        FRInvoice issuedInvoice = this.getInstance(DAOFRInvoice.class).get(this.issuedInvoiceUID);
        Integer nextNumber = 2;

        FRInvoiceEntity newInvoice = this.newInvoice(INVOICE_TYPE.FT);

        UID newInvoiceUID = newInvoice.getUID();
        newInvoice.setBusiness(issuedInvoice.getBusiness());

        this.issueNewInvoice(this.handler, newInvoice, this.DEFAULT_SERIES);

        FRInvoice lastInvoice = this.getInstance(DAOFRInvoice.class).get(newInvoiceUID);

        Assert.assertEquals(this.DEFAULT_SERIES, lastInvoice.getSeries());
        Assert.assertEquals(nextNumber, lastInvoice.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/" + nextNumber;
        Assert.assertEquals(formatedNumber, lastInvoice.getNumber());
    }

    @Test
    public void testIssuedInvoiceDifferentSeries() throws DocumentIssuingException {
        Integer nextNumber = 1;
        String newSeries = "FT NEW_SERIES";

        FRInvoiceEntity newInvoice = this.newInvoice(INVOICE_TYPE.FT);

        UID newInvoiceUID = newInvoice.getUID();

        this.issueNewInvoice(this.handler, newInvoice, newSeries);

        FRInvoice issuedInvoice = this.getInstance(DAOFRInvoice.class).get(newInvoiceUID);

        Assert.assertEquals(newSeries, issuedInvoice.getSeries());
        Assert.assertEquals(nextNumber, issuedInvoice.getSeriesNumber());
        String formatedNumber = newSeries + "/" + nextNumber;
        Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());
    }

    @Test
    public void testIssuedInvoiceSameSourceBilling() throws DocumentIssuingException {
        FRInvoiceEntity newInvoice = this.newInvoice(INVOICE_TYPE.FT);

        UID newInvoiceUID = newInvoice.getUID();

        this.issueNewInvoice(this.handler, newInvoice, this.DEFAULT_SERIES);

        this.getInstance(DAOFRInvoice.class).get(newInvoiceUID);
    }

}
