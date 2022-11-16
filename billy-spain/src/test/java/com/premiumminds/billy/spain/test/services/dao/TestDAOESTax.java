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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.spain.BillySpain;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESRegionContextEntity;
import com.premiumminds.billy.spain.services.entities.ESTax;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class TestDAOESTax extends ESPersistencyAbstractTest {

    @Test
    public void testGetTaxes() {
        final BillySpain billy = this.getInstance(BillySpain.class);
        final ESRegionContextEntity context = (ESRegionContextEntity)billy.contexts().continent().barcelona();

        final DAOESTax daoTax = this.getInstance(DAOESTax.class);

        final StringID<Tax> taxUID = billy.taxes().continent().normal().getUID();

        final TaxEntity tax = assertDoesNotThrow(() -> daoTax.get(taxUID));
        assertNotNull(tax);
        assertTrue(daoTax.exists(taxUID));

        assertAll(
                () -> assertEquals(1, daoTax.getTaxes(context, ESTax.ESVATCode.NORMAL, new Date(1661973810L*1000L), new Date(1661973810L*1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, ESTax.ESVATCode.NORMAL, null, new Date(1661973810L*1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, ESTax.ESVATCode.NORMAL, new Date(1661973810L*1000L), null).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, ESTax.ESVATCode.NORMAL, null, null).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, ESTax.ESVATCode.NORMAL, new Date(0), new Date(Integer.MAX_VALUE * 1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, ESTax.ESVATCode.NORMAL, tax.getValidFrom(), tax.getValidTo()).size()),
                () -> assertTrue(daoTax.getTaxes(context, ESTax.ESVATCode.NORMAL, new Date(1L),new Date(0L)).isEmpty())
        );

    }
}
