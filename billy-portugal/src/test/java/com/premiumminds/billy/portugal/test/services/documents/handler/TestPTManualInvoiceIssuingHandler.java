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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidSourceBillingException;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPTManualInvoiceIssuingHandler extends PTDocumentAbstractTest {

    private static final TYPE DEFAULT_TYPE = TYPE.FT;
    private static final SourceBilling SOURCE_BILLING = SourceBilling.M;

    private PTInvoiceIssuingHandler handler;
    private StringID<GenericInvoice> issuedInvoiceUID;

    @BeforeEach
    public void setUpNewManualInvoice() {
        this.handler = this.getInstance(PTInvoiceIssuingHandler.class);

        try {
            PTInvoiceEntity invoice = this.newInvoice(TestPTManualInvoiceIssuingHandler.DEFAULT_TYPE,
                    TestPTManualInvoiceIssuingHandler.SOURCE_BILLING);
            this.createSeries(invoice, PTPersistencyAbstractTest.DEFAULT_SERIES);

            this.issueNewInvoice(this.handler, invoice, PTPersistencyAbstractTest.DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();
        } catch (DocumentIssuingException | DocumentSeriesDoesNotExistException | SeriesUniqueCodeNotFilled e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIssuedManualInvoiceSimple() {
        PTInvoiceEntity issuedInvoice = this.getInstance(DAOPTInvoice.class).get(this.issuedInvoiceUID);

        Assertions.assertEquals(PTPersistencyAbstractTest.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber =
                TestPTManualInvoiceIssuingHandler.DEFAULT_TYPE + " " + PTPersistencyAbstractTest.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
        Assertions.assertEquals(TestPTManualInvoiceIssuingHandler.SOURCE_BILLING, issuedInvoice.getSourceBilling());
    }

    /**
     * Test the issue of a normal invoice in a manual series.
     *
     */
    @Test
    public void testDifferentBilling() {
        PTInvoiceEntity issuedInvoice = this.getInstance(DAOPTInvoice.class).get(this.issuedInvoiceUID);

        PTInvoiceEntity normalInvoice =
                this.newInvoice(TestPTManualInvoiceIssuingHandler.DEFAULT_TYPE, SourceBilling.P);
        normalInvoice.setBusiness(issuedInvoice.getBusiness());

        Assertions.assertThrows(InvalidSourceBillingException.class, () ->  this.issueNewInvoice(this.handler, normalInvoice, PTPersistencyAbstractTest.DEFAULT_SERIES));
    }
}
