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
package com.premiumminds.billy.france.test.services.builders;

import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.services.entities.FRRegionContext;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRRegionContextEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestFRRegionContextBuilder extends FRAbstractTest {

    private static final String FRCONTEXT_YML = AbstractTest.YML_CONFIGS_DIR + "FRContext.yml";

    @Test
    public void testRegionCode() {
        MockFRRegionContextEntity mockRegionContextEntity =
                this.createMockEntity(MockFRRegionContextEntity.class, TestFRRegionContextBuilder.FRCONTEXT_YML);

        Mockito.when(this.getInstance(DAOFRRegionContext.class).getEntityInstance())
                .thenReturn(new MockFRRegionContextEntity());

        Mockito.when(this.getInstance(DAOFRRegionContext.class).get(Mockito.any()))
                .thenReturn((ContextEntity) mockRegionContextEntity.getParentContext());

        FRRegionContext.Builder builder = this.getInstance(FRRegionContext.Builder.class);

        builder.setDescription(mockRegionContextEntity.getDescription()).setName(mockRegionContextEntity.getName())
                .setParentContextUID(mockRegionContextEntity.getParentContext().getUID());

        FRRegionContext regionContex = builder.build();

        Assertions.assertTrue(regionContex != null);
        Assertions.assertTrue(regionContex.getParentContext() != null);

        Assertions.assertEquals(regionContex.getDescription(), mockRegionContextEntity.getDescription());
        Assertions.assertEquals(regionContex.getName(), mockRegionContextEntity.getName());

        Assertions.assertEquals(regionContex.getParentContext().getUID(),
                mockRegionContextEntity.getParentContext().getUID());
    }
}
