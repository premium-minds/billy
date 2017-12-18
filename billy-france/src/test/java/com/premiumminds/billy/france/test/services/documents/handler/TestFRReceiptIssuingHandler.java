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
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.documents.FRReceiptIssuingHandler;
import com.premiumminds.billy.france.services.entities.FRReceipt;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.services.documents.FRDocumentAbstractTest;

public class TestFRReceiptIssuingHandler extends FRDocumentAbstractTest {

    private FRReceiptIssuingHandler handler;
    private UID issuedReceiptUID;

    private String DEFAULT_SERIES = INVOICE_TYPE.RC + " " + FRPersistencyAbstractTest.DEFAULT_SERIES;

    @Before
    public void setUpNewReceipt() {
        this.handler = this.getInstance(FRReceiptIssuingHandler.class);

        FRReceiptEntity receipt = this.newInvoice(INVOICE_TYPE.RC);
        try {
            this.issueNewInvoice(this.handler, receipt, this.DEFAULT_SERIES);
            this.issuedReceiptUID = receipt.getUID();
        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssueReceipt() {
        FRReceipt issuedReceipt = this.getInstance(DAOFRReceipt.class).get(this.issuedReceiptUID);

        Assert.assertEquals(this.DEFAULT_SERIES, issuedReceipt.getSeries());
        Assert.assertTrue(1 == issuedReceipt.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/1";
        Assert.assertEquals(formatedNumber, issuedReceipt.getNumber());
    }
}
