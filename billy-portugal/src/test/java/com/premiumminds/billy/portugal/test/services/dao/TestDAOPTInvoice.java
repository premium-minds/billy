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
package com.premiumminds.billy.portugal.test.services.dao;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;

public class TestDAOPTInvoice extends PTPersistencyAbstractTest {

    @Test
    public void testLastInvoiceNumber() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);
        this.getNewIssuedInvoice(B1);
        this.getNewIssuedInvoice(B1);
        PTInvoiceEntity resultInvoice2 = this.getNewIssuedInvoice(B1);
        Assertions.assertEquals(new Integer(3), resultInvoice2.getSeriesNumber());
    }

    @Test
    public void testLastInvoiceNumberWithDifferentBusiness() {
        StringID<Business> B1 = StringID.fromValue("B1");
        StringID<Business> B2 = StringID.fromValue("B2");
        this.createSeries(B1);
        this.createSeries(B2);
        PTInvoiceEntity inv1 = this.getNewIssuedInvoice(B1);
        PTInvoiceEntity inv2 = this.getNewIssuedInvoice(B2);

        PTInvoiceEntity resultInvoice1 = this.getInstance(DAOPTInvoice.class)
                .getLatestInvoiceFromSeries(inv1.getSeries(), inv1.getBusiness().getUID());
        PTInvoiceEntity resultInvoice2 = this.getInstance(DAOPTInvoice.class)
                .getLatestInvoiceFromSeries(inv2.getSeries(), inv2.getBusiness().getUID());

        PTInvoiceEntity inv3 = this.getNewIssuedInvoice(B1);
        PTInvoiceEntity inv4 = this.getNewIssuedInvoice(B2);

        Assertions.assertEquals(inv1.getSeriesNumber(), resultInvoice1.getSeriesNumber());
        Assertions.assertEquals(inv2.getSeriesNumber(), resultInvoice2.getSeriesNumber());
        Assertions.assertEquals(new Integer(2), inv3.getSeriesNumber());
        Assertions.assertEquals(new Integer(2), inv4.getSeriesNumber());
    }

    @Test
    public void testWithNoInvoice() {
        DAOPTInvoice instance = this.getInstance(DAOPTInvoice.class);
        Assertions.assertThrows(BillyRuntimeException.class, () -> instance.getLatestInvoiceFromSeries("NON EXISTING SERIES", StringID.fromValue(UUID.randomUUID().toString())));
    }

    @Test
    public void testInvoiceFromBusiness() {
        this.createSeries(StringID.fromValue("B1"));
        PTInvoiceEntity inv1 = this.getNewIssuedInvoice(StringID.fromValue("B1"));

        PTGenericInvoiceEntity res =
                this.getInstance(DAOPTInvoice.class).findByNumber(inv1.getBusiness().getUID(), inv1.getNumber());

        Assertions.assertEquals(inv1.getUID(), res.getUID());
        Assertions.assertNull(this.getInstance(DAOPTInvoice.class).findByNumber(
            StringID.fromValue("INEXISTENT_BUSINESS"), inv1.getNumber()));
    }

    @Test
    public void testFindCreditNote() {
        this.createSeries(StringID.fromValue("B1"));
        PTInvoiceEntity inv1 = this.getNewIssuedInvoice(StringID.fromValue("B1"));
        PTCreditNote cc1 = this.getNewIssuedCreditnote(inv1);

        List<PTCreditNote> cn1 = this.getInstance(DAOPTCreditNote.class)
                .findByReferencedDocument(cc1.getBusiness().getUID(), inv1.getUID());

        List<PTCreditNote> cn0 = this.getInstance(DAOPTCreditNote.class).findByReferencedDocument(
                StringID.fromValue("B1"),
                StringID.fromValue("leRandomNumbér"));

        Assertions.assertEquals(0, cn0.size());
        Assertions.assertEquals(1, cn1.size());
    }
}
