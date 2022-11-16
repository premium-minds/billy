/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.portugal.BillyPortugal;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class TestDAOPTTax extends PTPersistencyAbstractTest {

    @Test
    public void testGetTaxes() {

        final BillyPortugal billy = this.getInstance(BillyPortugal.class);
        final PTRegionContextEntity context = (PTRegionContextEntity)billy.contexts().continent().leiria();

        final DAOPTTax daoTax = this.getInstance(DAOPTTax.class);

        final StringID<Tax> taxUID = billy.taxes().continent().normal().getUID();

        final TaxEntity tax = assertDoesNotThrow(() -> daoTax.get(taxUID));
        assertNotNull(tax);
        assertTrue(daoTax.exists(taxUID));

        assertAll(
                () -> assertEquals(1, daoTax.getTaxes(context, PTTax.PTVATCode.NORMAL, new Date(1661973810L*1000L), new Date(1661973810L*1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, PTTax.PTVATCode.NORMAL,null, new Date(1661973810L*1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, PTTax.PTVATCode.NORMAL, new Date(1661973810L*1000L), null).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, PTTax.PTVATCode.NORMAL, null, null).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, PTTax.PTVATCode.NORMAL, new Date(0), new Date(Integer.MAX_VALUE * 1000L)).size()),
                () -> assertEquals(1, daoTax.getTaxes(context, PTTax.PTVATCode.NORMAL, tax.getValidFrom(), tax.getValidTo()).size()),
                () -> assertTrue(daoTax.getTaxes(context, PTTax.PTVATCode.NORMAL, new Date(1L),new Date(0L)).isEmpty())
        );

    }
}
