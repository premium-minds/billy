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

import java.util.Date;

import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.BillyFrance;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRRegionContextEntity;
import com.premiumminds.billy.france.services.entities.FRTax;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDAOFRTax extends FRPersistencyAbstractTest {

    @Test
    public void testGetTaxes() {

        final BillyFrance billy = this.getInstance(BillyFrance.class);
        final FRRegionContextEntity context = (FRRegionContextEntity)billy.contexts().continent().bretagne();

        final DAOFRTax daoTax = this.getInstance(DAOFRTax.class);

        final UID taxUID = billy.taxes().continent().normal().getUID();

        final TaxEntity tax = assertDoesNotThrow(() -> daoTax.get(taxUID));
        assertNotNull(tax);
        assertTrue(daoTax.exists(taxUID));

        assertAll(
                () -> assertEquals(1, daoTax.getTaxes(context, FRTax.FRVATCode.NORMAL, new Date(1661973810L*1000L), new Date(1661973810L*1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, FRTax.FRVATCode.NORMAL, null, new Date(1661973810L*1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, FRTax.FRVATCode.NORMAL, new Date(1661973810L*1000L), null).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, FRTax.FRVATCode.NORMAL, null, null).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, FRTax.FRVATCode.NORMAL, new Date(0), new Date(Integer.MAX_VALUE * 1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, FRTax.FRVATCode.NORMAL, tax.getValidFrom(), tax.getValidTo()).size()),
                () -> assertTrue(daoTax.getTaxes(context, FRTax.FRVATCode.NORMAL, new Date(1L),new Date(0L)).isEmpty())
        );

    }
}
