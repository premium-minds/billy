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
import com.premiumminds.billy.france.exceptions.BillySimpleInvoiceException;
import com.premiumminds.billy.france.persistence.dao.DAOFRSimpleInvoice;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.documents.FRSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.services.documents.FRDocumentAbstractTest;
import com.premiumminds.billy.france.test.util.FRSimpleInvoiceTestUtil;

public class TestFRSimpleInvoiceIssuingHandler extends FRDocumentAbstractTest {

    private String DEFAULT_SERIES = INVOICE_TYPE.FS + " " + FRPersistencyAbstractTest.DEFAULT_SERIES;

    private FRSimpleInvoiceIssuingHandler handler;
    private UID issuedInvoiceUID;

    @Before
    public void setUpNewSimpleInvoice() {
        this.handler = this.getInstance(FRSimpleInvoiceIssuingHandler.class);

        try {
            FRSimpleInvoiceEntity invoice = this.newInvoice(INVOICE_TYPE.FS, SOURCE_BILLING.APPLICATION);

            this.issueNewInvoice(this.handler, invoice, this.DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();
        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssuedInvoiceSimple() throws DocumentIssuingException {
        FRSimpleInvoice issuedInvoice = this.getInstance(DAOFRSimpleInvoice.class).get(this.issuedInvoiceUID);

        Assert.assertEquals(this.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assert.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/1";
        Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());
    }

    @Test(expected = BillySimpleInvoiceException.class)
    public void testBusinessSimpleInvoice() {
        new FRSimpleInvoiceTestUtil(FRAbstractTest.injector).getSimpleInvoiceEntity(CLIENTTYPE.BUSINESS);
    }

}
