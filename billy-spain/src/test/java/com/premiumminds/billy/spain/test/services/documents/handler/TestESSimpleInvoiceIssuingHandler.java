/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.test.services.documents.handler;

import com.premiumminds.billy.core.exceptions.InvalidAmountForDocumentTypeException;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.spain.persistence.dao.DAOESSimpleInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESSimpleInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.ESSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.services.documents.ESDocumentAbstractTest;
import com.premiumminds.billy.spain.test.util.ESSimpleInvoiceTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestESSimpleInvoiceIssuingHandler extends ESDocumentAbstractTest {

    private static final String DEFAULT_SERIES = INVOICE_TYPE.FS + " " + ESPersistencyAbstractTest.DEFAULT_SERIES;

    private ESSimpleInvoiceIssuingHandler handler;
    private StringID<GenericInvoice> issuedInvoiceUID;

    @BeforeEach
    public void setUpNewSimpleInvoice() {
        this.handler = this.getInstance(ESSimpleInvoiceIssuingHandler.class);

        try {
            ESSimpleInvoiceEntity invoice = this.newInvoice(INVOICE_TYPE.FS, SOURCE_BILLING.APPLICATION);

            this.createSeries(invoice, DEFAULT_SERIES);

            this.issueNewInvoice(this.handler, invoice, DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();
        } catch (DocumentIssuingException | DocumentSeriesDoesNotExistException | SeriesUniqueCodeNotFilled e) {
            e.printStackTrace();
        }
    }

    @Test
    void testIssuedInvoiceSimple() {
        ESSimpleInvoice issuedInvoice = this.getInstance(DAOESSimpleInvoice.class).get(this.issuedInvoiceUID);

        Assertions.assertEquals(DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertEquals(1, issuedInvoice.getSeriesNumber());
        String formatedNumber = DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
    }

    @Test
    void testCustomerOverMaxAmountSimpleInvoice() {
        ESSimpleInvoiceTestUtil simpleInvoiceTestUtil = new ESSimpleInvoiceTestUtil(ESAbstractTest.injector);

        Assertions.assertThrows(
            InvalidAmountForDocumentTypeException.class,
            simpleInvoiceTestUtil::getSimpleInvoiceEntityOverMaxForCustomer);
    }

    @Test
    void testBusinessSimpleInvoice() {
        ESSimpleInvoiceTestUtil simpleInvoiceTestUtil = new ESSimpleInvoiceTestUtil(ESAbstractTest.injector);

        Assertions.assertThrows(
            InvalidAmountForDocumentTypeException.class,
            () -> simpleInvoiceTestUtil.getSimpleInvoiceEntity(CLIENTTYPE.BUSINESS));
    }

}
