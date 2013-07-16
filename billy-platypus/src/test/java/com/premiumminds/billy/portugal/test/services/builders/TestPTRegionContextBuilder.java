package com.premiumminds.billy.portugal.test.services.builders;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.fixtures.MockContextEntity;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
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

	}
}
