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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.exceptions.BillySimpleInvoiceException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTSimpleInvoiceTestUtil;

public class TestPTSimpleInvoiceIssuingHandler extends PTDocumentAbstractTest {

    private static final TYPE DEFAULT_TYPE = TYPE.FS;
    private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

    private PTSimpleInvoiceIssuingHandler handler;
    private UID issuedInvoiceUID;

    @BeforeEach
    public void setUpNewSimpleInvoice() {
        this.handler = this.getInstance(PTSimpleInvoiceIssuingHandler.class);

        try {
            PTSimpleInvoiceEntity invoice = this.newInvoice(TestPTSimpleInvoiceIssuingHandler.DEFAULT_TYPE,
                    TestPTSimpleInvoiceIssuingHandler.SOURCE_BILLING);

            this.issueNewInvoice(this.handler, invoice, PTPersistencyAbstractTest.DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();
        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssuedInvoiceSimple() throws DocumentIssuingException {
        PTSimpleInvoice issuedInvoice = this.getInstance(DAOPTSimpleInvoice.class).get(this.issuedInvoiceUID);

        Assertions.assertEquals(PTPersistencyAbstractTest.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber =
                TestPTSimpleInvoiceIssuingHandler.DEFAULT_TYPE + " " + PTPersistencyAbstractTest.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
        Assertions.assertEquals(TestPTSimpleInvoiceIssuingHandler.SOURCE_BILLING, issuedInvoice.getSourceBilling());
    }

    @Test
    public void testBusinessSimpleInvoice() {
        PTSimpleInvoiceTestUtil simpleInvoiceTestUtil = new PTSimpleInvoiceTestUtil(PTAbstractTest.injector);

        Assertions.assertThrows(BillySimpleInvoiceException.class, () -> simpleInvoiceTestUtil
                .getSimpleInvoiceEntity(TestPTSimpleInvoiceIssuingHandler.SOURCE_BILLING, CLIENTTYPE.BUSINESS));
    }

}
