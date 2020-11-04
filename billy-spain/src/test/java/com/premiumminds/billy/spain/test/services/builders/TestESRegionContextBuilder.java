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
package com.premiumminds.billy.spain.test.services.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.services.entities.ESRegionContext;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESRegionContextEntity;

public class TestESRegionContextBuilder extends ESAbstractTest {

    private static final String ESCONTEXT_YML = AbstractTest.YML_CONFIGS_DIR + "ESContext.yml";

    @Test
    public void testRegionCode() {
        MockESRegionContextEntity mockRegionContextEntity =
                this.createMockEntity(MockESRegionContextEntity.class, TestESRegionContextBuilder.ESCONTEXT_YML);

        Mockito.when(this.getInstance(DAOESRegionContext.class).getEntityInstance())
                .thenReturn(new MockESRegionContextEntity());

        Mockito.when(this.getInstance(DAOESRegionContext.class).get(Matchers.any(UID.class)))
                .thenReturn((ContextEntity) mockRegionContextEntity.getParentContext());

        ESRegionContext.Builder builder = this.getInstance(ESRegionContext.Builder.class);

        builder.setDescription(mockRegionContextEntity.getDescription()).setName(mockRegionContextEntity.getName())
                .setParentContextUID(mockRegionContextEntity.getParentContext().getUID());

        ESRegionContext regionContex = builder.build();

        Assertions.assertTrue(regionContex != null);
        Assertions.assertTrue(regionContex.getParentContext() != null);

        Assertions.assertEquals(regionContex.getDescription(), mockRegionContextEntity.getDescription());
        Assertions.assertEquals(regionContex.getName(), mockRegionContextEntity.getName());

        Assertions.assertEquals(regionContex.getParentContext().getUID(),
                mockRegionContextEntity.getParentContext().getUID());
    }
}
