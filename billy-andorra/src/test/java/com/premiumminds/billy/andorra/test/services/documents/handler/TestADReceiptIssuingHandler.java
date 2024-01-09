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

import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.services.documents.ADReceiptIssuingHandler;
import com.premiumminds.billy.andorra.services.entities.ADReceipt;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.andorra.test.services.documents.ADDocumentAbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestADReceiptIssuingHandler extends ADDocumentAbstractTest {

    private ADReceiptIssuingHandler handler;
    private StringID<GenericInvoice> issuedReceiptUID;

    private String DEFAULT_SERIES = INVOICE_TYPE.RC + " " + ADPersistencyAbstractTest.DEFAULT_SERIES;

    @BeforeEach
    public void setUpNewReceipt() {
        this.handler = this.getInstance(ADReceiptIssuingHandler.class);

        ADReceiptEntity receipt = this.newInvoice(INVOICE_TYPE.RC);
        try {
            this.createSeries(receipt, this.DEFAULT_SERIES);

            this.issueNewInvoice(this.handler, receipt, this.DEFAULT_SERIES);

            this.issuedReceiptUID = receipt.getUID();
        } catch (DocumentIssuingException | DocumentSeriesDoesNotExistException | SeriesUniqueCodeNotFilled e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIssueReceipt() {
        ADReceipt issuedReceipt = this.getInstance(DAOADReceipt.class).get(this.issuedReceiptUID);

        Assertions.assertEquals(this.DEFAULT_SERIES, issuedReceipt.getSeries());
        Assertions.assertTrue(1 == issuedReceipt.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedReceipt.getNumber());
    }
}
