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
package com.premiumminds.billy.portugal.test.services.builders;

import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTRegionContextEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestPTRegionContextBuilder extends PTAbstractTest {

    private static final String PTCONTEXT_YML = AbstractTest.YML_CONFIGS_DIR + "PTContext.yml";

    @Test
    public void testRegionCode() {
        MockPTRegionContextEntity mockRegionContextEntity =
                this.createMockEntity(MockPTRegionContextEntity.class, TestPTRegionContextBuilder.PTCONTEXT_YML);

        Mockito.when(this.getInstance(DAOPTRegionContext.class).getEntityInstance())
                .thenReturn(new MockPTRegionContextEntity());

        Mockito.when(this.getInstance(DAOPTRegionContext.class).get(Mockito.any()))
                .thenReturn((ContextEntity) mockRegionContextEntity.getParentContext());

        PTRegionContext.Builder builder = this.getInstance(PTRegionContext.Builder.class);

        builder.setDescription(mockRegionContextEntity.getDescription()).setName(mockRegionContextEntity.getName())
                .setRegionCode(mockRegionContextEntity.getRegionCode())
                .setParentContextUID(mockRegionContextEntity.getParentContext().getUID());

        PTRegionContext regionContex = builder.build();

        Assertions.assertTrue(regionContex != null);
        Assertions.assertTrue(regionContex.getParentContext() != null);

        Assertions.assertEquals(regionContex.getRegionCode(), mockRegionContextEntity.getRegionCode());
        Assertions.assertEquals(regionContex.getDescription(), mockRegionContextEntity.getDescription());
        Assertions.assertEquals(regionContex.getName(), mockRegionContextEntity.getName());

        Assertions.assertEquals(regionContex.getParentContext().getUID(),
                mockRegionContextEntity.getParentContext().getUID());
    }
}
