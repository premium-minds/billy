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
