package com.premiumminds.billy.portugal.test;

import static org.mockito.Mockito.mock;

import com.premiumminds.billy.core.test.MockDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;

public class PTMockDependencyModule extends MockDependencyModule {

	@Override
	protected void configure() {
		super.configure();
		bind(DAOPTRegionContext.class).toInstance(
				mock(DAOPTRegionContext.class));
	}

}
