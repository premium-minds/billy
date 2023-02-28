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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.premiumminds.billy.andorra.BillyAndorra;
import com.premiumminds.billy.andorra.persistence.entities.ADRegionContextEntity;
import com.premiumminds.billy.andorra.services.entities.ADTax;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class TestDAOADTax extends ADPersistencyAbstractTest {

    @Test
    public void testGetTaxes() {
        final BillyAndorra billy = this.getInstance(BillyAndorra.class);
        final ADRegionContextEntity context = (ADRegionContextEntity)billy.contexts().continent().barcelona();

        final DAOADTax daoTax = this.getInstance(DAOADTax.class);

        final StringID<Tax> taxUID = billy.taxes().continent().normal().getUID();

        final TaxEntity tax = assertDoesNotThrow(() -> daoTax.get(taxUID));
        assertNotNull(tax);
        assertTrue(daoTax.exists(taxUID));

        assertAll(
                () -> assertEquals(1, daoTax.getTaxes(context, ADTax.ESVATCode.NORMAL, new Date(1661973810L*1000L), new Date(1661973810L*1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, ADTax.ESVATCode.NORMAL, null, new Date(1661973810L*1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, ADTax.ESVATCode.NORMAL, new Date(1661973810L*1000L), null).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, ADTax.ESVATCode.NORMAL, null, null).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, ADTax.ESVATCode.NORMAL, new Date(0), new Date(Integer.MAX_VALUE * 1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, ADTax.ESVATCode.NORMAL, tax.getValidFrom(), tax.getValidTo()).size()),
                () -> assertTrue(daoTax.getTaxes(context, ADTax.ESVATCode.NORMAL, new Date(1L), new Date(0L)).isEmpty())
        );

    }
}
