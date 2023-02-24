/*
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
package com.premiumminds.billy.france.test.services.dao;

import java.util.List;
import java.util.UUID;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNote;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.entities.FRGenericInvoiceEntity;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.entities.FRCreditNote;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;

public class TestDAOFRInvoice extends FRPersistencyAbstractTest {

    @Test public void testLastInvoiceNumber() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);
        this.getNewIssuedInvoice(B1);
        this.getNewIssuedInvoice(B1);
        FRInvoiceEntity resultInvoice2 = this.getNewIssuedInvoice(B1);
        Assertions.assertEquals(new Integer(3), resultInvoice2.getSeriesNumber());
    }

    @Test public void testLastInvoiceNumberWithDifferentBusiness() {
        StringID<Business> B1 = StringID.fromValue("B1");
        StringID<Business> B2 = StringID.fromValue("B2");
        this.createSeries(B1);
        this.createSeries(B2);
        FRInvoiceEntity inv1 = this.getNewIssuedInvoice(B1);
        FRInvoiceEntity inv2 = this.getNewIssuedInvoice(B2);

        FRInvoiceEntity resultInvoice1 = this.getInstance(DAOFRInvoice.class)
                .getLatestInvoiceFromSeries(inv1.getSeries(), inv1.getBusiness().getUID());
        FRInvoiceEntity resultInvoice2 = this.getInstance(DAOFRInvoice.class)
                .getLatestInvoiceFromSeries(inv2.getSeries(), inv2.getBusiness().getUID());

        FRInvoiceEntity inv3 = this.getNewIssuedInvoice(B1);
        FRInvoiceEntity inv4 = this.getNewIssuedInvoice(B2);

        Assertions.assertEquals(inv1.getSeriesNumber(), resultInvoice1.getSeriesNumber());
        Assertions.assertEquals(inv2.getSeriesNumber(), resultInvoice2.getSeriesNumber());
        Assertions.assertEquals(new Integer(2), inv3.getSeriesNumber());
        Assertions.assertEquals(new Integer(2), inv4.getSeriesNumber());
    }

    @Test public void testWithNoInvoice() {
        DAOFRInvoice instance = this.getInstance(DAOFRInvoice.class);
        Assertions.assertThrows(BillyRuntimeException.class,
                () -> instance.getLatestInvoiceFromSeries("NON EXISTING SERIES", StringID.fromValue(UUID.randomUUID().toString())));
    }

    @Test public void testInvoiceFromBusiness() {
        this.createSeries(StringID.fromValue("B1"));
        FRInvoiceEntity inv1 = this.getNewIssuedInvoice(StringID.fromValue("B1"));

        FRGenericInvoiceEntity res =
                this.getInstance(DAOFRInvoice.class).findByNumber(inv1.getBusiness().getUID(), inv1.getNumber());

        Assertions.assertEquals(inv1.getUID(), res.getUID());
        Assertions.assertNull(this.getInstance(DAOFRInvoice.class)
                .findByNumber(StringID.fromValue("INEXISTENT_BUSINESS"), inv1.getNumber()));
    }

    @Test public void testFindCreditNote() {
        this.createSeries(StringID.fromValue("B1"));
        FRInvoiceEntity inv1 = this.getNewIssuedInvoice(StringID.fromValue("B1"));
        FRCreditNote cc1 = this.getNewIssuedCreditnote(inv1);

        List<FRCreditNote> cn1 = this.getInstance(DAOFRCreditNote.class)
                .findByReferencedDocument(cc1.getBusiness().getUID(), inv1.getUID());

        List<FRCreditNote> cn0 = this.getInstance(DAOFRCreditNote.class)
                .findByReferencedDocument(StringID.fromValue("B1"), StringID.fromValue("INEXISTENT_CN"));

        Assertions.assertEquals(0, cn0.size());
        Assertions.assertEquals(1, cn1.size());
    }

    @Test public void testFindReceipt() {
        this.createSeries(StringID.fromValue("B1"));
        FRInvoiceEntity inv1 = this.getNewIssuedInvoice(StringID.fromValue("B1"));
        FRReceiptEntity rec1 = this.getNewIssuedReceipt(StringID.fromValue("B1"));

        DAOFRInvoice invoiceDao = this.getInstance(DAOFRInvoice.class);
        DAOFRReceipt receiptDao = this.getInstance(DAOFRReceipt.class);

        FRReceiptEntity found = receiptDao.get(rec1.getUID());
        Assertions.assertEquals(rec1.getUID(), found.getUID());

        try {
            invoiceDao.get(rec1.getUID());
            Assertions.fail();
        } catch (NoResultException e) {
        }

        try {
            receiptDao.get(inv1.getUID());
            Assertions.fail();
        } catch (NoResultException e) {
        }
    }
}
