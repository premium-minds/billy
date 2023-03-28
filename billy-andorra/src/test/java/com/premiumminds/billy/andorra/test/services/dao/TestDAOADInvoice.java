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
package com.premiumminds.billy.andorra.test.services.dao;

import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNote;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditNote;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import java.util.List;
import java.util.UUID;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;

public class TestDAOADInvoice extends ADPersistencyAbstractTest {

    @Test
    public void testLastInvoiceNumber() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);
        this.getNewIssuedInvoice(B1);
        this.getNewIssuedInvoice(B1);
        ADInvoiceEntity resultInvoice2 = this.getNewIssuedInvoice(B1);
        Assertions.assertEquals(new Integer(3), resultInvoice2.getSeriesNumber());
    }

    @Test
    public void testLastInvoiceNumberWithDifferentBusiness() {
        StringID<Business> B1 = StringID.fromValue("B1");
        StringID<Business> B2 = StringID.fromValue("B2");

        this.createSeries(B1);
        this.createSeries(B2);
        ADInvoiceEntity inv1 = this.getNewIssuedInvoice(B1);
        ADInvoiceEntity inv2 = this.getNewIssuedInvoice(B2);

        ADInvoiceEntity resultInvoice1 = this.getInstance(DAOADInvoice.class)
                                             .getLatestInvoiceFromSeries(inv1.getSeries(), inv1.getBusiness().getUID());
        ADInvoiceEntity resultInvoice2 = this.getInstance(DAOADInvoice.class)
                                             .getLatestInvoiceFromSeries(inv2.getSeries(), inv2.getBusiness().getUID());

        ADInvoiceEntity inv3 = this.getNewIssuedInvoice(B1);
        ADInvoiceEntity inv4 = this.getNewIssuedInvoice(B2);

        Assertions.assertEquals(inv1.getSeriesNumber(), resultInvoice1.getSeriesNumber());
        Assertions.assertEquals(inv2.getSeriesNumber(), resultInvoice2.getSeriesNumber());
        Assertions.assertEquals(new Integer(2), inv3.getSeriesNumber());
        Assertions.assertEquals(new Integer(2), inv4.getSeriesNumber());
    }

    @Test
    public void testWithNoInvoice() {
        DAOADInvoice instance = this.getInstance(DAOADInvoice.class);

        Assertions.assertThrows(BillyRuntimeException.class, () -> instance.getLatestInvoiceFromSeries("NON EXISTING SERIES", StringID.fromValue(UUID
                .randomUUID().toString())));
    }

    @Test
    public void testInvoiceFromBusiness() {
        this.createSeries(StringID.fromValue("B1"));
        ADInvoiceEntity inv1 = this.getNewIssuedInvoice(StringID.fromValue("B1"));

        ADGenericInvoiceEntity res =
                this.getInstance(DAOADInvoice.class).findByNumber(inv1.getBusiness().getUID(), inv1.getNumber());

        Assertions.assertEquals(inv1.getUID(), res.getUID());
        Assertions.assertNull(this.getInstance(DAOADInvoice.class).findByNumber(
            StringID.fromValue("INEXISTENT_BUSINESS"),
            inv1.getNumber()));
    }

    @Test
    public void testFindCreditNote() {
        this.createSeries(StringID.fromValue("B1"));
        ADInvoiceEntity inv1 = this.getNewIssuedInvoice(StringID.fromValue("B1"));
        ADCreditNote cc1 = this.getNewIssuedCreditnote(inv1);

        List<ADCreditNote> cn1 = this.getInstance(DAOADCreditNote.class)
                                     .findByReferencedDocument(cc1.getBusiness().getUID(), inv1.getUID());

        List<ADCreditNote> cn0 = this.getInstance(DAOADCreditNote.class).findByReferencedDocument(StringID.fromValue("B1"),
                                                                                                  StringID.fromValue("INEXISTENT_CN"));

        Assertions.assertEquals(0, cn0.size());
        Assertions.assertEquals(1, cn1.size());
    }

    @Test
    public void testFindReceipt() {
        this.createSeries(StringID.fromValue("B1"));
        ADInvoiceEntity inv1 = this.getNewIssuedInvoice(StringID.fromValue("B1"));
        ADReceiptEntity rec1 = this.getNewIssuedReceipt(StringID.fromValue("B1"));

        DAOADInvoice invoiceDao = this.getInstance(DAOADInvoice.class);
        DAOADReceipt receiptDao = this.getInstance(DAOADReceipt.class);

        ADReceiptEntity found = receiptDao.get(rec1.getUID());
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
