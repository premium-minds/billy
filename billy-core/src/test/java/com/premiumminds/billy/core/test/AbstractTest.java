package com.premiumminds.billy.core.test;

import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.CoreDependencyModule;

public class AbstractTest {

	private static Injector injector;

	@BeforeClass
	public static void setUpClass() {
		AbstractTest.injector = Guice
				.createInjector(Modules.override(new CoreDependencyModule()).with(new MockDependencyModule()));
	}

	public <T> T getInstance(Class<T> clazz) {
		return AbstractTest.injector.getInstance(clazz);
	}

	public <T> T getMock(Class<T> clazz) {
		return mock(clazz);
	}

}
