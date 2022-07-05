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

import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.documents.ESReceiptIssuingHandler;
import com.premiumminds.billy.spain.services.entities.ESReceipt;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.services.documents.ESDocumentAbstractTest;

public class TestESReceiptIssuingHandler extends ESDocumentAbstractTest {

    private ESReceiptIssuingHandler handler;
    private UID issuedReceiptUID;

    private String DEFAULT_SERIES = INVOICE_TYPE.RC + " " + ESPersistencyAbstractTest.DEFAULT_SERIES;

    @BeforeEach
    public void setUpNewReceipt() {
        this.handler = this.getInstance(ESReceiptIssuingHandler.class);

        ESReceiptEntity receipt = this.newInvoice(INVOICE_TYPE.RC);
        try {
            this.createSeries(receipt, this.DEFAULT_SERIES);

            this.issueNewInvoice(this.handler, receipt, this.DEFAULT_SERIES);

            this.issuedReceiptUID = receipt.getUID();
        } catch (DocumentIssuingException | DocumentSeriesDoesNotExistException | SeriesUniqueCodeNotFilled e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssueReceipt() {
        ESReceipt issuedReceipt = this.getInstance(DAOESReceipt.class).get(this.issuedReceiptUID);

        Assertions.assertEquals(this.DEFAULT_SERIES, issuedReceipt.getSeries());
        Assertions.assertTrue(1 == issuedReceipt.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedReceipt.getNumber());
    }
}
