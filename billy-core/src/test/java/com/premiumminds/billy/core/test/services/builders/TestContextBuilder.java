package com.premiumminds.billy.core.test.services.builders;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContextEntity;

public class TestContextBuilder extends AbstractTest {
	
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	
	@Test
	public void doTest() {
		MockContextEntity mockContext = this.loadFixture(ContextEntity.class);
		
		DAOContext mockDaoContext = this.getMock(DAOContext.class);
		
		Mockito.when(mockDaoContext.getEntityInstance()).thenReturn(new MockContextEntity());
		
		Context.Builder builder = new Context.Builder(mockDaoContext);
		
		builder.setDescription(mockContext.getDescription()).setName(mockContext.getName()).setParentContextUID(null);
		
		Context context = builder.build();
		
		assert(context != null);
		assertEquals(NAME, context.getName());
		assertEquals(DESCRIPTION, context.getDescription());
		assert(context.getParentContext() == null);
		
	}
	
	public MockContextEntity loadFixture(Class<ContextEntity> clazz) {
		MockContextEntity result = new MockContextEntity();
		
		result.name = NAME;
		result.description = DESCRIPTION;
		
		return result;
	}

}
