/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.fixtures.MockContextEntity;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTRegionContextEntity;

public class TestPTRegionContextBuilder extends PTAbstractTest {

	private static final String CONTEXT_YML = null;

	@Test
	public void testRegionCode() {
		MockPTRegionContextEntity mockRegionContextEntity = (MockPTRegionContextEntity) createMockEntity(
				generateMockEntityConstructor(MockPTRegionContextEntity.class),
				CONTEXT_YML);

		Mockito.when(getInstance(DAOPTRegionContext.class).getEntityInstance())
				.thenReturn(new MockPTRegionContextEntity());

		MockContextEntity mockParentContext = (MockContextEntity) createMockEntity(
				generateMockEntityConstructor(MockContextEntity.class),
				CONTEXT_YML);

		Mockito.when(getInstance(DAOContext.class).get(Matchers.any(UID.class)))
				.thenReturn(mockParentContext);

		mockRegionContextEntity.parentContext = mockParentContext;

		PTRegionContext.Builder builder = getInstance(PTRegionContext.Builder.class);

		builder.setDescription(mockRegionContextEntity.getDescription())
				.setName(mockRegionContextEntity.getName())
				.setRegionCode(mockRegionContextEntity.getRegionCode())
				.setParentContextUID(mockParentContext.getUID());

		PTRegionContext regionContex = builder.build();

		assert (regionContex != null);
	}
}
