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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.MoreCollectors;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceiptEntry;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;

public class TestDAOESCreditReceipt extends ESPersistencyAbstractTest {

    @Test
    public void testNoCreditEntriesForInvoice() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);

        ESReceiptEntity rec1 = this.getNewIssuedReceipt(StringID.fromValue("B1"));

        final DAOESCreditReceiptEntry underTest = this.getInstance(DAOESCreditReceiptEntry.class);

        Assertions.assertNull(underTest.checkCreditReceipt(rec1));
        Assertions.assertFalse(underTest.existsCreditReceipt(rec1));
    }

    @Test
    public void testCreditEntriesForInvoice() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);

        ESReceiptEntity rec1 = this.getNewIssuedReceipt(B1);
        ESCreditReceiptEntity ignored = this.getNewIssuedCreditReceipt(rec1);

        final DAOESCreditReceiptEntry underTest = this.getInstance(DAOESCreditReceiptEntry.class);

        final ESCreditReceiptEntity actual = underTest.checkCreditReceipt(rec1);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(rec1.getUID(), actual.getEntries()
                .stream()
                .map(x -> x.getReference().getUID())
                .collect(MoreCollectors.onlyElement()));
        Assertions.assertTrue(underTest.existsCreditReceipt(rec1));
    }
}
