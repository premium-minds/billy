/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.documents.handler;

import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTReceiptInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTReceiptInvoice;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;

public class TestPTReceiptInvoiceIssuingHandler extends PTDocumentAbstractTest {

    private static final TYPE DEFAULT_TYPE = TYPE.FR;
    private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

    private PTReceiptInvoiceIssuingHandler handler;
    private UID issuedInvoiceUID;

    @BeforeEach
    public void setUpNewInvoice() {
        this.handler = this.getInstance(PTReceiptInvoiceIssuingHandler.class);

        try {
            PTReceiptInvoiceEntity invoice = this.newInvoice(TestPTReceiptInvoiceIssuingHandler.DEFAULT_TYPE,
                    TestPTReceiptInvoiceIssuingHandler.SOURCE_BILLING);
            this.createSeries(invoice, PTPersistencyAbstractTest.DEFAULT_SERIES);

            this.issueNewInvoice(this.handler, invoice, PTPersistencyAbstractTest.DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();
        } catch (DocumentIssuingException | DocumentSeriesDoesNotExistException | SeriesUniqueCodeNotFilled e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testIssuedInvoiceSimple() {
        PTReceiptInvoice issuedInvoice = this.getInstance(DAOPTReceiptInvoice.class).get(this.issuedInvoiceUID);

        Assertions.assertEquals(PTPersistencyAbstractTest.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber =
                TestPTReceiptInvoiceIssuingHandler.DEFAULT_TYPE + " " + PTPersistencyAbstractTest.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
        Assertions.assertEquals(TestPTReceiptInvoiceIssuingHandler.SOURCE_BILLING, issuedInvoice.getSourceBilling());
    }

    // @Test
    // public void testIssuedInvoiceSameSeries() throws DocumentIssuingException {
    // PTInvoice issuedInvoice = (PTInvoice) this.getInstance(
    // DAOPTInvoice.class).get(this.issuedInvoiceUID);
    // Integer nextNumber = 2;
    //
    // PTGenericInvoiceEntity newInvoice = this.newInvoice(
    // TestPTReceiptInvoiceIssuingHandler.DEFAULT_TYPE,
    // TestPTReceiptInvoiceIssuingHandler.SOURCE_BILLING);
    //
    // UID newInvoiceUID = newInvoice.getUID();
    // newInvoice.setBusiness(issuedInvoice.getBusiness());
    //
    // this.issueNewInvoice(this.handler, newInvoice,
    // PTPersistencyAbstractTest.DEFAULT_SERIES);
    //
    // PTInvoice lastInvoice = (PTInvoice) this
    // .getInstance(DAOPTInvoice.class).get(newInvoiceUID);
    //
    // Assertions.assertEquals(PTPersistencyAbstractTest.DEFAULT_SERIES,
    // lastInvoice.getSeries());
    // Assertions.assertEquals(nextNumber, lastInvoice.getSeriesNumber());
    // String formatedNumber = TestPTReceiptInvoiceIssuingHandler.DEFAULT_TYPE
    // + " " + PTPersistencyAbstractTest.DEFAULT_SERIES + "/"
    // + nextNumber;
    // Assertions.assertEquals(formatedNumber, lastInvoice.getNumber());
    // }
    //
    // @Test
    // public void testIssuedInvoiceDifferentSeries()
    // throws DocumentIssuingException {
    // Integer nextNumber = 1;
    // String newSeries = "NEW_SERIES";
    //
    // PTGenericInvoiceEntity newInvoice = this.newInvoice(
    // TestPTReceiptInvoiceIssuingHandler.DEFAULT_TYPE,
    // TestPTReceiptInvoiceIssuingHandler.SOURCE_BILLING);
    //
    // UID newInvoiceUID = newInvoice.getUID();
    //
    // this.issueNewInvoice(this.handler, newInvoice, newSeries);
    //
    // PTInvoice issuedInvoice = (PTInvoice) this.getInstance(
    // DAOPTInvoice.class).get(newInvoiceUID);
    //
    // Assertions.assertEquals(newSeries, issuedInvoice.getSeries());
    // Assertions.assertEquals(nextNumber, issuedInvoice.getSeriesNumber());
    // String formatedNumber = TestPTReceiptInvoiceIssuingHandler.DEFAULT_TYPE
    // + " " + newSeries + "/" + nextNumber;
    // Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
    // }
    //
    // /**
    // * Test issue of invoice of different type in same series
    // *
    // * @throws DocumentIssuingException
    // */
    // @Test(expected = InvalidInvoiceTypeException.class)
    // public void testIssuedInvoiceFailure() throws DocumentIssuingException {
    // String series = "NEW_SERIES";
    //
    // PTGenericInvoiceEntity invoice = this.newInvoice(
    // TestPTReceiptInvoiceIssuingHandler.DEFAULT_TYPE,
    // TestPTReceiptInvoiceIssuingHandler.SOURCE_BILLING);
    //
    // this.issueNewInvoice(this.handler, invoice, series);
    //
    // PTSimpleInvoiceIssuingHandler newHandler = this
    // .getInstance(PTSimpleInvoiceIssuingHandler.class);
    //
    // PTGenericInvoiceEntity diffentTypeInvoice = this.newInvoice(TYPE.FS,
    // TestPTReceiptInvoiceIssuingHandler.SOURCE_BILLING);
    // diffentTypeInvoice.setBusiness(invoice.getBusiness());
    //
    // this.issueNewInvoice(newHandler, diffentTypeInvoice, series);
    // }
    //
    // @Test(expected = InvalidInvoiceDateException.class)
    // public void testIssuedInvoiceBeforeDate() throws DocumentIssuingException {
    // this.issueNewInvoice(this.handler, this.newInvoice(
    // TestPTReceiptInvoiceIssuingHandler.DEFAULT_TYPE,
    // TestPTReceiptInvoiceIssuingHandler.SOURCE_BILLING),
    // PTPersistencyAbstractTest.DEFAULT_SERIES, new Date(0));
    // }
    //
    // @Test
    // public void testIssuedInvoiceSameSourceBilling()
    // throws DocumentIssuingException {
    // PTGenericInvoiceEntity newInvoice = this.newInvoice(
    // TestPTReceiptInvoiceIssuingHandler.DEFAULT_TYPE,
    // TestPTReceiptInvoiceIssuingHandler.SOURCE_BILLING);
    //
    // UID newInvoiceUID = newInvoice.getUID();
    //
    // this.issueNewInvoice(this.handler, newInvoice,
    // PTPersistencyAbstractTest.DEFAULT_SERIES);
    //
    // PTInvoice issuedInvoice = (PTInvoice) this.getInstance(
    // DAOPTInvoice.class).get(newInvoiceUID);
    //
    // Assertions.assertEquals(TestPTReceiptInvoiceIssuingHandler.SOURCE_BILLING,
    // issuedInvoice.getSourceBilling());
    // }

}
