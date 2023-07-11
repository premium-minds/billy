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

import com.premiumminds.billy.andorra.persistence.dao.DAOADSimpleInvoice;
import com.premiumminds.billy.andorra.persistence.entities.ADSimpleInvoiceEntity;
import com.premiumminds.billy.andorra.services.documents.ADSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import com.premiumminds.billy.andorra.test.services.documents.ADDocumentAbstractTest;
import com.premiumminds.billy.andorra.test.util.ADSimpleInvoiceTestUtil;
import com.premiumminds.billy.core.exceptions.InvalidAmountForDocumentTypeException;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestADSimpleInvoiceIssuingHandler extends ADDocumentAbstractTest {

    private static final String DEFAULT_SERIES = INVOICE_TYPE.FS + " " + ADPersistencyAbstractTest.DEFAULT_SERIES;

    private ADSimpleInvoiceIssuingHandler handler;
    private StringID<GenericInvoice> issuedInvoiceUID;

    @BeforeEach
    public void setUpNewSimpleInvoice() {
        this.handler = this.getInstance(ADSimpleInvoiceIssuingHandler.class);

        try {
            ADSimpleInvoiceEntity invoice = this.newInvoice(INVOICE_TYPE.FS, SOURCE_BILLING.APPLICATION);

            this.createSeries(invoice, DEFAULT_SERIES);

            this.issueNewInvoice(this.handler, invoice, DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();
        } catch (DocumentIssuingException | DocumentSeriesDoesNotExistException | SeriesUniqueCodeNotFilled e) {
            e.printStackTrace();
        }
    }

    @Test
    void testIssuedInvoiceSimple() {
        ADSimpleInvoice issuedInvoice = this.getInstance(DAOADSimpleInvoice.class).get(this.issuedInvoiceUID);

        Assertions.assertEquals(DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertEquals(1, issuedInvoice.getSeriesNumber());
        String formatedNumber = DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
    }

    @Test
    void testCustomerOverMaxAmountSimpleInvoice() {
        ADSimpleInvoiceTestUtil simpleInvoiceTestUtil = new ADSimpleInvoiceTestUtil(ADAbstractTest.injector);

        Assertions.assertThrows(
            InvalidAmountForDocumentTypeException.class,
            simpleInvoiceTestUtil::getSimpleInvoiceEntityOverMaxForCustomer);
    }

    @Test
    void testBusinessSimpleInvoice() {
        ADSimpleInvoiceTestUtil simpleInvoiceTestUtil = new ADSimpleInvoiceTestUtil(ADAbstractTest.injector);

        Assertions.assertThrows(
            InvalidAmountForDocumentTypeException.class,
            () -> simpleInvoiceTestUtil.getSimpleInvoiceEntity(CLIENTTYPE.BUSINESS));
    }

}
