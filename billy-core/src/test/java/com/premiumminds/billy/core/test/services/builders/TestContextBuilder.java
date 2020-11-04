/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.services.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContextEntity;

public class TestContextBuilder extends AbstractTest {

    private static final String CONTEXT_YML = AbstractTest.YML_CONFIGS_DIR + "Context.yml";

    @Test
    public void doTest() {
        MockContextEntity mockContext = this.createMockEntity(MockContextEntity.class, TestContextBuilder.CONTEXT_YML);

        Mockito.when(this.getInstance(DAOContext.class).getEntityInstance()).thenReturn(new MockContextEntity());

        Mockito.when(this.getInstance(DAOContext.class).get(Matchers.any(UID.class)))
                .thenReturn((ContextEntity) mockContext.getParentContext());

        Context.Builder builder = this.getInstance(Context.Builder.class);

        builder.setDescription(mockContext.getDescription()).setName(mockContext.getName())
                .setParentContextUID(mockContext.getParentContext().getUID());

        Context context = builder.build();

        assert (context != null);
        Assertions.assertEquals(mockContext.getName(), context.getName());
        Assertions.assertEquals(mockContext.getDescription(), context.getDescription());
        Assertions.assertEquals(mockContext.getParentContext().getUID(), context.getParentContext().getUID());

    }

}
