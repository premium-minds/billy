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
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTReceiptInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTReceiptInvoice;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPTReceiptInvoiceIssuingHandler extends PTDocumentAbstractTest {

    private static final TYPE DEFAULT_TYPE = TYPE.FR;
    private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

    private PTReceiptInvoiceIssuingHandler handler;
    private StringID<GenericInvoice> issuedInvoiceUID;

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

}
