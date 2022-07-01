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
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.PTSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceTypeException;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;

public class TestPTInvoiceIssuingHandler extends PTDocumentAbstractTest {

    private static final TYPE DEFAULT_TYPE = TYPE.FT;
    private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

    private PTInvoiceIssuingHandler handler;
    private UID issuedInvoiceUID;

    @BeforeEach
    public void setUpNewInvoice() {
        this.handler = this.getInstance(PTInvoiceIssuingHandler.class);

        try {
            PTInvoiceEntity invoice = this.newInvoice(TestPTInvoiceIssuingHandler.DEFAULT_TYPE,
                    TestPTInvoiceIssuingHandler.SOURCE_BILLING);
			this.createSeries(invoice, PTPersistencyAbstractTest.DEFAULT_SERIES);

            this.issueNewInvoice(this.handler, invoice, PTPersistencyAbstractTest.DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testIssuedInvoiceSimple() throws DocumentIssuingException {
        PTInvoice issuedInvoice = this.getInstance(DAOPTInvoice.class).get(this.issuedInvoiceUID);

        Assertions.assertEquals(PTPersistencyAbstractTest.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber =
                TestPTInvoiceIssuingHandler.DEFAULT_TYPE + " " + PTPersistencyAbstractTest.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
        Assertions.assertEquals(TestPTInvoiceIssuingHandler.SOURCE_BILLING, issuedInvoice.getSourceBilling());
    }

    @Test
    public void testIssuedInvoiceSameSeries() throws DocumentIssuingException, SeriesUniqueCodeNotFilled {
        PTInvoice issuedInvoice = this.getInstance(DAOPTInvoice.class).get(this.issuedInvoiceUID);
        Integer nextNumber = 2;

        PTGenericInvoiceEntity newInvoice =
                this.newInvoice(TestPTInvoiceIssuingHandler.DEFAULT_TYPE, TestPTInvoiceIssuingHandler.SOURCE_BILLING);

        UID newInvoiceUID = newInvoice.getUID();
        newInvoice.setBusiness(issuedInvoice.getBusiness());

        this.issueNewInvoice(this.handler, newInvoice, PTPersistencyAbstractTest.DEFAULT_SERIES);

        PTInvoice lastInvoice = this.getInstance(DAOPTInvoice.class).get(newInvoiceUID);

        Assertions.assertEquals(PTPersistencyAbstractTest.DEFAULT_SERIES, lastInvoice.getSeries());
        Assertions.assertEquals(nextNumber, lastInvoice.getSeriesNumber());
        String formatedNumber = TestPTInvoiceIssuingHandler.DEFAULT_TYPE + " " +
                PTPersistencyAbstractTest.DEFAULT_SERIES + "/" + nextNumber;
        Assertions.assertEquals(formatedNumber, lastInvoice.getNumber());
    }

    @Test
    public void testIssuedInvoiceDifferentSeries() throws DocumentIssuingException, SeriesUniqueCodeNotFilled {
        Integer nextNumber = 1;
        String newSeries = "NEWSERIES";

        PTGenericInvoiceEntity newInvoice =
                this.newInvoice(TestPTInvoiceIssuingHandler.DEFAULT_TYPE, TestPTInvoiceIssuingHandler.SOURCE_BILLING);

        UID newInvoiceUID = newInvoice.getUID();
		this.createSeries(newInvoice, newSeries);

        this.issueNewInvoice(this.handler, newInvoice, newSeries);

        PTInvoice issuedInvoice = this.getInstance(DAOPTInvoice.class).get(newInvoiceUID);

        Assertions.assertEquals(newSeries, issuedInvoice.getSeries());
        Assertions.assertEquals(nextNumber, issuedInvoice.getSeriesNumber());
        String formatedNumber = TestPTInvoiceIssuingHandler.DEFAULT_TYPE + " " + newSeries + "/" + nextNumber;
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
    }

    /**
     * Test issue of invoice of different type in same series
     *
     * @throws DocumentIssuingException
     */
    @Test
    public void testIssuedInvoiceFailure() throws DocumentIssuingException, SeriesUniqueCodeNotFilled {
        String series = "NEWSERIES";

        PTGenericInvoiceEntity invoice =
                this.newInvoice(TestPTInvoiceIssuingHandler.DEFAULT_TYPE, TestPTInvoiceIssuingHandler.SOURCE_BILLING);

		this.createSeries(invoice, series);
		this.issueNewInvoice(this.handler, invoice, series);

        PTSimpleInvoiceIssuingHandler newHandler = this.getInstance(PTSimpleInvoiceIssuingHandler.class);

        PTGenericInvoiceEntity diffentTypeInvoice =
                this.newInvoice(TYPE.FS, TestPTInvoiceIssuingHandler.SOURCE_BILLING);
        diffentTypeInvoice.setBusiness(invoice.getBusiness());

        Assertions.assertThrows(InvalidInvoiceTypeException.class, () -> this.issueNewInvoice(newHandler, diffentTypeInvoice, series));
    }

	@Test
	public void testIssuedInvoiceFailureWithInvalidSeriesAndInvoiceNumber() throws DocumentIssuingException {
		String series = "NEW_SERIES";

		PTGenericInvoiceEntity invoice =
			this.newInvoice(TestPTInvoiceIssuingHandler.DEFAULT_TYPE, TestPTInvoiceIssuingHandler.SOURCE_BILLING);

		Assertions.assertThrows(DocumentIssuingException.class, () -> this.issueNewInvoice(this.handler, invoice, series));
	}

    @Test
    public void testIssuedInvoiceSameSourceBilling() throws DocumentIssuingException, SeriesUniqueCodeNotFilled {
        PTGenericInvoiceEntity newInvoice =
                this.newInvoice(TestPTInvoiceIssuingHandler.DEFAULT_TYPE, TestPTInvoiceIssuingHandler.SOURCE_BILLING);

        UID newInvoiceUID = newInvoice.getUID();
		this.createSeries(newInvoice, PTPersistencyAbstractTest.DEFAULT_SERIES);

        this.issueNewInvoice(this.handler, newInvoice, PTPersistencyAbstractTest.DEFAULT_SERIES);

        PTInvoice issuedInvoice = this.getInstance(DAOPTInvoice.class).get(newInvoiceUID);

        Assertions.assertEquals(TestPTInvoiceIssuingHandler.SOURCE_BILLING, issuedInvoice.getSourceBilling());
    }

	@Test
	public void testIssuedInvoiceFailWhenSeriesHasNoUniqueCode() {
		PTGenericInvoiceEntity newInvoice =
			this.newInvoice(TestPTInvoiceIssuingHandler.DEFAULT_TYPE, TestPTInvoiceIssuingHandler.SOURCE_BILLING);

		this.createSeries(newInvoice, PTPersistencyAbstractTest.DEFAULT_SERIES, Optional.empty());

		Assertions.assertThrows(SeriesUniqueCodeNotFilled.class,
								() -> this.issueNewInvoice(this.handler, newInvoice, PTPersistencyAbstractTest.DEFAULT_SERIES));
	}

}
