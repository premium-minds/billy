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
package com.premiumminds.billy.andorra.test.services.builders;

import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.services.entities.ADRegionContext;
import com.premiumminds.billy.andorra.services.entities.ADRegionContext.Builder;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADRegionContextEntity;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.test.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADRegionContextBuilder extends ADAbstractTest {

    private static final String AD_CONTEXT_YML = AbstractTest.YML_CONFIGS_DIR + "ADContext.yml";

    @Test
    public void testRegionCode() {
        MockADRegionContextEntity mockRegionContextEntity =
                this.createMockEntity(MockADRegionContextEntity.class, TestADRegionContextBuilder.AD_CONTEXT_YML);

        Mockito.when(this.getInstance(DAOADRegionContext.class).getEntityInstance())
                .thenReturn(new MockADRegionContextEntity());

        Mockito.when(this.getInstance(DAOADRegionContext.class).get(Mockito.any()))
                .thenReturn((ContextEntity) mockRegionContextEntity.getParentContext());

        Builder builder = this.getInstance(Builder.class);

        builder.setDescription(mockRegionContextEntity.getDescription()).setName(mockRegionContextEntity.getName())
                .setParentContextUID(mockRegionContextEntity.getParentContext().getUID());

        ADRegionContext regionContex = builder.build();

        Assertions.assertTrue(regionContex != null);
        Assertions.assertTrue(regionContex.getParentContext() != null);

        Assertions.assertEquals(regionContex.getDescription(), mockRegionContextEntity.getDescription());
        Assertions.assertEquals(regionContex.getName(), mockRegionContextEntity.getName());

        Assertions.assertEquals(regionContex.getParentContext().getUID(),
                mockRegionContextEntity.getParentContext().getUID());
    }
}
