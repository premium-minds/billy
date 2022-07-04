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
package com.premiumminds.billy.spain.test.services.dao;

import java.util.List;

import javax.persistence.NoResultException;

import com.premiumminds.billy.spain.exceptions.BillySimpleInvoiceException;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;

public class TestDAOESInvoice extends ESPersistencyAbstractTest {

    @Test
    public void testLastInvoiceNumber() {
        String B1 = "B1";
		this.createSeries(B1);
        this.getNewIssuedInvoice(B1);
        this.getNewIssuedInvoice(B1);
        ESInvoiceEntity resultInvoice2 = this.getNewIssuedInvoice(B1);
        Assertions.assertEquals(new Integer(3), resultInvoice2.getSeriesNumber());
    }

    @Test
    public void testLastInvoiceNumberWithDifferentBusiness() {
        String B1 = "B1";
        String B2 = "B2";

		this.createSeries(B1);
		this.createSeries(B2);
        ESInvoiceEntity inv1 = this.getNewIssuedInvoice(B1);
        ESInvoiceEntity inv2 = this.getNewIssuedInvoice(B2);

        ESInvoiceEntity resultInvoice1 = this.getInstance(DAOESInvoice.class)
                .getLatestInvoiceFromSeries(inv1.getSeries(), inv1.getBusiness().getUID().toString());
        ESInvoiceEntity resultInvoice2 = this.getInstance(DAOESInvoice.class)
                .getLatestInvoiceFromSeries(inv2.getSeries(), inv2.getBusiness().getUID().toString());

        ESInvoiceEntity inv3 = this.getNewIssuedInvoice(B1);
        ESInvoiceEntity inv4 = this.getNewIssuedInvoice(B2);

        Assertions.assertEquals(inv1.getSeriesNumber(), resultInvoice1.getSeriesNumber());
        Assertions.assertEquals(inv2.getSeriesNumber(), resultInvoice2.getSeriesNumber());
        Assertions.assertEquals(new Integer(2), inv3.getSeriesNumber());
        Assertions.assertEquals(new Integer(2), inv4.getSeriesNumber());
    }

    @Test
    public void testWithNoInvoice() {
        DAOESInvoice instance = this.getInstance(DAOESInvoice.class);

        Assertions.assertThrows(BillyRuntimeException.class, () -> instance.getLatestInvoiceFromSeries("NON EXISTING SERIES", (new UID().toString())));
    }

    @Test
    public void testInvoiceFromBusiness() {
		this.createSeries("B1");
        ESInvoiceEntity inv1 = this.getNewIssuedInvoice("B1");

        ESGenericInvoiceEntity res =
                this.getInstance(DAOESInvoice.class).findByNumber(inv1.getBusiness().getUID(), inv1.getNumber());

        Assertions.assertEquals(inv1.getUID(), res.getUID());
        Assertions.assertNull(this.getInstance(DAOESInvoice.class).findByNumber(UID.fromString("INEXISTENT_BUSINESS"),
                inv1.getNumber()));
    }

    @Test
    public void testFindCreditNote() {
		this.createSeries("B1");
        ESInvoiceEntity inv1 = this.getNewIssuedInvoice("B1");
        ESCreditNote cc1 = this.getNewIssuedCreditnote(inv1);

        List<ESCreditNote> cn1 = this.getInstance(DAOESCreditNote.class)
                .findByReferencedDocument(cc1.getBusiness().getUID(), inv1.getUID());

        List<ESCreditNote> cn0 = this.getInstance(DAOESCreditNote.class).findByReferencedDocument(UID.fromString("B1"),
                UID.fromString("INEXISTENT_CN"));

        Assertions.assertEquals(0, cn0.size());
        Assertions.assertEquals(1, cn1.size());
    }

    @Test
    public void testFindReceipt() {
		this.createSeries("B1");
        ESInvoiceEntity inv1 = this.getNewIssuedInvoice("B1");
        ESReceiptEntity rec1 = this.getNewIssuedReceipt("B1");

        DAOESInvoice invoiceDao = this.getInstance(DAOESInvoice.class);
        DAOESReceipt receiptDao = this.getInstance(DAOESReceipt.class);

        ESReceiptEntity found = receiptDao.get(rec1.getUID());
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
