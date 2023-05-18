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
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNoteEntry;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;

public class TestDAOESCreditNote extends ESPersistencyAbstractTest {

    @Test
    public void testNoCreditEntriesForInvoice() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);

        ESInvoiceEntity inv1 = this.getNewIssuedInvoice(B1);

        final DAOESCreditNoteEntry underTest = this.getInstance(DAOESCreditNoteEntry.class);

        Assertions.assertNull(underTest.checkCreditNote(inv1));
        Assertions.assertFalse(underTest.existsCreditNote(inv1));
    }

    @Test
    public void testCreditEntriesForInvoice() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);
        ESInvoiceEntity inv1 = this.getNewIssuedInvoice(B1);
        ESCreditNote ignored = this.getNewIssuedCreditnote(inv1);

        final DAOESCreditNoteEntry underTest = this.getInstance(DAOESCreditNoteEntry.class);

        final ESCreditNoteEntity actual = underTest.checkCreditNote(inv1);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(inv1.getUID(), actual.getEntries()
                .stream()
                .map(x -> x.getReference().getUID())
                .collect(MoreCollectors.onlyElement()));
        Assertions.assertTrue(underTest.existsCreditNote(inv1));
    }
}
