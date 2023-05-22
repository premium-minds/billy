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

import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditNote;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.MoreCollectors;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNoteEntry;

public class TestDAOADCreditNote extends ADPersistencyAbstractTest {

    @Test
    public void testNoCreditEntriesForInvoice() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);

        ADInvoiceEntity inv1 = this.getNewIssuedInvoice(B1);

        final DAOADCreditNoteEntry underTest = this.getInstance(DAOADCreditNoteEntry.class);

        Assertions.assertNull(underTest.checkCreditNote(inv1));
        Assertions.assertFalse(underTest.existsCreditNote(inv1));
    }

    @Test
    public void testCreditEntriesForInvoice() {
        StringID<Business> B1 = StringID.fromValue("B1");
        this.createSeries(B1);
        ADInvoiceEntity inv1 = this.getNewIssuedInvoice(B1);
        ADCreditNote ignored = this.getNewIssuedCreditnote(inv1);

        final DAOADCreditNoteEntry underTest = this.getInstance(DAOADCreditNoteEntry.class);

        final ADCreditNoteEntity actual = underTest.checkCreditNote(inv1);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(inv1.getUID(), actual.getEntries()
                .stream()
                .map(x -> x.getReference().getUID())
                .collect(MoreCollectors.onlyElement()));
        Assertions.assertTrue(underTest.existsCreditNote(inv1));
    }
}
