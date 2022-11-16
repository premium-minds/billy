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
package com.premiumminds.billy.portugal.test.services.export.qrcode;

import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.persistence.entities.jpa.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeStringGenerator;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestQRCodeStringGenerator extends PTDocumentAbstractTest {

    private static final TYPE DEFAULT_TYPE = TYPE.FT;
    private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

    private PTInvoiceIssuingHandler handler;
    private StringID<GenericInvoice> issuedInvoiceUID;
    private QRCodeStringGenerator underTest;
    private DAOInvoiceSeries daoInvoiceSeries;

    @BeforeEach
    public void setUp() {
        this.handler = this.getInstance(PTInvoiceIssuingHandler.class);
        this.daoInvoiceSeries = this.getInstance(DAOInvoiceSeries.class);
        this.underTest = new QRCodeStringGenerator();
    }

    @Test
    public void testGenerateQRCodeData() throws SeriesUniqueCodeNotFilled {
        generateInvoice();
        final PTInvoice document = this.getInstance(DAOPTInvoice.class).get(this.issuedInvoiceUID);

        String result = null;
        try {
            result = underTest.generateQRCodeData(document);
        } catch (RequiredFieldNotFoundException e) {
            Assertions.fail();
        }

        final LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        final String hash = String.valueOf(document.getHash().charAt(0)) + document.getHash().charAt(10) +
            document.getHash().charAt(20) + document.getHash().charAt(30);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(
            "A:123456789*B:123456789*C:PT*D:FT*E:N*F:"
                + now.format(formatter)
                + "*G:FT DEFAULT/1*H:CCCC2345-1*I1:PT*I7:0.37*I8:0.08*N:0.08*O:0.45*Q:"
                + hash
                + "*R:1",
            result
            );

    }

    private void generateInvoice() throws SeriesUniqueCodeNotFilled {
        try {
            PTInvoiceEntity invoice = this.newInvoice(DEFAULT_TYPE,
                                                      SOURCE_BILLING);
            InvoiceSeriesEntity entity = new JPAInvoiceSeriesEntity();
            entity.setBusiness(invoice.getBusiness());
            entity.setSeries(PTPersistencyAbstractTest.DEFAULT_SERIES);
            entity.setSeriesUniqueCode("CCCC2345");
            daoInvoiceSeries.create(entity);
            this.issueNewInvoice(this.handler, invoice, PTPersistencyAbstractTest.DEFAULT_SERIES);
            this.issuedInvoiceUID = invoice.getUID();
        } catch (DocumentIssuingException | DocumentSeriesDoesNotExistException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
