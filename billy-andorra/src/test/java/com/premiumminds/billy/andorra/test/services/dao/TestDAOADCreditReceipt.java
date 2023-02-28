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

import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.MoreCollectors;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceiptEntry;

public class TestDAOADCreditReceipt extends ADPersistencyAbstractTest {

    @Test
    public void testNoCreditEntriesForInvoice() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);

        ADReceiptEntity rec1 = this.getNewIssuedReceipt(StringID.fromValue("B1"));

        final DAOADCreditReceiptEntry underTest = this.getInstance(DAOADCreditReceiptEntry.class);

        Assertions.assertNull(underTest.checkCreditReceipt(rec1));
    }

    @Test
    public void testCreditEntriesForInvoice() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);

        ADReceiptEntity rec1 = this.getNewIssuedReceipt(B1);
        ADCreditReceiptEntity ignored = this.getNewIssuedCreditReceipt(rec1);

        final DAOADCreditReceiptEntry underTest = this.getInstance(DAOADCreditReceiptEntry.class);

        final ADCreditReceiptEntity actual = underTest.checkCreditReceipt(rec1);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(rec1.getUID(), actual.getEntries()
                .stream()
                .map(x -> x.getReference().getUID())
                .collect(MoreCollectors.onlyElement()));
    }
}
