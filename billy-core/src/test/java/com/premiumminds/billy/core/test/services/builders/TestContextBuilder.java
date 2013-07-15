/**
 * Copyright (C) 2013 Premium Minds.
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContextEntity;

public class TestContextBuilder extends AbstractTest {

	private static final String CONTEXT_YML = "src/test/resources/Context.yml";

	@Test
	public void doTest() {
		MockContextEntity mockContext = (MockContextEntity) createMockEntityFromYaml(
				MockContextEntity.class, CONTEXT_YML);

		Mockito.when(getInstance(DAOContext.class).getEntityInstance())
				.thenReturn(new MockContextEntity());

		MockContextEntity mockParentContext = new MockContextEntity();
		mockParentContext.uid = new UID("uid_ref");
		Mockito.when(getInstance(DAOContext.class).get(Matchers.any(UID.class)))
				.thenReturn(mockParentContext);
		mockContext.parentContext = mockParentContext;

		Context.Builder builder = getInstance(Context.Builder.class);

		builder.setDescription(mockContext.getDescription())
				.setName(mockContext.getName())
				.setParentContextUID(mockContext.getParentContext().getUID());

		Context context = builder.build();

		assert (context != null);
		assertEquals(mockContext.getName(), context.getName());
		assertEquals(mockContext.getDescription(), context.getDescription());
		assertEquals(mockContext.getParentContext().getUID(), context
				.getParentContext().getUID());

	}

}
