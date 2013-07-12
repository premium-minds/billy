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
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContextEntity;

public class TestContextBuilder extends AbstractTest {

	private static final String CONTEXT_YML = "src/test/resources/Context.yml";

	@Test
	public void doTest() {
		MockContextEntity mockContext = (MockContextEntity) createMockEntityFromYaml(
				MockContextEntity.class, CONTEXT_YML);

		DAOContext mockDaoContext = this.getMock(DAOContext.class);

		Mockito.when(mockDaoContext.getEntityInstance()).thenReturn(
				new MockContextEntity());

		Context.Builder builder = new Context.Builder(mockDaoContext);

		builder.setDescription(mockContext.getDescription())
				.setName(mockContext.getName()).setParentContextUID(null);

		Context context = builder.build();

		assert (context != null);
		assertEquals(mockContext.getName(), context.getName());
		assertEquals(mockContext.getDescription(), context.getDescription());
		assert (context.getParentContext() == null);

	}

}
